/*
 * Copyright 2021 Broadband Forum
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

package org.broadband_forum.obbaa.dhcp.util;

import org.broadband_forum.obbaa.connectors.sbi.netconf.NetconfConnectionManager;
import org.broadband_forum.obbaa.dhcp.DhcpConstants;
import org.broadband_forum.obbaa.dhcp.NotificationRequest;
import org.broadband_forum.obbaa.dhcp.exception.MessageFormatterException;
import org.broadband_forum.obbaa.dhcp.kafka.consumer.DhcpKafkaConsumer;
import org.broadband_forum.obbaa.dhcp.kafka.producer.DhcpKafkaProducer;
import org.broadband_forum.obbaa.dhcp.message.GpbFormatter;
import org.broadband_forum.obbaa.dhcp.message.JsonFormatter;
import org.broadband_forum.obbaa.dhcp.message.MessageFormatter;
import org.broadband_forum.obbaa.dhcp.message.ResponseData;
import org.broadband_forum.obbaa.dmyang.dao.DeviceDao;
import org.broadband_forum.obbaa.dmyang.entities.*;
import org.broadband_forum.obbaa.netconf.api.messages.AbstractNetconfRequest;
import org.broadband_forum.obbaa.netconf.api.server.notification.NotificationService;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.utils.TxService;
import org.broadband_forum.obbaa.netconf.server.util.TestUtil;
import org.broadband_forum.obbaa.nf.dao.NetworkFunctionDao;
import org.broadband_forum.obbaa.nf.dao.impl.KafkaTopicPurpose;
import org.broadband_forum.obbaa.nf.entities.KafkaTopic;
import org.broadband_forum.obbaa.nm.devicemanager.DeviceManager;
import org.broadband_forum.obbaa.pma.PmaRegistry;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

//import org.broadband_forum.obbaa.dhcp.MediatedDeviceNetconfSession;
//import org.broadband_forum.obbaa.dhcp.UnknownONUHandler;
//import org.broadband_forum.obbaa.dhcp.entity.UnknownONU;
//import org.broadband_forum.obbaa.dhcp.notification.ONUNotification;

public class VOLTDhcpManagementUtilTest {
    private final String ONU_NAME = "onu1";
    private final String ONU_ID = "1";

    TxService m_txService;
    @Mock
    DeviceDao m_deviceDao;
    @Mock
    Device m_onuDevice;
    @Mock
    DeviceMgmt m_deviceMgmt;
    @Mock
    DeviceState m_deviceState;
    @Mock
    DeviceManager m_deviceManager;
    @Mock
    MessageFormatter m_messageFormatter;
    @Mock
    NetworkFunctionDao m_networkFunctionDao;
    @Mock
    DhcpKafkaConsumer m_dhcpKafkaConsumer;
    @Mock
    DhcpKafkaProducer m_dhcpKafkaProducer;
    @Mock
    NotificationRequest m_notificationRequest;
    @Mock
    Object m_notification;
    @Mock
    Map<String, Set<String>> m_kafkaConsumerTopicMap;
    @Mock
    KafkaTopic m_kafkaTopic;
    @Mock
    NetconfConnectionManager m_netconfConnectionManager;
    @Mock
    NotificationService m_notificationService;
    @Mock
    OnuConfigInfo m_onuConfigInfo;
    @Mock
    ResponseData m_responseData;
    @Mock
    AbstractNetconfRequest m_request;
    @Mock
    PmaRegistry m_pmaRegistry;
    @Mock
    ExpectedAttachmentPoint m_expectedAttachmentPoint;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        m_kafkaTopic.setPurpose(KafkaTopicPurpose.DHCP_RESPONSE.toString());
        m_txService = new TxService();
    }

    @Test
    public void testIsResponseOK() {
        ArrayList<Boolean> responseArray = new ArrayList<>();
        responseArray.add(true);
        responseArray.add(true);
        responseArray.add(true);
        assertTrue(VOLTManagementUtil.isResponseOK(responseArray));
        responseArray.remove(0);
        responseArray.add(0, false);
        assertFalse(VOLTManagementUtil.isResponseOK(responseArray));

    }

    @Test
    public void testUpdateKafkaSubscriptions() {
        Set<KafkaTopic> kafkaTopicSet = new HashSet<>();
        kafkaTopicSet.add(m_kafkaTopic);
        when(m_networkFunctionDao.getKafkaConsumerTopics("dhcp1", KafkaTopicPurpose.DHCP_RESPONSE)).thenReturn(kafkaTopicSet);
        m_messageFormatter = new GpbFormatter();
        VOLTManagementUtil.updateKafkaSubscriptions("dhcp1", m_messageFormatter, m_networkFunctionDao, m_dhcpKafkaConsumer,
                m_kafkaConsumerTopicMap);
        verify(m_dhcpKafkaConsumer, times(1)).updateSubscriberTopics(kafkaTopicSet);
        verify(m_networkFunctionDao, times(1)).getKafkaConsumerTopics("dhcp1", KafkaTopicPurpose.DHCP_RESPONSE);

    }

    @Test
    public void testUpdateKafkaSubscriptionsJsonFormatter() {
        Set<KafkaTopic> kafkaTopicSet = new HashSet<>();
        kafkaTopicSet.add(m_kafkaTopic);
        when(m_networkFunctionDao.getKafkaConsumerTopics("dhcp1")).thenReturn(kafkaTopicSet);
        m_messageFormatter = new JsonFormatter();
        VOLTManagementUtil.updateKafkaSubscriptions("dhcp1", m_messageFormatter, m_networkFunctionDao, m_dhcpKafkaConsumer,
                m_kafkaConsumerTopicMap);
        verify(m_dhcpKafkaConsumer, never()).updateSubscriberTopics(kafkaTopicSet);
        verify(m_networkFunctionDao, never()).getKafkaConsumerTopics("dhcp1");

    }

    @Test
    public void testRemoveSubscriptionsNoEntryInMap() {
        Set<KafkaTopic> kafkaTopicSet = new HashSet<>();
        kafkaTopicSet.add(m_kafkaTopic);
        when(m_kafkaConsumerTopicMap.containsKey("dhcp1")).thenReturn(false);
        VOLTManagementUtil.removeSubscriptions("dhcp1", m_dhcpKafkaConsumer, m_kafkaConsumerTopicMap);
        verify(m_dhcpKafkaConsumer, never()).removeSubscriberTopics(any());
    }

    @Test
    public void testRemoveSubscriptions() {
        Set<KafkaTopic> kafkaTopicSet = new HashSet<>();
        kafkaTopicSet.add(m_kafkaTopic);
        Set<String> kafkaTopicNameSet = new HashSet<>();
        kafkaTopicNameSet.add("dhcp1-request");
        when(m_kafkaConsumerTopicMap.containsKey("dhcp1")).thenReturn(true);
        when(m_kafkaConsumerTopicMap.get("dhcp1")).thenReturn(kafkaTopicNameSet);
        VOLTManagementUtil.removeSubscriptions("dhcp1", m_dhcpKafkaConsumer, m_kafkaConsumerTopicMap);
        verify(m_dhcpKafkaConsumer, times(1)).removeSubscriberTopics(any());
    }

    @Test
    public void testUpdateOperationTypeInResponseDataJsonFormatter() {
        m_messageFormatter = new JsonFormatter();
        when(m_responseData.getIdentifier()).thenReturn("2");
        when(m_request.getMessageId()).thenReturn("2");
        VOLTManagementUtil.registerInRequestMap(m_request, ONU_NAME, DhcpConstants.CREATE_ONU);
        ResponseData responseData = VOLTManagementUtil.updateOperationTypeInResponseData(m_responseData, m_messageFormatter);
        verify(m_responseData, times(0)).setOnuName(ONU_NAME);
        verify(m_responseData, times(0)).setOperationType(DhcpConstants.CREATE_ONU);
        assertNotNull(responseData);
        assertEquals(m_responseData, responseData);
        VOLTManagementUtil.removeRequestFromMap("2");
    }

    @Test
    public void testSendKafkaMessage() throws MessageFormatterException {
        final HashSet<String> kafkaTopicNamesFinal = new HashSet<>();
        kafkaTopicNamesFinal.add("dhcp1-request");
        when(m_networkFunctionDao.getKafkaTopicNames("dhcp1", KafkaTopicPurpose.DHCP_REQUEST)).thenReturn(kafkaTopicNamesFinal);
        VOLTManagementUtil.sendKafkaMessage(m_notification, "dhcp1", m_txService, m_networkFunctionDao, m_dhcpKafkaProducer);
        verify(m_dhcpKafkaProducer, times(1)).sendNotification("dhcp1-request", m_notification);
        kafkaTopicNamesFinal.remove("dhcp1-request");
        VOLTManagementUtil.sendKafkaMessage(m_notification, "dhcp1", m_txService, m_networkFunctionDao, m_dhcpKafkaProducer);
        verify(m_dhcpKafkaProducer, times(1)).sendNotification("dhcp1-request", m_notification);
    }

    @Test
    public void testSendKafkaMessageWhenNoTopicPresent() throws MessageFormatterException {
        final HashSet<String> kafkaTopicNamesFinal = new HashSet<>();
        when(m_networkFunctionDao.getKafkaTopicNames("dhcp1", KafkaTopicPurpose.DHCP_REQUEST)).thenReturn(kafkaTopicNamesFinal);
        VOLTManagementUtil.sendKafkaMessage(m_notification, "dhcp1", m_txService, m_networkFunctionDao, m_dhcpKafkaProducer);
        verify(m_dhcpKafkaProducer, times(0)).sendNotification("dhcp1-request", m_notification);
    }

    @Test
    public void testSetMessageId() {
        AtomicLong messageId = new AtomicLong(0);
        VOLTManagementUtil.setMessageId(m_request, messageId);
        verify(m_request, times(1)).setMessageId("1");
        VOLTManagementUtil.setMessageId(m_request, messageId);
        verify(m_request, times(1)).setMessageId("2");
    }

    @Test
    public void TestGenerateRandomMessageId() {
        String messageId = VOLTManagementUtil.generateRandomMessageId();
        assertNotNull(messageId);
    }

    private String prepareJsonResponseFromFile(String name) {
        return TestUtil.loadAsString(name).trim();
    }
}
