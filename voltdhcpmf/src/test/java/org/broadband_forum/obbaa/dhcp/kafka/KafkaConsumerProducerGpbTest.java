package org.broadband_forum.obbaa.dhcp.kafka;

import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.broadband_forum.obbaa.dhcp.VOLTDhcpManagement;
import org.broadband_forum.obbaa.dhcp.exception.MessageFormatterException;
import org.broadband_forum.obbaa.dhcp.kafka.consumer.DhcpKafkaConsumerGpb;
import org.broadband_forum.obbaa.dhcp.kafka.producer.DhcpKafkaProducerGpb;
import org.broadband_forum.obbaa.dhcp.kafka.producer.convert.MsgSerializer;
import org.broadband_forum.obbaa.dhcp.message.gpb.message.Msg;
import org.broadband_forum.obbaa.nf.dao.NetworkFunctionDao;
import org.junit.Test;
import org.mockito.Mock;
import org.osgi.framework.Bundle;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KafkaConsumerProducerGpbTest {
    @Mock
    private MockProducer<String, Msg> m_mockProducer;
    @Mock
    private MockConsumer<String, Msg> m_mockConsumer;
    @Mock
    private MockConsumer<String, Msg> m_closeConsumer;
    @Mock
    private Bundle m_bundle;
    @Mock
    private VOLTDhcpManagement m_volMgmt;
    @Mock
    private NetworkFunctionDao m_networkFunctionDao;

    private DhcpKafkaProducerGpb testProducer;
    private DhcpKafkaConsumerGpb testConsumer;

    public KafkaConsumerProducerGpbTest() throws MessageFormatterException {
        sendKV_thenVerifyHistory();
        testKafkaTopicChecker();
        add_Callbacks();
        consumeKV_thenVerifyConsumption();
        destroyConsumer();
        destroyProducer();
    }

    @Test
    public void sendKV_thenVerifyHistory() throws MessageFormatterException {
        m_mockProducer = new MockProducer<>(true, new StringSerializer(), new MsgSerializer());
        testProducer = new DhcpKafkaProducerGpb(m_bundle);
        testProducer.setProducer(m_mockProducer);
        testProducer.sendNotification("testkey", Msg.newBuilder().build());
        assertEquals(1, m_mockProducer.history().size());
    }

    @Test
    public void testKafkaTopicChecker() {
        testProducer = new DhcpKafkaProducerGpb(m_bundle);
        assertEquals("sometopic", testProducer.checkTopicName("some.topic"));
    }

    @Test
    public void add_Callbacks() {
        m_mockConsumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
        testConsumer = new DhcpKafkaConsumerGpb(m_volMgmt, m_bundle, m_networkFunctionDao);
        testConsumer.setConsumer(m_mockConsumer);
        testConsumer.setCallback(new KafkaNotificationCallback<>(m_volMgmt));
        testConsumer.addNotificationCallback();
    }

    @Test
    public void consumeKV_thenVerifyConsumption() {
        Thread subscribeThread = new Thread() {
            public void run() {
                testConsumer.subscribe();
            }
        };
        assertEquals(0, m_mockConsumer.poll(Duration.ZERO).count());
    }

    @Test
    public void destroyProducer() {
        testProducer.destroy();
        assertTrue(m_mockProducer.closed());
    }

    @Test
    public void destroyConsumer() {
        DhcpKafkaConsumerGpb closeConsumer = new DhcpKafkaConsumerGpb(m_volMgmt, m_bundle, m_networkFunctionDao);
        m_closeConsumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
        closeConsumer.setConsumer(m_closeConsumer);
        closeConsumer.testInitThreadpool();
        closeConsumer.destroy();
        assertTrue(m_closeConsumer.closed());
    }
}
