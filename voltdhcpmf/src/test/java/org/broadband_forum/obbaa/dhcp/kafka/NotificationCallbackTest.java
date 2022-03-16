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

package org.broadband_forum.obbaa.dhcp.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.broadband_forum.obbaa.dhcp.VOLTDhcpManagement;
import org.broadband_forum.obbaa.dhcp.message.gpb.message.Msg;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public final class NotificationCallbackTest {

    @Mock
    private VOLTDhcpManagement m_voltDhcpManagement;
    @Mock
    private KafkaNotificationCallback<String> m_callback;
    @Mock
    private KafkaNotificationCallback<Msg> m_callbackGpb;
    @Mock
    private ConsumerRecord<String, String> m_testRecord;
    @Mock
    private Hashtable<String, java.util.function.Consumer> m_callbackFunctions;

    private JSONObject m_json;
    private Msg m_gpb;
    private int m_result = 0;


    public NotificationCallbackTest() {
        testResponseCallbackJson();
        testResponseCallbackGpb();
        testOnNotificationJson();
        testOnNotificationGpb();
    }

    @Test
    public void testResponseCallbackJson() {
        MockitoAnnotations.initMocks(this);
        m_json = new JSONObject("{test:testvalue}");
        doNothing().when(m_voltDhcpManagement).processResponse(m_json);
        doCallRealMethod().when(m_callback).processResponse(any());
        m_callback = new KafkaNotificationCallback<>(m_voltDhcpManagement);
        m_callback.processResponse(m_json);
        verify(m_voltDhcpManagement, times(1)).processResponse(any());
    }

    @Test
    public void testResponseCallbackGpb() {
        MockitoAnnotations.initMocks(this);
        m_gpb = Msg.newBuilder().build();
        doNothing().when(m_voltDhcpManagement).processResponse(m_gpb);
        doCallRealMethod().when(m_callbackGpb).processResponse(any());
        m_callbackGpb = new KafkaNotificationCallback<>(m_voltDhcpManagement);
        m_callbackGpb.processResponse(m_gpb);
        verify(m_voltDhcpManagement, times(1)).processResponse(any());
    }

    @Test
    public void testOnNotificationJson() {
        MockitoAnnotations.initMocks(this);
        Map<TopicPartition, List<ConsumerRecord<String, String>>> records = new LinkedHashMap<>();
        ConsumerRecord<String, String> testrecord = new ConsumerRecord<>("topic", 1, 0, "key", "value1");
        records.put(new TopicPartition("topic", 1), Collections.singletonList(testrecord));
        ConsumerRecords<String, String> consumerRecords = new ConsumerRecords<>(records);
        Hashtable<String, java.util.function.Consumer> callbackFunctions = new Hashtable<String, java.util.function.Consumer>();
        callbackFunctions.put("topic", this::changeResult);
        m_result = 0;
        m_callback = new KafkaNotificationCallback<>(m_voltDhcpManagement);
        m_callback.onNotification(consumerRecords, callbackFunctions);
        assertEquals(1, m_result);
    }

    @Test
    public void testOnNotificationGpb() {
        MockitoAnnotations.initMocks(this);
        Map<TopicPartition, List<ConsumerRecord<String, Msg>>> records = new LinkedHashMap<>();
        ConsumerRecord<String, Msg> testrecord = new ConsumerRecord<>("topic", 1, 0, "key", Msg.newBuilder().build());
        records.put(new TopicPartition("topic", 1), Collections.singletonList(testrecord));
        ConsumerRecords<String, Msg> consumerRecords = new ConsumerRecords<>(records);
        Hashtable<String, java.util.function.Consumer> callbackFunctions = new Hashtable<String, java.util.function.Consumer>();
        callbackFunctions.put("topic", this::changeResult);
        m_result = 0;
        m_callbackGpb = new KafkaNotificationCallback<>(m_voltDhcpManagement);
        m_callbackGpb.onNotification(consumerRecords, callbackFunctions);
        assertEquals(1, m_result);
    }

    private void changeResult(Object obj) {
        Object not_relevant = obj;
        m_result = 1;
    }
}