/*
 * Copyright 2018 Broadband Forum
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

package org.broadband_forum.obbaa.standard.model;


import org.apache.commons.lang3.StringUtils;
import org.broadband_forum.obbaa.connectors.sbi.netconf.NetconfConnectionManager;
import org.broadband_forum.obbaa.device.adapter.DeviceInterface;
import org.broadband_forum.obbaa.dmyang.entities.ConnectionState;
import org.broadband_forum.obbaa.dmyang.entities.Device;
import org.broadband_forum.obbaa.netconf.alarm.util.AlarmConstants;
import org.broadband_forum.obbaa.netconf.api.messages.AbstractNetconfRequest;
import org.broadband_forum.obbaa.netconf.api.messages.CopyConfigRequest;
import org.broadband_forum.obbaa.netconf.api.messages.EditConfigElement;
import org.broadband_forum.obbaa.netconf.api.messages.EditConfigErrorOptions;
import org.broadband_forum.obbaa.netconf.api.messages.EditConfigRequest;
import org.broadband_forum.obbaa.netconf.api.messages.EditConfigTestOptions;
import org.broadband_forum.obbaa.netconf.api.messages.GetConfigRequest;
import org.broadband_forum.obbaa.netconf.api.messages.GetRequest;
import org.broadband_forum.obbaa.netconf.api.messages.NetConfResponse;
import org.broadband_forum.obbaa.netconf.api.messages.NetconfNotification;
import org.broadband_forum.obbaa.netconf.api.messages.NetconfRpcError;
import org.broadband_forum.obbaa.netconf.api.messages.NetconfRpcErrorSeverity;
import org.broadband_forum.obbaa.netconf.api.messages.NetconfRpcErrorTag;
import org.broadband_forum.obbaa.netconf.api.messages.NetconfRpcErrorType;
import org.broadband_forum.obbaa.netconf.api.messages.Notification;
import org.broadband_forum.obbaa.netconf.api.util.DocumentUtils;
import org.broadband_forum.obbaa.netconf.api.util.NetconfMessageBuilderException;
import org.broadband_forum.obbaa.netconf.api.util.Pair;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.SubSystemValidationException;
import org.opendaylight.yangtools.yang.common.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.broadband_forum.obbaa.dhcp.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ZyxelModelDeviceInterface implements DeviceInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZyxelModelDeviceInterface.class);
    private NetconfConnectionManager m_ncm;
    private VOLTDhcpManagement voltManagement;
    public static final String IETF_ALARM_NS = "urn:ietf:params:xml:ns:yang:ietf-alarms";

    public ZyxelModelDeviceInterface(NetconfConnectionManager ncm,VOLTDhcpManagement voltManagement) {
        this.m_ncm = ncm;
        this.voltManagement=voltManagement;
    }

    @Override
    public Future<NetConfResponse> align(Device device, EditConfigRequest request, NetConfResponse getConfigResponse)
            throws ExecutionException {
        if (m_ncm.isConnected(device)) {
            LOGGER.info(String.format("Inside My Adapter : %s", device.getDeviceName()));
            EditConfigElement editConfigElement = request.getConfigElement();
            //Removing hardware node Start
            List<Element> configElementList = editConfigElement.getConfigElementContents();
            for (int i = 0; i < configElementList.size(); i++) {
                if (configElementList.get(i).getNodeName().contains("bbf-xpongemtcont:xpongemtcont")) {
                    voltManagement.processApplicationRequest(request);
                    break;
                }
            }
            return m_ncm.executeNetconf(device.getDeviceName(), request);
        } else {
            throw new IllegalStateException(String.format("Device not connected %s", device.getDeviceName()));
        }
    }

    @Override
    public Pair<AbstractNetconfRequest, Future<NetConfResponse>> forceAlign(Device device, NetConfResponse getConfigResponse)
            throws NetconfMessageBuilderException, ExecutionException {

        // if it is infra call -> voltmf.test (infra call);
        voltManagement.test();
        if (m_ncm.isConnected(device)) {
            LOGGER.info(String.format("Inside My Adapter : %s", device.getDeviceName()));
            CopyConfigRequest ccRequest = new CopyConfigRequest();
            EditConfigElement config = new EditConfigElement();
            config.setConfigElementContents(getConfigResponse.getDataContent());
            ccRequest.setSourceConfigElement(config.getXmlElement());
            ccRequest.setTargetRunning();
            Future<NetConfResponse> responseFuture = m_ncm.executeNetconf(device.getDeviceName(), ccRequest);
            return new Pair<>(ccRequest, responseFuture);
        } else {
            throw new IllegalStateException(String.format("Device not connected %s", device.getDeviceName()));
        }
    }

    private List<Element> translate(NodeList listOfNodes) {
        List<Element> elementList = new ArrayList<>();
        for (int i = 0; i < listOfNodes.getLength(); i++) {
            Element dataNode = (Element) listOfNodes.item(i);
            if (dataNode.getLocalName().equalsIgnoreCase("interfaces")) {
                for (Element element : DocumentUtils.getChildElements(listOfNodes.item(i))) {
                    if (element.getLocalName().equalsIgnoreCase("interface")) {
                        for (Element child : DocumentUtils.getChildElements(element)) {
                            if (child.getLocalName().equalsIgnoreCase("description")) {
                                element.removeChild(child);
                            }
                        }
                    }
                }
            }
            elementList.add(dataNode);
        }
        return elementList;
    }

    @Override
    public Future<NetConfResponse> get(Device device, GetRequest getRequest) throws ExecutionException {
        if (m_ncm.isConnected(device)) {
            return m_ncm.executeNetconf(device, getRequest);
        } else {
            throw new IllegalStateException(String.format("Device %s is not connected", device.getDeviceName()));
        }
    }

    @Override
    public void veto(Device device, EditConfigRequest request, Document oldDataStore, Document updatedDataStore)
            throws SubSystemValidationException {
    }

    @Override
    public Future<NetConfResponse> getConfig(Device device, GetConfigRequest getConfigRequest) throws ExecutionException {
        if (m_ncm.isConnected(device)) {
            return m_ncm.executeNetconf(device, getConfigRequest);
        } else {
            throw new IllegalStateException(String.format("Device %s is not connected", device.getDeviceName()));
        }
    }

    @Override
    public ConnectionState getConnectionState(Device device) {
        return m_ncm.getConnectionState(device);
    }

    @Override
    public Notification normalizeNotification(Notification notification) {
        //check the notification type is alarm-notification
        return notification;
    }
}
