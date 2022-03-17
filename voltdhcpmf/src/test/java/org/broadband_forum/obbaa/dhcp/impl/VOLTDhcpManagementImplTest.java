/*
 * Copyright 2020 Broadband Forum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadband_forum.obbaa.dhcp.impl;

import org.broadband_forum.obbaa.connectors.sbi.netconf.NetconfConnectionManager;
import org.broadband_forum.obbaa.device.adapter.AdapterManager;
import org.broadband_forum.obbaa.dhcp.DhcpConstants;
import org.broadband_forum.obbaa.dhcp.Entity;
import org.broadband_forum.obbaa.dhcp.exception.MessageFormatterException;
import org.broadband_forum.obbaa.dhcp.kafka.consumer.DhcpKafkaConsumer;
import org.broadband_forum.obbaa.dhcp.kafka.consumer.DhcpKafkaConsumerGpb;
import org.broadband_forum.obbaa.dhcp.kafka.consumer.DhcpKafkaConsumerJson;
import org.broadband_forum.obbaa.dhcp.kafka.producer.DhcpKafkaProducerGpb;
import org.broadband_forum.obbaa.dhcp.kafka.producer.DhcpKafkaProducerJson;
import org.broadband_forum.obbaa.dhcp.message.*;
import org.broadband_forum.obbaa.dhcp.message.gpb.message.Msg;
import org.broadband_forum.obbaa.dhcp.util.JsonUtil;
import org.broadband_forum.obbaa.dhcp.util.VoltMFTestConstants;
import org.broadband_forum.obbaa.dmyang.entities.*;
import org.broadband_forum.obbaa.netconf.alarm.api.AlarmService;
import org.broadband_forum.obbaa.netconf.api.messages.*;
import org.broadband_forum.obbaa.netconf.api.server.notification.NotificationService;
import org.broadband_forum.obbaa.netconf.api.util.DocumentUtils;
import org.broadband_forum.obbaa.netconf.api.util.NetconfMessageBuilderException;
import org.broadband_forum.obbaa.netconf.api.util.NetconfResources;
import org.broadband_forum.obbaa.netconf.mn.fwk.schema.SchemaRegistry;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.datastore.ModelNodeDataStoreManager;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.utils.TxService;
import org.broadband_forum.obbaa.netconf.server.util.TestUtil;
import org.broadband_forum.obbaa.nf.dao.NetworkFunctionDao;
import org.broadband_forum.obbaa.nf.dao.impl.KafkaTopicPurpose;
import org.broadband_forum.obbaa.nf.entities.KafkaTopic;
import org.broadband_forum.obbaa.nm.devicemanager.DeviceManager;
import org.broadband_forum.obbaa.pma.PmaRegistry;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.common.QNameModule;
import org.opendaylight.yangtools.yang.model.api.DataSchemaNode;
import org.opendaylight.yangtools.yang.model.api.RevisionAwareXPath;
import org.opendaylight.yangtools.yang.model.api.SchemaPath;
import org.opendaylight.yangtools.yang.model.api.Status;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


/**
 * <p>
 * Unit tests that tests DHCP kafka message and and notification handling
 * </p>
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({VOLTDhcpManagementImpl.class, JsonUtil.class, QNameModule.class, Optional.class, DhcpKafkaConsumer.class})
public class VOLTDhcpManagementImplTest {

    @Mock
    public static GetRequest m_getRequestOne;
    String getResponseNOK = "{\"olt-name\":\"OLT1\",\"payload\":\"{\\\"identifier\\\":\\\"0\\\",\\\"operation\\\":\\\"get\\\",\\\"data\\\":{\\\"network-manager:device-management\\\":{\\\"device-state\\\":{\\\"bbf-obbaa-onu-management:onu-state-info\\\":{\\\"equipment-id\\\":\\\"test1\\\",\\\"software-images\\\":{\\\"software-image\\\":[{\\\"id\\\":\\\"1\\\",\\\"version\\\":\\\"1111\\\",\\\"is-committed\\\":\\\"false\\\",\\\"is-active\\\":\\\"false\\\",\\\"is-valid\\\":\\\"true\\\",\\\"product-code\\\":\\\"test1111\\\",\\\"hash\\\":\\\"11\\\"},{\\\"id\\\":\\\"0\\\",\\\"version\\\":\\\"0000\\\",\\\"is-committed\\\":\\\"true\\\",\\\"is-active\\\":\\\"true\\\",\\\"is-valid\\\":\\\"true\\\",\\\"product-code\\\":\\\"test\\\",\\\"hash\\\":\\\"00\\\"}]}}}}},\\\"status\\\":\\\"NOK\\\"}\",\"onu-name\":\"onu\",\"channel-termination-ref\":\"channeltermination.1\",\"event\": \"response\",\"onu-id\": \"1\"}";
    @Mock
    KafkaTopic m_kafkaTopic;
    @Mock
    private Device m_vonuDevice;
    @Mock
    private AlarmService m_alarmService;
    @Mock
    private DhcpKafkaProducerJson m_kafkaProducerJson;
    @Mock
    private DhcpKafkaProducerGpb m_kafkaProducerGpb;
    @Mock
    private NetconfConnectionManager m_connectionManager;
    @Mock
    private ConnectionState m_connectionState;
    @Mock
    private ModelNodeDataStoreManager m_modelNodeDSM;
    @Mock
    private NotificationService m_notificationService;
    @Mock
    private AdapterManager m_adapterManager;
    @Mock
    private DeviceManager m_deviceManager;
    @Mock
    private DeviceMgmt m_deviceMgmt;
    @Mock
    private DeviceState m_deviceState;
    @Mock
    private PmaRegistry m_pmaRegistry;
    @Mock
    private OnuConfigInfo m_onuConfigInfo;
    @Mock
    private ExpectedAttachmentPoint m_attachmentPoint;
    @Mock
    private SchemaRegistry m_schemaRegistry;
    @Mock
    private OnuStateInfo m_onuStateInfo;
    @Mock
    private NetconfFilter m_filter;
    @Mock
    private ActualAttachmentPoint m_actualAttachmentPoint;
    private ThreadPoolExecutor m_kafkaCommunicationPool;
    private TxService m_txService;
    private VOLTDhcpManagementImpl m_voltManagement;
    private String deviceName = VoltMFTestConstants.ONU;
    private SoftwareImages m_softwareImages;
    private MessageFormatter m_messageFormatter;
    @Mock
    private NetworkFunctionDao m_networkFunctionDao;
    @Mock
    private Set<KafkaTopic> m_kafkaTopicSet;
    @Mock
    private ResponseData m_responseData;
    @Mock
    private Object m_notificationResponse;
    @Mock
    private GpbFormatter m_gpbFormatter;

    private DhcpKafkaConsumer<String> m_dhcpKafkaConsumerJson = PowerMockito.mock(DhcpKafkaConsumerJson.class);
    private DhcpKafkaConsumer<Msg> m_dhcpKafkaConsumerGpb = PowerMockito.mock(DhcpKafkaConsumerGpb.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        m_softwareImages = new SoftwareImages();
        m_txService = new TxService();
        m_voltManagement = new VOLTDhcpManagementImpl(m_txService, m_alarmService, m_kafkaProducerJson,
                m_connectionManager, m_modelNodeDSM, m_notificationService, m_adapterManager, m_pmaRegistry, m_schemaRegistry, m_messageFormatter, m_networkFunctionDao);
        m_voltManagement.setKafkaConsumer(m_dhcpKafkaConsumerJson);
        when(m_vonuDevice.getDeviceName()).thenReturn(deviceName);
        when(m_deviceManager.getDevice(deviceName)).thenReturn(m_vonuDevice);
        PowerMockito.mockStatic(JsonUtil.class);

        m_kafkaCommunicationPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        m_voltManagement.init();
    }

    @Test
    public void testProcessResponseWhenDeviceNull() {
        Device device = null;
        when(m_deviceManager.getDevice(deviceName)).thenReturn(device);
        m_voltManagement.processNotificationResponse(deviceName, VoltMFTestConstants.ONU_GET_OPERATION, VoltMFTestConstants.DEFAULT_MESSAGE_ID,
                VoltMFTestConstants.OK_RESPONSE, null, null);
        verify(m_connectionManager, never()).addMediatedDeviceNetconfSession(m_vonuDevice, null);
    }

    private DataSchemaNode getDataSchemaNode() {
        return new DataSchemaNode() {
            @Override
            public boolean isConfiguration() {
                return false;
            }

            @Override
            public boolean isAugmenting() {
                return false;
            }

            @Override
            public boolean isAddedByUses() {
                return false;
            }

            @NotNull
            @Override
            public QName getQName() {
                return null;
            }

            @NotNull
            @Override
            public SchemaPath getPath() {
                return SchemaPath.create(true, QName.create(VoltMFTestConstants.NETWORK_MANAGER_NAMESPACE, VoltMFTestConstants.NETWORK_MANAGER_REVISION, VoltMFTestConstants.NETWORK_MANAGER));
            }

            @NotNull
            @Override
            public Status getStatus() {
                return null;
            }

            @Override
            public Optional<String> getDescription() {
                return Optional.empty();
            }

            @Override
            public Optional<String> getReference() {
                return Optional.empty();
            }

            @Override
            public Optional<RevisionAwareXPath> getWhenCondition() {
                return Optional.empty();
            }
        };
    }


    @Test
    public void testInternalGetResponseNOKCreatedONU() {
        JSONObject jsonResponse = new JSONObject(getResponseNOK);
        Set<SoftwareImage> softwareImageSet = new HashSet<SoftwareImage>();
        when(m_deviceManager.getDevice(deviceName)).thenReturn(m_vonuDevice);
        when(m_deviceMgmt.getDeviceState()).thenReturn(m_deviceState);
        when(m_deviceState.getOnuStateInfo()).thenReturn(m_onuStateInfo);
        m_voltManagement.processResponse(jsonResponse);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        verify(m_deviceManager, never()).updateEquipmentIdInOnuStateInfo(eq(deviceName), eq(""));
        verify(m_deviceManager, never()).updateSoftwareImageInOnuStateInfo(eq(deviceName), eq(softwareImageSet));
    }

//    @Test
//    @Ignore
//    public void testConvertActionMessage() throws NetconfMessageBuilderException, MessageFormatterException {
//        NetworkWideTag networkWideTag = new NetworkWideTag("onu", "olt", "1", "voltmf", null, "ONU", "Recipient", ObjectType.ONU);
//
//        when(m_vonuDevice.getDeviceName()).thenReturn(deviceName);
//        when(m_deviceManager.getDevice(deviceName)).thenReturn(m_vonuDevice);
//        when(m_adapterManager.getAdapterContext(any())).thenReturn(any());
//
//        Entity actionRequest = generateActionRequest();
//        actionRequest.setMessageId("12");
//        GpbFormatter gpbFormatter = new GpbFormatter();
//        Msg msg = gpbFormatter.getFormattedRequest(eq(actionRequest), eq(NetconfResources.ACTION), eq(m_vonuDevice), eq(m_adapterManager), eq(m_modelNodeDSM), eq(m_schemaRegistry), eq(networkWideTag));
//        String payload = msg.getBody().getRequest().getAction().getInputData().toStringUtf8();
//        assertEquals(payload, VoltMFTestConstants.JSON_PAYLOAD_ACTION_REQUEST);
//    }

    private ActionRequest generateActionRequest() throws NetconfMessageBuilderException {
        String actionRequest = VoltMFTestConstants.XML_ACTION_REQUEST;
        return DocumentToPojoTransformer.getAction(DocumentUtils.stringToDocument(actionRequest));
    }


    @Test
    public void testNetworkFunctionCreationAndDeletion() {
        String networkFunctionName = "dhcpi";
        m_messageFormatter = new GpbFormatter();
        m_voltManagement = new VOLTDhcpManagementImpl(m_txService, m_alarmService, m_kafkaProducerJson,
                m_connectionManager, m_modelNodeDSM, m_notificationService, m_adapterManager, m_pmaRegistry, m_schemaRegistry, m_messageFormatter, m_networkFunctionDao);
        Set<KafkaTopic> kafkaTopicSet = new HashSet<>();
        when(m_kafkaTopic.getPurpose()).thenReturn(KafkaTopicPurpose.DHCP_RESPONSE.toString());
        kafkaTopicSet.add(m_kafkaTopic);
        when(m_networkFunctionDao.getKafkaConsumerTopics(networkFunctionName, KafkaTopicPurpose.DHCP_RESPONSE)).thenReturn(kafkaTopicSet);
        m_voltManagement.setKafkaConsumer(m_dhcpKafkaConsumerJson);
        m_voltManagement.networkFunctionAdded(networkFunctionName);
        verify(m_networkFunctionDao, times(1)).getKafkaConsumerTopics(networkFunctionName, KafkaTopicPurpose.DHCP_RESPONSE);
        verify(m_dhcpKafkaConsumerJson, times(1)).updateSubscriberTopics(any());

        m_voltManagement.networkFunctionRemoved(networkFunctionName);
        verify(m_dhcpKafkaConsumerJson, times(1)).removeSubscriberTopics(any());
    }


    private Notification getDetectNotification(String serialNum, String regId, String chanTermref, String vaniRef,
                                               String onuId, String onuState) throws NetconfMessageBuilderException {
        String onuNotification = "<notification xmlns=\"urn:ietf:params:xml:ns:netconf:notification:1.0\">\n" +
                "    <eventTime>2019-07-25T05:53:36+00:00</eventTime>\n" +
                "    <bbf-xpon-onu-states:onu-state-change xmlns:bbf-xpon-onu-states=\"urn:bbf:yang:bbf-xpon-onu-states\">                               \n" +
                "        <bbf-xpon-onu-states:detected-serial-number>" + serialNum + "</bbf-xpon-onu-states:detected-serial-number>\n" +
                "        <bbf-xpon-onu-states:onu-id>" + onuId + "</bbf-xpon-onu-states:onu-id>\n" +
                "        <bbf-xpon-onu-states:channel-termination-ref>" + chanTermref + "</bbf-xpon-onu-states:channel-termination-ref>\n" +
                "        <bbf-xpon-onu-states:onu-state>" + onuState + "</bbf-xpon-onu-states:onu-state>\n" +
                "        <bbf-xpon-onu-states:detected-registration-id>" + regId + "</bbf-xpon-onu-states:detected-registration-id>\n" +
                "        <bbf-xpon-onu-states:onu-state-last-change>2019-07-25T05:53:36+00:00</bbf-xpon-onu-states:onu-state-last-change>\n" +
                "        <bbf-xpon-onu-states:v-ani-ref>" + vaniRef + "</bbf-xpon-onu-states:v-ani-ref>\n" +
                "    </bbf-xpon-onu-states:onu-state-change>\n" +
                "</notification>";
        Notification notification = new NetconfNotification(DocumentUtils.stringToDocument(onuNotification));
        return notification;
    }

    private Set<SoftwareImage> prepareSwImageSet(String parentId) {
        Set<SoftwareImage> softwareImageSet = new HashSet<>();
        SoftwareImage softwareImage0 = new SoftwareImage();
        softwareImage0.setId(0);
        softwareImage0.setParentId(parentId);
        softwareImage0.setHash("00");
        softwareImage0.setProductCode("test");
        softwareImage0.setVersion("0000");
        softwareImage0.setIsValid(true);
        softwareImage0.setIsCommitted(true);
        softwareImage0.setIsActive(true);
        SoftwareImage softwareImage1 = new SoftwareImage();
        softwareImage1.setId(1);
        softwareImage1.setParentId(parentId);
        softwareImage1.setHash("11");
        softwareImage1.setProductCode("test1111");
        softwareImage1.setVersion("1111");
        softwareImage1.setIsValid(true);
        softwareImage1.setIsCommitted(false);
        softwareImage1.setIsActive(false);
        softwareImageSet.add(softwareImage0);
        softwareImageSet.add(softwareImage1);
        return softwareImageSet;
    }

    private String getEqptId(JSONObject jsonResponse) {
        JSONObject payloadJson = new JSONObject(jsonResponse.getString(DhcpConstants.PAYLOAD_JSON_KEY));
        String data = payloadJson.optString(DhcpConstants.DATA_JSON_KEY);
        JSONObject dataJson = new JSONObject(data);
        return dataJson.getJSONObject(DhcpConstants.NETWORK_MANAGER_JSON_KEY)
                .getJSONObject(DhcpConstants.DEVICE_STATE_JSON_KEY).getJSONObject(DhcpConstants.ONU_STATE_INFO_JSON_KEY)
                .optString(DhcpConstants.EQPT_ID_JSON_KEY);
    }

    private String prepareJsonResponseFromFile(String name) {
        return TestUtil.loadAsString(name).trim();
    }

}
