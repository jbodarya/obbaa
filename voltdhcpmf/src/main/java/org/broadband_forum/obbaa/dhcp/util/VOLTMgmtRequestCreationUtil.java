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

import org.broadband_forum.obbaa.dhcp.DhcpConstants;
import org.broadband_forum.obbaa.dmyang.entities.Device;
import org.broadband_forum.obbaa.netconf.api.messages.ActionRequest;
import org.broadband_forum.obbaa.netconf.api.messages.GetRequest;
import org.broadband_forum.obbaa.netconf.api.messages.NetconfFilter;
import org.broadband_forum.obbaa.netconf.api.messages.NetconfRpcRequest;
import org.broadband_forum.obbaa.netconf.api.util.DocumentUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;


public final class VOLTMgmtRequestCreationUtil {

    private VOLTMgmtRequestCreationUtil() {
        //Not Called
    }

    public static NetconfRpcRequest prepareCreateOnuRequest(String onuDeviceName) {
        Document document = DocumentUtils.createDocument();
        String elementNS = DhcpConstants.BBF_VOMCI_FUNCTION_NS;
        Element createOnuNode = document.createElementNS(elementNS, DhcpConstants.CREATE_ONU);
        Element nameLeaf = document.createElement(DhcpConstants.NAME);
        nameLeaf.setTextContent(onuDeviceName);
        createOnuNode.appendChild(nameLeaf);
        document.appendChild(createOnuNode);
        NetconfRpcRequest request = new NetconfRpcRequest();
        request.setRpcInput(document.getDocumentElement());
        return request;
    }


    public static NetconfRpcRequest prepareDHCPRequest(Map<String, String > map) {

        Document document = DocumentUtils.createDocument();
        String elementNS = DhcpConstants.BBF_VOMCI_FUNCTION_NS;
        Element dhcpValues = document.createElementNS(elementNS, DhcpConstants.DHCP_VALUES);

        map.forEach(
                (k, v) -> {
                    Element nameLeaf = document.createElement(k);
                    nameLeaf.setTextContent(v);
                    dhcpValues.appendChild(nameLeaf);
                }
        );
        document.appendChild(dhcpValues);


//        map.forEach(
//                (k, v ) -> {
//                    appendElement(elementNS, document, dhcpValues, k, v);
//                }
//        );
//        document.appendChild(dhcpValues);

        NetconfRpcRequest request = new NetconfRpcRequest();
        request.setRpcInput(document.getDocumentElement());
        return request;
    }


    public static ActionRequest prepareSetOnuCommunicationRequest(String onuDeviceName, boolean isCommunicationAvailable, String oltName,
                                                                  String channelTermName, String onuId, String voltmfRemoteEpName,
                                                                  String oltRemoteEpName, boolean isMsgToProxy) {
        Document document = DocumentUtils.createDocument();
        String elementNS = DhcpConstants.BBF_VOMCI_FUNCTION_NS;

        Element managedOnus = document.createElementNS(elementNS, DhcpConstants.MANAGED_ONUS);
        Element managedOnu = document.createElementNS(elementNS, DhcpConstants.MANAGED_ONU);
        Element nameLeaf = document.createElementNS(elementNS, DhcpConstants.NAME);
        nameLeaf.setTextContent(onuDeviceName);
        managedOnu.appendChild(nameLeaf);
        managedOnus.appendChild(managedOnu);
        Element setOnuCommunication = document.createElementNS(elementNS, DhcpConstants.SET_ONU_COMMUNICATION);
        managedOnu.appendChild(setOnuCommunication);
        appendElement(elementNS, document, setOnuCommunication, DhcpConstants.SET_ONU_COMM_AVAILABLE,
                String.valueOf(isCommunicationAvailable));
        appendElement(elementNS, document, setOnuCommunication, DhcpConstants.OLT_REMOTE_NAME, oltRemoteEpName);
        String nbRemoteEndpoint;
        if (isMsgToProxy) {
            nbRemoteEndpoint = DhcpConstants.VOMCI_FUNCTION_REMOTE_ENDPOINT;
        } else {
            nbRemoteEndpoint = DhcpConstants.VOLTMF_REMOTE_NAME;
        }
        appendElement(elementNS, document, setOnuCommunication, nbRemoteEndpoint, voltmfRemoteEpName);
        Element onuAttachmentPoint = document.createElementNS(elementNS, DhcpConstants.ONU_ATTACHMENT_POINT);
        appendElement(elementNS, document, onuAttachmentPoint, DhcpConstants.OLT_NAME_JSON_KEY, oltName);
        appendElement(elementNS, document, onuAttachmentPoint, DhcpConstants.CHANNEL_TERMINATION_NAME, channelTermName);
        appendElement(elementNS, document, onuAttachmentPoint, DhcpConstants.ONU_ID_JSON_KEY, onuId);
        setOnuCommunication.appendChild(onuAttachmentPoint);
        document.appendChild(managedOnus);
        ActionRequest request = new ActionRequest();
        request.setActionTreeElement(document.getDocumentElement());
        return request;
    }

    public static Element appendElement(String elementNS, Document document, Element parentElement, String localName,
                                        String... textContents) {
        Element lastElement = null;
        for (String textContent : textContents) {
            Element element = document.createElementNS(elementNS, localName);
            element.setTextContent(textContent);
            parentElement.appendChild(element);
            lastElement = element;
        }
        return lastElement;
    }


    public static HashMap<String, String> getLabels(Device device) {
        HashMap<String, String> labels = new HashMap<>();
        labels.put("name", "vendor");
        labels.put("value", device.getDeviceManagement().getDeviceVendor());
        return labels;
    }
}
