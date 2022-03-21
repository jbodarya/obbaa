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

package org.broadband_forum.obbaa.dhcp;

/**
 * <p>
 * Constants
 * </p>
 */
public interface DhcpConstants {

    String CREATE_ONU = "create-onu";

    String DHCP_VALUES = "dhcp-values";
    String VOLTDHCPMF_NAME = "vOLTDHCPMF";
    String PAYLOAD_JSON_KEY = "payload";
    String EQPT_ID_JSON_KEY = "equipment-id";
    String STATUS_JSON_KEY = "status";
    String DATA_JSON_KEY = "data";
    String FAILURE_REASON = "failure-reason";
    String TRANSACTION_ID = "transaction_id";
    String EVENT = "event";
    String COLON = ":";
    String ROOT = "root";
    String DEFAULT_MESSAGE_ID = "0";
    int MESSAGE_ID_MIN = 0;
    int MESSAGE_ID_MAX = 1000;

    // Events
    String REQUEST_EVENT = "request";
    String RESPONSE_EVENT = "response";

    // Threadpools
    int PROCESS_REQ_RESPONSE_THREADS = 5;
    int PROCESS_NOTIF_RESPONSE_THREADS = 5;
    int PROCESS_NOTIF_REQUEST_THREADS = 5;
    int KAFKA_COMMUNICATION_THREADS = 10;
    int KAFKA_CONSUMER_THREADS = 1;
}
