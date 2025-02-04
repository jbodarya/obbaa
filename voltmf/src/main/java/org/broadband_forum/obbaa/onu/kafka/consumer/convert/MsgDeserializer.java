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

package org.broadband_forum.obbaa.onu.kafka.consumer.convert;

import org.apache.kafka.common.serialization.Deserializer;
import org.broadband_forum.obbaa.onu.message.gpb.message.Msg;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * <p>
 * Deserialize messages received by KafkaConsumerGpb
 * </p>
 * Created by Filipe Cláudio (Altice Labs) on 09/06/2021.
 */
public class MsgDeserializer implements Deserializer<Msg> {

    @Override
    public Msg deserialize(final String topic, byte[] data) {
        try {
            return Msg.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            return null;
        }
    }
}
