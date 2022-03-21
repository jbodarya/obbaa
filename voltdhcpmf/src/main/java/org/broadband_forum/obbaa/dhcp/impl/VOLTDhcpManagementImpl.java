/*
 */

package org.broadband_forum.obbaa.dhcp.impl;

import org.apache.log4j.Logger;
import org.broadband_forum.obbaa.dhcp.DhcpConstants;
import org.broadband_forum.obbaa.dhcp.Entity;
import org.broadband_forum.obbaa.dhcp.VOLTDhcpManagement;
import org.broadband_forum.obbaa.dhcp.exception.MessageFormatterException;
import org.broadband_forum.obbaa.dhcp.kafka.consumer.DhcpKafkaConsumer;
import org.broadband_forum.obbaa.dhcp.kafka.producer.DhcpKafkaProducer;
import org.broadband_forum.obbaa.dhcp.message.*;
import org.broadband_forum.obbaa.dhcp.util.VOLTDhcpManagementUtil;
import org.broadband_forum.obbaa.dmyang.entities.Device;
import org.broadband_forum.obbaa.netconf.api.messages.EditConfigRequest;
import org.broadband_forum.obbaa.netconf.api.util.NetconfMessageBuilderException;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.utils.TxService;
import org.broadband_forum.obbaa.nf.dao.NetworkFunctionDao;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * Manages Action on Send and received Kafka messages
 * </p>
 */
public class VOLTDhcpManagementImpl implements VOLTDhcpManagement {
    private static final Logger LOGGER = Logger.getLogger(VOLTDhcpManagementImpl.class);
    private final TxService m_txService;
    private final DhcpKafkaProducer m_kafkaProducer;
    private ThreadPoolExecutor m_processNotificationResponsePool;
    private ThreadPoolExecutor m_processRequestResponsePool;
    private ThreadPoolExecutor m_processNotificationRequestPool;
    private ThreadPoolExecutor m_kafkaCommunicationPool;
    private MessageFormatter m_messageFormatter;
    private NetworkFunctionDao m_networkFunctionDao;
    private DhcpKafkaConsumer m_dhcpKafkaconsumer;
    private Map<String, Set<String>> m_kafkaConsumerTopicMap;
    private AtomicLong m_messageId = new AtomicLong(0);

    public VOLTDhcpManagementImpl(TxService txService,
                                  DhcpKafkaProducer kafkaProducer,
                                  MessageFormatter messageFormatter,
                                  NetworkFunctionDao networkFunctionDao) {
        m_txService = txService;
        m_kafkaProducer = kafkaProducer;
        m_messageFormatter = messageFormatter;
        m_networkFunctionDao = networkFunctionDao;
        m_kafkaConsumerTopicMap = new HashMap<>();
    }


    @Override
    public void setKafkaConsumer(DhcpKafkaConsumer dhcpKafkaConsumer) {
        m_dhcpKafkaconsumer = dhcpKafkaConsumer;
    }

    @Override
    public void unsetKafkaConsumer(DhcpKafkaConsumer dhcpKafkaConsumer) {
        m_dhcpKafkaconsumer = null;
    }

    @Override
    public void networkFunctionAdded(String networkFunctionName) {
        LOGGER.debug("network function added, updating the subscription");
        VOLTDhcpManagementUtil.updateKafkaSubscriptions(networkFunctionName, m_messageFormatter, m_networkFunctionDao,
                m_dhcpKafkaconsumer, m_kafkaConsumerTopicMap);
    }

    @Override
    public void networkFunctionRemoved(String networkFunctionName) {
        LOGGER.debug("network function removed, removing the subscription");
        VOLTDhcpManagementUtil.removeSubscriptions(networkFunctionName, m_dhcpKafkaconsumer, m_kafkaConsumerTopicMap);
    }

    public void init() {
        m_processNotificationRequestPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(DhcpConstants.PROCESS_NOTIF_REQUEST_THREADS);
        m_processNotificationResponsePool = (ThreadPoolExecutor) Executors.newFixedThreadPool(DhcpConstants.PROCESS_NOTIF_RESPONSE_THREADS);
        m_processRequestResponsePool = (ThreadPoolExecutor) Executors.newFixedThreadPool(DhcpConstants.PROCESS_REQ_RESPONSE_THREADS);
        m_kafkaCommunicationPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(DhcpConstants.KAFKA_COMMUNICATION_THREADS);

        /* TODO below code is for test purpose only : remove before merge */

        LOGGER.info("XXXXXXXXXXXXXXXXXXXXXX init ");
//        new Thread(() -> {
//            int i = 0;
//            while (true) {
//                HashMap<String,String> m = new HashMap();
//                m.put("firstElement ", "one");
//                m.put("secondElement", "two");
//                try {
//                    sendDhcpTopics(m);
//                    Thread.sleep(10000);
//                } catch (Exception e) {
//                    LOGGER.info(e);
//                }
//            }
//        }).start();

    }

    public void destroy() {
        m_processNotificationRequestPool.shutdown();
        m_processNotificationResponsePool.shutdown();
        m_processRequestResponsePool.shutdown();
        m_kafkaCommunicationPool.shutdown();
    }


    public void sendDhcpTopics(Map<String, String> dhcpValues) {

        LOGGER.info("XXXXXXXXXXXXXXXXXXXXXX sending dhcp topic");
        Entity e = new Entity("DHCP-ENTITY", dhcpValues);

        Object kafkaMessage = getFormattedKafkaMessage(e, dhcpValues.getOrDefault("bbf-xpongemtcont:name", "onu"), "DHCPAPP",
                "DHCPSUBSCRIBERDETAILS", ObjectType.VOLTMF, "rpc");
        if (kafkaMessage != null) {


            //m_networkFunctionDao.getAllNetworkFunctionNames()
            m_networkFunctionDao.getAllNetworkFunctionNames().forEach(nf -> {
                VOLTDhcpManagementUtil.sendKafkaMessage(kafkaMessage, nf,
                        m_txService, m_networkFunctionDao, m_kafkaProducer);
            });
        }

    }

    private Object getFormattedKafkaMessage(Entity entity, String deviceName, String recepientName, String objectName,
                                            ObjectType objectType, String operationType) {
        NetworkWideTag networkWideTag = new NetworkWideTag(deviceName, recepientName, objectName, objectType);
        Device onuDevice = null;
        Object kafkaMessage = null;
        VOLTDhcpManagementUtil.setMessageId(entity, m_messageId);
        try {

            kafkaMessage = m_messageFormatter.getFormattedRequest(entity, operationType, onuDevice
                    ,networkWideTag);
            VOLTDhcpManagementUtil.registerInRequestMap(entity, deviceName, operationType);
        } catch (NetconfMessageBuilderException e) {
            LOGGER.error(String.format("Failed to convert netconf request to json: %s", entity.toString(), e));
        } catch (MessageFormatterException e) {
            LOGGER.error(String.format("Failed to build GPB message from netconf request: %s", entity.toString(), e));
        }
        return kafkaMessage;
    }

    @Override
    public void processResponse(Object responseObject) {
        try { /* the callback function is here to process the response */

            /* need to remove identifier, once get the response */
           // VOLTManagementUtil.removeRequestFromMap(identifier);

            ResponseData initialResponseData = m_messageFormatter.getResponseData(responseObject);
            ResponseData responseData = VOLTDhcpManagementUtil.updateOperationTypeInResponseData(initialResponseData, m_messageFormatter);
//            if (responseData != null) {
            m_processRequestResponsePool.execute(() -> {
                processRequestResponse(responseData);
            });
//                }
//            } else {
//                LOGGER.error("Error while processing response. Response Data could not be formed ");
//            }
        } catch (MessageFormatterException e) {
            LOGGER.error(e.getMessage());
        }
    }

    protected void processNotificationResponse(String onuDeviceName, String operationType, String identifier,
                                               String responseStatus, String failureReason, String data) {
    }

    protected void processRequestResponse(ResponseData responseData) {

        //        VOLTManagementUtil.removeRequestFromMap(identifier);

    }

    @Override
    public void processNotification(Object notificationResponse) {
        LOGGER.info(String.format("Received notification:  %s", notificationResponse.toString()));
//        try {
        if (m_messageFormatter instanceof GpbFormatter) {
//        } catch (MessageFormatterException e) {
//            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void processApplicationRequest(EditConfigRequest requeest) {

        LOGGER.info(String.format("Get request %s ,", requeest.requestToString()));

        DocumentBuilder documentBuilder;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(requeest.requestToString())));
            NodeList nodeList = document.getElementsByTagName("bbf-xpongemtcont:traffic-descriptor-profile");

            sendDhcpTopics(getElementsFromNodes(nodeList));

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Map<String, String> getElementsFromNodes(NodeList nodeList) {
        Map<String, String> listOfElements = new HashMap();

        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE && tempNode.hasChildNodes()) {
                NodeList childNodeList = tempNode.getChildNodes();

                for (int c = 0; c < childNodeList.getLength(); c++) {
                    Node tempChildNode = childNodeList.item(c);
                    if (tempChildNode.getNodeType() == Node.ELEMENT_NODE) {
                        listOfElements.put(tempChildNode.getNodeName(),tempChildNode.getTextContent());
                    }
                }
            }
        }
        return listOfElements;
    }

}