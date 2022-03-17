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
 * Constants related to ONU
 * </p>
 */
public interface DhcpConstants {

    String CREATE_ONU = "create-onu";

    String DHCP_VALUES = "dhcp-values";
    String DELETE_ONU = "delete-onu";
    String MANAGED_ONUS = "managed-onus";
    String MANAGED_ONU = "managed-onu";
    String SET_ONU_COMMUNICATION = "set-onu-communication";
    String SET_ONU_COMM_AVAILABLE = "onu-communication-available";
    String OLT_REMOTE_NAME = "olt-remote-endpoint-name";
    String VOLTMF_REMOTE_NAME = "voltmf-remote-endpoint-name";
    String ONU_ATTACHMENT_POINT = "onu-attachment-point";
    String VOLTDHCPMF_NAME = "vOLTDHCPMF";
    String ONU_NAME_JSON_KEY = "onu-name";
    String OLT_NAME_JSON_KEY = "olt-name";
    String CHANNEL_TERMINATION_REF_JSON_KEY = "channel-termination-ref";
    String CHANNEL_TERMINATION_NAME = "channel-termination-name";
    String ONU_ID_JSON_KEY = "onu-id";
    String PAYLOAD_JSON_KEY = "payload";
    String LABELS_JSON_KEY = "labels";
    String IDENTIFIER_JSON_KEY = "identifier";
    String EQPT_ID_JSON_KEY = "equipment-id";
    String OPERATION_JSON_KEY = "operation";
    String FILTERS_JSON_KEY = "filters";
    String STATUS_JSON_KEY = "status";
    String DATA_JSON_KEY = "data";
    String FAILURE_REASON = "failure-reason";
    String TRANSACTION_ID = "transaction_id";
    String EVENT = "event";
    String ONU_CURRENT_CONFIG = "current";
    String ONU_TARGET_CONFIG = "target";
    String ONU_DELTA_CONFIG = "delta";
    String COLON = ":";
    String NETWORK_MANAGER = "network-manager";
    String ROOT = "root";
    String DEFAULT_MESSAGE_ID = "0";
    String NETWORK_MANAGER_JSON_KEY = "network-manager:device-management";
    String DEVICE_STATE_JSON_KEY = "device-state";
    String ONU_STATE_INFO_JSON_KEY = "bbf-obbaa-onu-management:onu-state-info";
    int JSON_INDENT_FACTOR = 4;
    int MESSAGE_ID_MIN = 0;
    int MESSAGE_ID_MAX = 1000;

    String EDIT_CONFIG_SYNCED = "Edit-config already synced";


    // Kafka topics // jatin
    String ONU_REQUEST_KAFKA_TOPIC = "OBBAA_ONU_REQUEST";
    String ONU_RESPONSE_KAFKA_TOPIC = "OBBAA_ONU_RESPONSE";

    // Events
    String REQUEST_EVENT = "request";
    String RESPONSE_EVENT = "response";
    String DETECT_EVENT = "detect";
    String UNDETECT_EVENT = "undetect";

    // Operation type constants
    String ONU_GET_OPERATION = "get";
    String ONU_COPY_OPERATION = "copy-config";
    String ONU_EDIT_OPERATION = "edit-config";

    // Threadpools
    int PROCESS_REQ_RESPONSE_THREADS = 5;
    int PROCESS_NOTIF_RESPONSE_THREADS = 5;
    int PROCESS_NOTIF_REQUEST_THREADS = 5;
    int KAFKA_COMMUNICATION_THREADS = 10;
    int KAFKA_CONSUMER_THREADS = 1;


    String NETWORK_MANAGER_NAMESPACE = "urn:bbf:yang:obbaa:network-manager";
    String OBBAA_NETWORK_MANAGER = "bbf-obbaa-network-manager:network-manager";
    //String BBF_VOMCI_FUNCTION_NS = "urn:bbf:yang:bbf-vomci-function";
    String BBF_VOMCI_FUNCTION_NS = "urn:bbf:yang:bbf-xpongemtcont";

    String SUBTREE_FILTER = "subtree";
    String MANAGED_DEVICES = "managed-devices";
    String DEVICE = "device";
    String NAME = "name";
    String DEVICE_MANAGEMENT = "device-management";
    String DEVICE_STATE = "device-state";
    String ONU_STATE_INFO = "onu-state-info";
    String ONU_STATE_INFO_NAMESPACE = "urn:bbf:yang:obbaa:onu-management";
    String EQUIPEMENT_ID = "equipment-id";
    String SOFTWARE_IMAGES = "software-images";
    String SOFTWARE_IMAGE = "software-image";
    String GET_FILTER = "{\"network-manager:device-management\":{\"device-state\":{\"bbf-obbaa-onu-management:onu-state-info\""
            + ":{\"equipment-id\":\"\",\"software-images\":{\"software-image\":{}}}}}}";
    String VOMCI_FUNCTION_REMOTE_ENDPOINT = "vomci-func-remote-endpoint-name";

}
