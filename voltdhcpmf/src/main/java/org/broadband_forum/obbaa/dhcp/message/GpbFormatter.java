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

package org.broadband_forum.obbaa.dhcp.message;

import com.google.protobuf.ByteString;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.broadband_forum.obbaa.device.adapter.AdapterManager;
import org.broadband_forum.obbaa.dhcp.DhcpConstants;
import org.broadband_forum.obbaa.dhcp.Entity;
import org.broadband_forum.obbaa.dhcp.exception.MessageFormatterException;
import org.broadband_forum.obbaa.dhcp.message.gpb.message.*;
import org.broadband_forum.obbaa.dhcp.message.gpb.message.Header.OBJECT_TYPE;
import org.broadband_forum.obbaa.dhcp.message.gpb.message.Status.StatusCode;
import org.broadband_forum.obbaa.dhcp.util.XmlUtil;
import org.broadband_forum.obbaa.dmyang.entities.Device;
import org.broadband_forum.obbaa.netconf.api.messages.AbstractNetconfRequest;
import org.broadband_forum.obbaa.netconf.api.messages.NetconfRpcRequest;
import org.broadband_forum.obbaa.netconf.api.util.DocumentUtils;
import org.broadband_forum.obbaa.netconf.api.util.NetconfMessageBuilderException;
import org.broadband_forum.obbaa.netconf.api.util.NetconfResources;
import org.broadband_forum.obbaa.netconf.mn.fwk.schema.SchemaRegistry;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.datastore.ModelNodeDataStoreManager;

import java.util.Map;

/**
 * <p>
 * Formatting messages to GPB format from XML or from GPB Format to XML
 * </p>
 */
public class GpbFormatter implements MessageFormatter<Msg> {

    private static final Logger LOGGER = Logger.getLogger(GpbFormatter.class);
    private static final String SENDER_NAME = DhcpConstants.VOLTDHCPMF_NAME;

    @Override
    public Msg getFormattedRequest(Entity e,
                                   String operationType,
                                   Device onuDevice,
                                   AdapterManager adapterManager,
                                   ModelNodeDataStoreManager modelNodeDsm,
                                   SchemaRegistry schemaRegistry,
                                   NetworkWideTag networkWideTag) throws NetconfMessageBuilderException, MessageFormatterException {

        Msg msg = null;
        // These operations can be sent to the ONU, vOMCI-Function or vOMCI-Proxy.
        switch (operationType) {
            case NetconfResources.RPC:
                // below is not working : need to check 
//                msg = getFormattedMessageForRpc((NetconfRpcRequest) request, onuDevice,
//                        adapterManager, modelNodeDsm, networkWideTag);
                msg = getFormattedMessageForRpc(e, schemaRegistry,
                        modelNodeDsm, networkWideTag);
                break;
            default:
                LOGGER.warn("GpbFormatter didn't recognize the operation: " + operationType);
                break;
        }
        return msg;
    }

    @Override
    public ResponseData getResponseData(Object responseObject) throws MessageFormatterException {
        if (responseObject == null) {
            throw new MessageFormatterException("Response received without information.");
        }

        String recipientName = null;
        String senderName = null;
        ObjectType objectType = null;
        String objectName = null;
        String identifier = null;
        String operationType = null;
        String responseStatus = null;
        String failureReason = null;
        String data = null;

        ResponseData responseData = null;

        if (responseObject instanceof Msg) {
            Msg msg = (Msg) responseObject;
            validateResponse(msg);

            recipientName = msg.getHeader().getRecipientName();
            senderName = msg.getHeader().getSenderName();
            objectType = ObjectType.getObjectTypeFromCode(msg.getHeader().getObjectType().getNumber());
            objectName = msg.getHeader().getObjectName();
            identifier = msg.getHeader().getMsgId();
            if (msg.getBody() != null && msg.getBody().getResponse() != null && !msg.getBody().getResponse().toString().isEmpty()) {
                Response response = msg.getBody().getResponse();
                if (response.hasRpcResp()) {
                    operationType = NetconfResources.RPC;
                    data = response.getRpcResp().getOutputData().toStringUtf8();
                    responseStatus = getStatusFromResponse(response.getRpcResp().getStatusResp());
                    failureReason = getErrorCauseFromResponse(response.getRpcResp().getStatusResp());
                } else {
                    throw new MessageFormatterException("The response body does not match with any of the expected.");
                }
            } else if (msg.getBody() != null && msg.getBody().getNotification() != null && !msg.getBody()
                    .getNotification().toString().isEmpty()) {
                Notification notification = msg.getBody().getNotification();
                data = notification.getData().toStringUtf8();
                operationType = NetconfResources.NOTIFICATION;
            } else {
                throw new MessageFormatterException("Response received without information.");
            }
            responseData = new ResponseData(recipientName, senderName, objectType, objectName,
                    identifier, operationType, responseStatus, failureReason, data);
            if (objectType != null && objectType.toString().equals(ObjectType.ONU.name())) {
                responseData.setOnuName(objectName);
            }
        } else {
            throw new MessageFormatterException(String.format("GpbFormatter can only process responses of type '%s'. Given: '%s'.",
                    Msg.class.toString(), responseObject.getClass().toString()));
        }
        return responseData;
    }


    private Msg getFormattedMessageForRpc(Entity e, SchemaRegistry schemaRegistry,
                                          ModelNodeDataStoreManager modelNodeDsm,
                                          NetworkWideTag networkWideTag) throws NetconfMessageBuilderException {
//        String payload = XmlUtil.convertXmlToJson(schemaRegistry, modelNodeDsm,
//                DocumentUtils.documentToPrettyString(request.getRpcInput()));
//
        //String payload = StringUtils.join(map);
        return Msg.newBuilder()
                .setHeader(buildHeader(e, networkWideTag))
                .setBody(Body.newBuilder().setRequest(buildRpcRequest(e.toString())).build())
                .build();
    }

    private Header buildHeader(Entity e, NetworkWideTag networkWideTag) {
        return Header.newBuilder()
                .setMsgId(e.getMessageId())
                .setSenderName(SENDER_NAME)
                .setRecipientName(networkWideTag.getRecipientName())
                .setObjectType(OBJECT_TYPE.forNumber(networkWideTag.getObjectType().getCode()))
                .setObjectName(networkWideTag.getObjectName())
                .build();
    }


    private Request buildRpcRequest(String payload) {
        return Request.newBuilder()
                .setRpc(RPC.newBuilder()
                        .setInputData(ByteString.copyFromUtf8(payload))
                        .build())
                .build();
    }

    private String getStatusFromResponse(Status status) {
        if (status != null && status.getStatusCode() != null) {
            return status.getStatusCode().name();
        }
        return StatusCode.UNRECOGNIZED.name();
    }

    private String getErrorCauseFromResponse(Status status) {
        if (status != null && status.getErrorList() != null && !status.getErrorList().isEmpty()) {
            return status.getErrorList().stream()
                    .map(e -> e.getErrorType() + ":" + e.getErrorMessage())
                    .reduce("Error Messages: ", (acc, element) -> acc + "\n-" + element);
        }
        return null;
    }

    private void validateResponse(Msg msg) throws MessageFormatterException {
        if (msg.getHeader() == null) {
            throw new MessageFormatterException("Message received without information..");
        }
        Header header = msg.getHeader();
        if (!header.getRecipientName().equals(SENDER_NAME)) {
            throw new MessageFormatterException(String.format("The recipient name from response is different than expected. "
                    + "Expected = %s, Received = %s", SENDER_NAME, header.getRecipientName()));
        }
        String objectType = header.getObjectType().toString();
        if (objectType.equals(String.valueOf(OBJECT_TYPE.VOLTMF))) {
            throw new MessageFormatterException(String.format("The object type from response is different than expected. "
                    + "Expected = %s, Received = %s", OBJECT_TYPE.VOMCI_FUNCTION.name(), header.getObjectType().name()));
        }
    }
}