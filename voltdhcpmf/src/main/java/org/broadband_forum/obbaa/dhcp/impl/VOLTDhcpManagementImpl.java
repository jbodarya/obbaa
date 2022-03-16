/*
 */

package org.broadband_forum.obbaa.dhcp.impl;

import org.apache.log4j.Logger;
import org.broadband_forum.obbaa.connectors.sbi.netconf.NetconfConnectionManager;
import org.broadband_forum.obbaa.device.adapter.AdapterManager;
import org.broadband_forum.obbaa.dhcp.DhcpConstants;
import org.broadband_forum.obbaa.dhcp.VOLTDhcpManagement;
import org.broadband_forum.obbaa.dhcp.exception.MessageFormatterException;
import org.broadband_forum.obbaa.dhcp.kafka.consumer.DhcpKafkaConsumer;
import org.broadband_forum.obbaa.dhcp.kafka.producer.DhcpKafkaProducer;
import org.broadband_forum.obbaa.dhcp.message.*;
import org.broadband_forum.obbaa.dhcp.util.VOLTManagementUtil;
import org.broadband_forum.obbaa.dhcp.util.VOLTMgmtRequestCreationUtil;
import org.broadband_forum.obbaa.dmyang.entities.Device;
import org.broadband_forum.obbaa.netconf.alarm.api.AlarmService;
import org.broadband_forum.obbaa.netconf.api.messages.AbstractNetconfRequest;
import org.broadband_forum.obbaa.netconf.api.messages.EditConfigElement;
import org.broadband_forum.obbaa.netconf.api.messages.EditConfigRequest;
import org.broadband_forum.obbaa.netconf.api.messages.NetconfRpcRequest;
import org.broadband_forum.obbaa.netconf.api.server.notification.NotificationService;
import org.broadband_forum.obbaa.netconf.api.util.NetconfMessageBuilderException;
import org.broadband_forum.obbaa.netconf.mn.fwk.schema.SchemaRegistry;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.datastore.ModelNodeDataStoreManager;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.utils.TxService;
import org.broadband_forum.obbaa.nf.dao.NetworkFunctionDao;
import org.broadband_forum.obbaa.pma.PmaRegistry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;
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
    private final AlarmService m_alarmService;
    private final DhcpKafkaProducer m_kafkaProducer;
    private final NetconfConnectionManager m_connectionManager;
    private final ModelNodeDataStoreManager m_modelNodeDSM;
    private final NotificationService m_notificationService;
    private final AdapterManager m_adapterManager;
    private final PmaRegistry m_pmaRegistry;
    private final SchemaRegistry m_schemaRegistry;
    private ThreadPoolExecutor m_processNotificationResponsePool;
    private ThreadPoolExecutor m_processRequestResponsePool;
    private ThreadPoolExecutor m_processNotificationRequestPool;
    private ThreadPoolExecutor m_kafkaCommunicationPool;
    private MessageFormatter m_messageFormatter;
    private NetworkFunctionDao m_networkFunctionDao;
    private DhcpKafkaConsumer m_dhcpKafkaconsumer;
    private Map<String, Set<String>> m_kafkaConsumerTopicMap;
    private Map<String, ArrayList<Boolean>> m_networkFunctionResponse;
    private AtomicLong m_messageId = new AtomicLong(0);

    public VOLTDhcpManagementImpl(TxService txService, AlarmService alarmService,
                                  DhcpKafkaProducer kafkaProducer,
                                  NetconfConnectionManager connectionManager, ModelNodeDataStoreManager modelNodeDSM,
                                  NotificationService notificationService, AdapterManager adapterManager,
                                  PmaRegistry pmaRegistry, SchemaRegistry schemaRegistry, MessageFormatter messageFormatter,
                                  NetworkFunctionDao networkFunctionDao) {
        m_txService = txService;
        m_alarmService = alarmService;
        m_kafkaProducer = kafkaProducer;
        this.m_connectionManager = connectionManager;
        m_modelNodeDSM = modelNodeDSM;
        m_notificationService = notificationService;
        m_adapterManager = adapterManager;
        m_pmaRegistry = pmaRegistry;
        m_schemaRegistry = schemaRegistry;
        m_messageFormatter = messageFormatter;
        m_networkFunctionDao = networkFunctionDao;
        m_kafkaConsumerTopicMap = new HashMap<>();
        m_networkFunctionResponse = new HashMap<>();
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
        VOLTManagementUtil.updateKafkaSubscriptions(networkFunctionName, m_messageFormatter, m_networkFunctionDao,
                m_dhcpKafkaconsumer, m_kafkaConsumerTopicMap);
    }

    @Override
    public void networkFunctionRemoved(String networkFunctionName) {
        LOGGER.debug("network function removed, removing the subscription");
        VOLTManagementUtil.removeSubscriptions(networkFunctionName, m_dhcpKafkaconsumer, m_kafkaConsumerTopicMap);
    }

    public void init() {
        m_processNotificationRequestPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(DhcpConstants.PROCESS_NOTIF_REQUEST_THREADS);
        m_processNotificationResponsePool = (ThreadPoolExecutor) Executors.newFixedThreadPool(DhcpConstants.PROCESS_NOTIF_RESPONSE_THREADS);
        m_processRequestResponsePool = (ThreadPoolExecutor) Executors.newFixedThreadPool(DhcpConstants.PROCESS_REQ_RESPONSE_THREADS);
        m_kafkaCommunicationPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(DhcpConstants.KAFKA_COMMUNICATION_THREADS);

        /* TODO below code is for test purpose only : remove before merge */
//        new Thread(() -> {
//            int i = 0;
//            while (true) {
//                sendDhcpTopics("device" + i++);
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
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

        /* we required Network function name before procedding  !!!
        gere we can have a hook to publish kafka msg*/

        NetconfRpcRequest rpcRequest = VOLTMgmtRequestCreationUtil.prepareDHCPRequest(dhcpValues);

        LOGGER.debug("Prepared Create ONU RPC request " + rpcRequest.requestToString());

        // Object kafkaMessage = getFormattedKafkaMessage(rpcRequest, onuDeviceName, "DHCPAPP",
        //       "DHCPSUBSCRIBERDETAILS", ObjectType.VOLTMF, ONUConstants.CREATE_ONU);
        Object kafkaMessage = getFormattedKafkaMessage(rpcRequest, dhcpValues.getOrDefault("bbf-xpongemtcont:name", "1"), "DHCPAPP",
                "DHCPSUBSCRIBERDETAILS", ObjectType.VOLTMF, "rpc");
        if (kafkaMessage != null) {


            //m_networkFunctionDao.getAllNetworkFunctionNames()
            m_networkFunctionDao.getAllNetworkFunctionNames().forEach(nf -> {
                VOLTManagementUtil.sendKafkaMessage(kafkaMessage, nf,
                        m_txService, m_networkFunctionDao, m_kafkaProducer);
            });
        }

    }

    private Object getFormattedKafkaMessage(AbstractNetconfRequest request, String onuDeviceName, String recepientName, String objectName,
                                            ObjectType objectType, String operationType) {
        NetworkWideTag networkWideTag = new NetworkWideTag(onuDeviceName, recepientName, objectName, objectType);
        Device onuDevice = null;
        Object kafkaMessage = null;
        VOLTManagementUtil.setMessageId(request, m_messageId);
        try {
            kafkaMessage = m_messageFormatter.getFormattedRequest(request, operationType, onuDevice,
                    m_adapterManager, m_modelNodeDSM, m_schemaRegistry, networkWideTag);
            VOLTManagementUtil.registerInRequestMap(request, onuDeviceName, operationType);
        } catch (NetconfMessageBuilderException e) {
            LOGGER.error(String.format("Failed to convert netconf request to json: %s", request.requestToString(), e));
        } catch (MessageFormatterException e) {
            LOGGER.error(String.format("Failed to build GPB message from netconf request: %s", request.requestToString(), e));
        }
        return kafkaMessage;
    }

    @Override
    public void processResponse(Object responseObject) {
        try { /* the callback function is here to process the response */

            /* need to remove identifier, once get the response */
           // VOLTManagementUtil.removeRequestFromMap(identifier);

            ResponseData initialResponseData = m_messageFormatter.getResponseData(responseObject);
            ResponseData responseData = VOLTManagementUtil.updateOperationTypeInResponseData(initialResponseData, m_messageFormatter);
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


        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(requeest.requestToString());
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
                        System.out.println("\nNode Name =" + tempChildNode.getNodeName());
                        listOfElements.put(tempChildNode.getNodeName(),tempChildNode.getTextContent());
                    }
                }
            }
        }
        return listOfElements;
    }

}