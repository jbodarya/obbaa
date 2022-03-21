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

import org.apache.log4j.Logger;
import org.broadband_forum.obbaa.dhcp.DhcpConstants;
import org.broadband_forum.obbaa.dhcp.Entity;
import org.broadband_forum.obbaa.dhcp.exception.MessageFormatterException;
import org.broadband_forum.obbaa.dhcp.impl.SupportedDhcpKafkaTopicPurpose;
import org.broadband_forum.obbaa.dhcp.kafka.consumer.DhcpKafkaConsumer;
import org.broadband_forum.obbaa.dhcp.kafka.producer.DhcpKafkaProducer;
import org.broadband_forum.obbaa.dhcp.message.GpbFormatter;
import org.broadband_forum.obbaa.dhcp.message.MessageFormatter;
import org.broadband_forum.obbaa.dhcp.message.ResponseData;
import org.broadband_forum.obbaa.netconf.api.util.Pair;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.utils.TxService;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.utils.TxTemplate;
import org.broadband_forum.obbaa.nf.dao.NetworkFunctionDao;
import org.broadband_forum.obbaa.nf.dao.impl.KafkaTopicPurpose;
import org.broadband_forum.obbaa.nf.entities.KafkaTopic;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public final class VOLTDhcpManagementUtil {
    private static final Logger LOGGER = Logger.getLogger(VOLTDhcpManagementUtil.class);
    private static final Object LOCK = new Object();
    private static Map<String, Pair<String, String>> m_requestMap = new HashMap<>();
    private static Map<String, ArrayList<String>> m_networkFunctionMap = new HashMap<>();

    private VOLTDhcpManagementUtil() {
        //Not called
    }

    public static String generateRandomMessageId() {
        int randomNumber = (int) (Math.random() * (DhcpConstants.MESSAGE_ID_MAX - DhcpConstants.MESSAGE_ID_MIN + 1)
                + DhcpConstants.MESSAGE_ID_MIN);
        return String.valueOf(randomNumber);
    }


    public static boolean isResponseOK(ArrayList<Boolean> respArray) {
        return (respArray == null || respArray.isEmpty() || respArray.contains(false)) ? false : true;
    }

    public static void updateKafkaSubscriptions(String vomciFunctionName, MessageFormatter messageFormatter, NetworkFunctionDao
            networkFunctionDao, DhcpKafkaConsumer dhcpKafkaConsumer, Map<String, Set<String>> kafkaConsumerTopicMap) {
        if (messageFormatter instanceof GpbFormatter) {
            Set<KafkaTopic> kafkaTopicSet = networkFunctionDao.getKafkaConsumerTopics(vomciFunctionName, KafkaTopicPurpose.DHCP_RESPONSE);
            Set<String> kafkaTopicNameSet = new HashSet<>();
            if (kafkaTopicSet != null && !kafkaTopicSet.isEmpty()) {
                dhcpKafkaConsumer.updateSubscriberTopics(kafkaTopicSet);
                for (KafkaTopic kafkaTopic : kafkaTopicSet) {
                    if (SupportedDhcpKafkaTopicPurpose.getList().contains(kafkaTopic.getPurpose())) {
                        LOGGER.info("topic is supported with dhcp :" + kafkaTopic.getPurpose() + ",added");
                        kafkaTopicNameSet.add(kafkaTopic.getTopicName());
                    } else {
                        LOGGER.info("topic is not supported with dhcp :" + kafkaTopic.getPurpose() + ",not added");
                    }
                }
                kafkaConsumerTopicMap.put(vomciFunctionName, kafkaTopicNameSet);
            }
        }
    }

    public static void removeSubscriptions(String networkFunctionName, DhcpKafkaConsumer dhcpKafkaConsumer,
                                           Map<String, Set<String>> kafkaConsumerTopicMap) {
        if (kafkaConsumerTopicMap.containsKey(networkFunctionName)) {
            Set<String> kafkaTopicNameSet = kafkaConsumerTopicMap.get(networkFunctionName);
            Set<String> topicsToUnsubscribe = new HashSet<>(kafkaTopicNameSet);
            kafkaConsumerTopicMap.remove(networkFunctionName);
            for (String kafkaTopicName : kafkaTopicNameSet) {
                for (Set<String> kafkaTopicNames : kafkaConsumerTopicMap.values()) {
                    if (kafkaTopicNames.contains(kafkaTopicName)) {
                        topicsToUnsubscribe.remove(kafkaTopicName);
                        break;
                    }
                }
            }

            if (!topicsToUnsubscribe.isEmpty()) {
                LOGGER.info("Unsubscribing topics: " + topicsToUnsubscribe);
                dhcpKafkaConsumer.removeSubscriberTopics(topicsToUnsubscribe);
            }
        }
    }

    public static ResponseData updateOperationTypeInResponseData(ResponseData responseData, MessageFormatter messageFormatter) {
        if (messageFormatter instanceof GpbFormatter) {
            String identifier = responseData.getIdentifier();
//            if (responseData.getOperationType().equals(NetconfResources.RPC)
//            }
        }
        return responseData;
    }

    public static void registerInRequestMap(Entity request, String deviceName, String operation) {
        synchronized (LOCK) {
            m_requestMap.put(request.getMessageId(), new Pair<>(deviceName, operation));
        }
    }

    public static void removeRequestFromMap(String identifier) {
        synchronized (LOCK) {
            m_requestMap.remove(identifier);
        }
    }

    public static void setMessageId(Entity request, AtomicLong messageId) {
        final String currentMessageId = String.valueOf(messageId.addAndGet(1));
        request.setMessageId(currentMessageId);
    }

    public static void sendKafkaMessage(Object kafkaMessage, String networkFunctionName,
                                        TxService txService, NetworkFunctionDao networkFunctionDao,
                                        DhcpKafkaProducer dhcpKafkaProducer) {

        AtomicReference<HashSet<String>> kafkaTopicNames = new AtomicReference<>(new HashSet<String>());
        txService.executeWithTxRequired((TxTemplate<Void>) () -> {
            final HashSet<String> kafkaTopicNamesFinal = networkFunctionDao.getKafkaTopicNames(networkFunctionName,
                    KafkaTopicPurpose.DHCP_REQUEST);
            if (kafkaTopicNamesFinal != null && !kafkaTopicNamesFinal.isEmpty()) {
                kafkaTopicNames.set(kafkaTopicNamesFinal);
            } else {
                LOGGER.error(String.format("Topic Name for the Network Function %s and Topic Purpose %s was not found",
                        networkFunctionName, KafkaTopicPurpose.DHCP_REQUEST));
            }
            return null;
        });
        if (!kafkaTopicNames.get().isEmpty()) {
            for (String topicName : kafkaTopicNames.get()) {
                try {
                    dhcpKafkaProducer.sendNotification(topicName, kafkaMessage);
                } catch (MessageFormatterException e) {
                    LOGGER.error(String.format("Failed to send kafka message: %s to topic :%s", kafkaMessage.toString(), topicName, e));
                }
            }
        }
    }
}
