package org.broadband_forum.obbaa.onu.impl;

import org.apache.commons.collections.CollectionUtils;
import org.broadband_forum.obbaa.nf.dao.impl.KafkaTopicPurpose;

import java.util.HashSet;
import java.util.Set;

public enum SupportedKafkaTopicPurpose {
    VOMCI_NOTIFICATION(KafkaTopicPurpose.VOMCI_NOTIFICATION),
    VOMCI_REQUEST(KafkaTopicPurpose.VOMCI_REQUEST),
    VOMCI_RESPONSE(KafkaTopicPurpose.VOMCI_RESPONSE);

    SupportedKafkaTopicPurpose(KafkaTopicPurpose topicPurpose) {
        kafkaTopicPurpose = topicPurpose;
    }

    private KafkaTopicPurpose kafkaTopicPurpose;
    private static Set<String> list = new HashSet<>();

    public static Set<String> getList() {
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        }
        SupportedKafkaTopicPurpose[] vals = SupportedKafkaTopicPurpose.values();
        for (SupportedKafkaTopicPurpose val : vals) {
            list.add(val.kafkaTopicPurpose.toString());
        }
        return list;
    }
}