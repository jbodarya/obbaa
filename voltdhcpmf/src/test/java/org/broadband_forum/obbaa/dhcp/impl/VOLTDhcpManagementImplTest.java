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
import org.broadband_forum.obbaa.dhcp.kafka.consumer.DhcpKafkaConsumer;
import org.broadband_forum.obbaa.dhcp.kafka.consumer.DhcpKafkaConsumerGpb;
import org.broadband_forum.obbaa.dhcp.kafka.consumer.DhcpKafkaConsumerJson;
import org.broadband_forum.obbaa.dhcp.kafka.producer.DhcpKafkaProducerGpb;
import org.broadband_forum.obbaa.dhcp.kafka.producer.DhcpKafkaProducerJson;
import org.broadband_forum.obbaa.dhcp.message.GpbFormatter;
import org.broadband_forum.obbaa.dhcp.message.MessageFormatter;
import org.broadband_forum.obbaa.dhcp.message.ResponseData;
import org.broadband_forum.obbaa.dhcp.message.gpb.message.Msg;
import org.broadband_forum.obbaa.dhcp.util.JsonUtil;
import org.broadband_forum.obbaa.dhcp.util.VoltMFTestConstants;
import org.broadband_forum.obbaa.dmyang.entities.*;
import org.broadband_forum.obbaa.netconf.api.messages.GetRequest;
import org.broadband_forum.obbaa.netconf.api.messages.NetconfFilter;
import org.broadband_forum.obbaa.netconf.api.server.notification.NotificationService;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.utils.TxService;
import org.broadband_forum.obbaa.nf.dao.NetworkFunctionDao;
import org.broadband_forum.obbaa.nf.dao.impl.KafkaTopicPurpose;
import org.broadband_forum.obbaa.nf.entities.KafkaTopic;
import org.broadband_forum.obbaa.nm.devicemanager.DeviceManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.opendaylight.yangtools.yang.common.QNameModule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.mockito.Matchers.any;
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
    private DhcpKafkaProducerJson m_kafkaProducerJson;
    @Mock
    private DhcpKafkaProducerGpb m_kafkaProducerGpb;
    @Mock
    private NetconfConnectionManager m_connectionManager;
    @Mock
    private ConnectionState m_connectionState;
    @Mock
    private NotificationService m_notificationService;
    @Mock
    private DeviceManager m_deviceManager;
    @Mock
    private DeviceMgmt m_deviceMgmt;
    @Mock
    private DeviceState m_deviceState;
    @Mock
    private ExpectedAttachmentPoint m_attachmentPoint;
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
        m_txService = new TxService();
        m_voltManagement = new VOLTDhcpManagementImpl(m_txService, m_kafkaProducerJson, m_messageFormatter, m_networkFunctionDao);
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

    @Test
    public void testNetworkFunctionCreationAndDeletion() {
        String networkFunctionName = "dhcpi";
        m_messageFormatter = new GpbFormatter();
        m_voltManagement = new VOLTDhcpManagementImpl(m_txService, m_kafkaProducerJson, m_messageFormatter, m_networkFunctionDao);
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

}
