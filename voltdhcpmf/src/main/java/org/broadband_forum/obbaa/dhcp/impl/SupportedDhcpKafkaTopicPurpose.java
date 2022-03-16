package org.broadband_forum.obbaa.dhcp.impl;

import org.apache.commons.collections.CollectionUtils;
import org.broadband_forum.obbaa.nf.dao.impl.KafkaTopicPurpose;

import java.util.HashSet;
import java.util.Set;

public enum SupportedDhcpKafkaTopicPurpose {
    DHCP_NOTIFICATION(KafkaTopicPurpose.DHCP_NOTIFICATION),
    DHCP_REQUEST(KafkaTopicPurpose.DHCP_REQUEST),
    DHCP_RESPONSE(KafkaTopicPurpose.DHCP_RESPONSE);

    SupportedDhcpKafkaTopicPurpose(KafkaTopicPurpose topicPurpose) {
        kafkaTopicPurpose = topicPurpose;
    }

    private KafkaTopicPurpose kafkaTopicPurpose;
    private static Set<String> list = new HashSet<>();

    public static Set<String> getList() {
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        }
        SupportedDhcpKafkaTopicPurpose[] vals = SupportedDhcpKafkaTopicPurpose.values();
        for (SupportedDhcpKafkaTopicPurpose val : vals) {
            list.add(val.kafkaTopicPurpose.toString());
        }
        return list;
    }
}