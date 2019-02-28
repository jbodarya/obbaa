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

package org.broadband_forum.obbaa.dm;

import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.ADAPTER_REVISION;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.AUTH_ID_TEMPLATE;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.CONFIGURATION_ALIGNMENT_STATE;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.CONNECTED;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.CONNECTION_CREATION_TIME;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.CONNECTION_STATE;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.DESCRIPTION;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.DEVELOPER;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.DEVICE;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.DEVICES_RELATED;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.DEVICE_ADAPTER;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.DEVICE_ADAPTERS;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.DEVICE_ADAPTERS_SP;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.DEVICE_ADAPTER_COUNT;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.DEVICE_CAPABILITY;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.DEVICE_COUNT;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.DEVICE_ID_TEMPLATE;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.DEVICE_STATE;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.DUID;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.INTERFACE_VERSION;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.IN_USE;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.IS_NETCONF;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.MANAGED_DEVICES_ID_TEMPLATE;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.MANAGED_DEVICES_SP;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.MODEL;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.MODULE;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.NETWORK_MANAGER_ID_TEMPLATE;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.NEW_DEVICE;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.NEW_DEVICES;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.NEW_DEVICES_SP;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.NS;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.TYPE;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.UPLOAD_DATE;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.VENDOR;
import static org.broadband_forum.obbaa.dmyang.entities.DeviceManagerNSConstants.YANG_MODULES;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.broadband_forum.obbaa.connectors.sbi.netconf.NetconfConnectionManager;
import org.broadband_forum.obbaa.connectors.sbi.netconf.NewDeviceInfo;
import org.broadband_forum.obbaa.device.adapter.AdapterContext;
import org.broadband_forum.obbaa.device.adapter.AdapterManager;
import org.broadband_forum.obbaa.device.adapter.AdapterUtils;
import org.broadband_forum.obbaa.device.adapter.DeviceAdapter;
import org.broadband_forum.obbaa.device.adapter.DeviceAdapterId;
import org.broadband_forum.obbaa.dmyang.entities.ConnectionState;
import org.broadband_forum.obbaa.dmyang.entities.Device;
import org.broadband_forum.obbaa.dmyang.entities.DeviceMgmt;
import org.broadband_forum.obbaa.dmyang.entities.DeviceState;
import org.broadband_forum.obbaa.netconf.api.util.DocumentUtils;
import org.broadband_forum.obbaa.netconf.api.util.Pair;
import org.broadband_forum.obbaa.netconf.mn.fwk.schema.SchemaRegistry;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.AbstractSubSystem;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.ChangeNotification;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.EditConfigChangeNotification;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.EditContainmentNode;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.FilterNode;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.GetAttributeException;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.ModelNodeChange;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.ModelNodeChangeType;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.ModelNodeId;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.util.SubtreeFilterUtil;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.model.api.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DeviceManagementSubsystem extends AbstractSubSystem {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceManagementSubsystem.class);
    private DeviceManager m_deviceManager;
    private SchemaRegistry m_schemaRegistry;
    private SubtreeFilterUtil m_subtreeFilterUtil;
    private NetconfConnectionManager m_connectionManager;
    private AdapterManager m_adapterManager;

    public DeviceManagementSubsystem(SchemaRegistry schemaRegistry, AdapterManager adapterManager) {
        m_schemaRegistry = schemaRegistry;
        m_adapterManager = adapterManager;
        m_subtreeFilterUtil = new SubtreeFilterUtil(m_schemaRegistry);
    }

    public DeviceManager getDeviceManager() {
        return m_deviceManager;
    }

    public void setDeviceManager(DeviceManager deviceManager) {
        m_deviceManager = deviceManager;
    }

    public NetconfConnectionManager getConnectionManager() {
        return m_connectionManager;
    }

    public void setConnectionManager(NetconfConnectionManager connectionManager) {
        m_connectionManager = connectionManager;
    }

    @Override
    public void notifyChanged(List<ChangeNotification> changeNotificationList) {
        LOGGER.debug("notification received : {}", changeNotificationList);
        for (ChangeNotification notification : changeNotificationList) {
            EditConfigChangeNotification editNotif = (EditConfigChangeNotification) notification;
            LOGGER.debug("notification received : {}", editNotif);
            ModelNodeId nodeId = editNotif.getModelNodeId();
            EditContainmentNode changeData = editNotif.getChange().getChangeData();
            if (nodeId.equals(MANAGED_DEVICES_ID_TEMPLATE) && "device".equals(editNotif.getChange().getChangeData().getName())) {
                LOGGER.debug("ModelNodeId[{}] matched device holder template", nodeId);
                handleDeviceCreateOrDelete(nodeId, editNotif);
            } else if (nodeId.matchesTemplate(AUTH_ID_TEMPLATE)) {
                handleAuthChanged(nodeId.getRdnValue("name"), changeData);
            }
        }
    }

    private void handleAuthChanged(String deviceName, EditContainmentNode editNotif) {
        m_deviceManager.devicePropertyChanged(deviceName);
    }

    private void handleDeviceCreateOrDelete(ModelNodeId nodeId, EditConfigChangeNotification editNotif) {
        LOGGER.debug(null, "Handle device create or delete for ModelNodeId[{}] with notification[{}]", nodeId, editNotif);
        ModelNodeChange deviceChange = editNotif.getChange();
        String deviceId = deviceChange.getChangeData().getMatchNodes().get(0).getValue();
        // device added
        if (editNotif.getChange().getChangeType().equals(ModelNodeChangeType.create)) {
            LOGGER.debug(null, "Device create identified for  ModelNodeId[{}] with notification[{}]", nodeId, editNotif);
            m_deviceManager.deviceAdded(deviceId);
        } else if (editNotif.getChange().getChangeType().equals(ModelNodeChangeType.delete)
            || editNotif.getChange().getChangeType().equals(ModelNodeChangeType.remove)) {

            LOGGER.debug(null, "Device delete identified for ModelNodeId[{}] with notification[{}]", nodeId, editNotif);
            m_deviceManager.deviceRemoved(deviceId);

        }
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = {RuntimeException.class,Exception.class})
    protected Map<ModelNodeId, List<Element>> retrieveStateAttributes(Map<ModelNodeId, Pair<List<QName>,
        List<FilterNode>>> mapAttributes) throws GetAttributeException {
        Map<ModelNodeId, List<Element>> stateInfo = new HashMap<>();
        try {
            Document document = DocumentUtils.createDocument();
            for (Map.Entry<ModelNodeId, Pair<List<QName>, List<FilterNode>>> entry : mapAttributes.entrySet()) {
                List<Element> stateElements = new ArrayList<>();
                ModelNodeId nodeId = entry.getKey();
                List<FilterNode> filters = entry.getValue().getSecond();
                Element deviceStateElement = null;
                Element newDevicesElement = null;
                Element deviceAdapterElement = null;
                if (nodeId.matchesTemplate(DEVICE_ID_TEMPLATE)) {
                    Device device = getDevice(nodeId.getRdnValue("name"));
                    if (device != null) {
                        deviceStateElement = buildDeviceStateElement(document, device);
                    }
                } else if (nodeId.matchesTemplate(NETWORK_MANAGER_ID_TEMPLATE)) {
                    List<NewDeviceInfo> newDeviceInfos = m_connectionManager.getNewDevices();
                    List<DeviceAdapter> adapters = new ArrayList<>(m_adapterManager.getAllDeviceAdapters());
                    if (!newDeviceInfos.isEmpty()) {
                        newDevicesElement = buildNewDeviceStateElement(document, newDeviceInfos);
                    }
                    deviceAdapterElement = buildAdaptersStateElement(document, adapters);
                }
                for (FilterNode filter : filters) {
                    if (DEVICE_STATE.equals(filter.getNodeName()) && NS.equals(filter.getNamespace())) {
                        Element filteredDeviceStateElement = document.createElementNS(NS, DEVICE_STATE);
                        m_subtreeFilterUtil.doFilter(document, filter, m_schemaRegistry.getDataSchemaNode(MANAGED_DEVICES_SP),
                            deviceStateElement, filteredDeviceStateElement);
                        deviceStateElement = filteredDeviceStateElement;
                        if (deviceStateElement != null) {
                            stateElements.add(deviceStateElement);
                        }
                    } else if (NEW_DEVICES.equals(filter.getNodeName()) && NS.equals(filter.getNamespace())) {
                        Element filteredNewDevicesElement = document.createElementNS(NS, NEW_DEVICES);
                        m_subtreeFilterUtil.doFilter(document, filter, m_schemaRegistry.getDataSchemaNode(NEW_DEVICES_SP),
                            newDevicesElement, filteredNewDevicesElement);
                        newDevicesElement = filteredNewDevicesElement;
                        if (newDevicesElement != null) {
                            stateElements.add(newDevicesElement);
                        }
                    } else if (DEVICE_ADAPTERS.equals(filter.getNodeName()) && NS.equals(filter.getNamespace())) {
                        Element filteredDeviceAdaptersElement = document.createElementNS(NS, DEVICE_ADAPTERS);
                        m_subtreeFilterUtil.doFilter(document, filter, m_schemaRegistry.getDataSchemaNode(DEVICE_ADAPTERS_SP),
                            deviceAdapterElement, filteredDeviceAdaptersElement);
                        deviceAdapterElement = filteredDeviceAdaptersElement;
                        if (deviceAdapterElement != null) {
                            stateElements.add(deviceAdapterElement);
                        }
                    }
                    stateInfo.put(nodeId, stateElements);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error in retrieveStateAttributes: ", e);
            throw e;
        }
        return stateInfo;
    }

    private Element buildAdaptersStateElement(Document document, List<DeviceAdapter> adapters) {
        Element deviceAdapters = document.createElementNS(NS, DEVICE_ADAPTERS);
        appendElement(document, deviceAdapters, DEVICE_ADAPTER_COUNT, String.valueOf(adapters.size()));
        for (DeviceAdapter info : adapters) {
            Element deviceAdapter = document.createElementNS(NS, DEVICE_ADAPTER);
            buildAdapterBasicInfo(document, deviceAdapter, info);
            buildAdapterRelatedDevices(document, deviceAdapter, info);
            buildAdapterYangModuleInfo(document, deviceAdapter, info);
            deviceAdapters.appendChild(deviceAdapter);
        }
        return deviceAdapters;
    }

    private void buildAdapterBasicInfo(Document document, Element parentElement, DeviceAdapter adapter) {
        appendElement(document, parentElement, TYPE, adapter.getType());
        appendElement(document, parentElement, INTERFACE_VERSION, adapter.getInterfaceVersion());
        appendElement(document, parentElement, MODEL, adapter.getModel());
        appendElement(document, parentElement, VENDOR, adapter.getVendor());
        appendElement(document, parentElement, DESCRIPTION, genAdapterDescription(adapter));
        appendElement(document, parentElement, IS_NETCONF, adapter.getNetconf().toString());

        if (adapter.getDeveloper() != null) {
            appendElement(document, parentElement, DEVELOPER, adapter.getDeveloper());
        }
        if (CollectionUtils.isNotEmpty(adapter.getRevisions())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            String date = sdf.format(adapter.getRevisions().get(0).getTime());
            appendElement(document, parentElement, ADAPTER_REVISION, date);
        }
        if (adapter.getLastUpdateTime() != null) {
            appendElement(document, parentElement, UPLOAD_DATE, adapter.getLastUpdateTime());
        }
    }

    private String genAdapterDescription(DeviceAdapter adapter) {
        return String.format("This is an adapter for %s.%s provided by %s",
                adapter.getModel(), adapter.getType(), adapter.getVendor());
    }

    private void buildAdapterYangModuleInfo(Document document, Element parentElement, DeviceAdapter deviceAdapter) {
        AdapterContext adapterContext = m_adapterManager.getAdapterContext(deviceAdapter.getDeviceAdapterId());
        if (adapterContext == null) {
            return;
        }
        Set<Module> yangModules = adapterContext.getSchemaRegistry().getAllModules();
        if (CollectionUtils.isEmpty(yangModules)) {
            return;
        }

        Element yangModulesContainer = document.createElementNS(NS, YANG_MODULES);
        yangModules.stream().forEach(module -> {
            Element moduleList = document.createElementNS(NS, MODULE);
            appendElement(document, moduleList, "name", module.getName());
            module.getRevision().ifPresent(
                revision -> appendElement(document, moduleList, "revision", revision.toString()));
            yangModulesContainer.appendChild(moduleList);
        });
        parentElement.appendChild(yangModulesContainer);
    }

    private void buildAdapterRelatedDevices(Document document, Element parentElement, DeviceAdapter deviceAdapter) {
        Map<DeviceAdapterId, Set<Device>> adapter2DevicesMap = getAdapter2RelatedDevicesMap();
        DeviceAdapterId deviceAdapterId = deviceAdapter.getDeviceAdapterId();
        Set<Device> deviceSet = adapter2DevicesMap.get(deviceAdapterId);

        if (deviceSet == null || deviceSet.isEmpty()) {
            appendElement(document, parentElement, IN_USE, "false");
            return;
        }

        Element relDevicesEl = document.createElementNS(NS, DEVICES_RELATED);
        if (deviceSet.size() > 0) {
            appendElement(document, parentElement, IN_USE, "true");
            appendElement(document, relDevicesEl, DEVICE_COUNT, String.valueOf(deviceSet.size()));
            deviceSet.forEach(device -> appendElement(document, relDevicesEl, DEVICE, device.getDeviceName()));
        }
        parentElement.appendChild(relDevicesEl);
    }

    private Element buildNewDeviceStateElement(Document document, List<NewDeviceInfo> newDeviceInfos) {
        if (!newDeviceInfos.isEmpty()) {
            Element newdevices = document.createElementNS(NS,NEW_DEVICES);
            for (NewDeviceInfo info : newDeviceInfos) {
                Element newDevice = document.createElementNS(NS, NEW_DEVICE);
                appendElement(document, newDevice, DUID, info.getDuid());
                Set<String> deviceCaps = info.getCapabilities();
                appendElement(document, newDevice, DEVICE_CAPABILITY,
                    deviceCaps.toArray(new String[deviceCaps.size()]));


                newdevices.appendChild(newDevice);
            }
            return newdevices;
        }
        return null;
    }

    private DeviceAdapterId getAdapterId(String type, String interfaceVersion, String model, String vendor) {
        return new DeviceAdapterId(type, interfaceVersion, model, vendor);
    }

    private Map<DeviceAdapterId, Set<Device>> getAdapter2RelatedDevicesMap() {

        Map<DeviceAdapterId, Set<Device>> adapter2DevicesMap = new HashMap<>();

        List<Device> allDevices = new ArrayList<>(getDeviceManager().getAllDevices());
        for (Device device : allDevices) {

            DeviceMgmt deviceMgmt = device.getDeviceManagement();
            DeviceAdapterId adapterId = getAdapterId(deviceMgmt.getDeviceType(), deviceMgmt.getDeviceInterfaceVersion(),
                    deviceMgmt.getDeviceModel(), deviceMgmt.getDeviceVendor());
            Set<Device> relatedDevices = adapter2DevicesMap
                    .computeIfAbsent(adapterId, s -> new HashSet<>());
            relatedDevices.add(device);
        }
        return adapter2DevicesMap;
    }

    private Element buildDeviceStateElement(Document document, Device device) {
        DeviceState deviceState = device.getDeviceManagement().getDeviceState();
        if (deviceState != null) {
            Element deviceStateElem = document.createElementNS(NS, DEVICE_STATE);
            appendElement(document, deviceStateElem, CONFIGURATION_ALIGNMENT_STATE,
                String.valueOf(deviceState.getConfigAlignmentState()));
            Element connectionState = document.createElementNS(NS, CONNECTION_STATE);
            AdapterContext context = AdapterUtils.getAdapterContext(device, m_adapterManager);
            if (context == null) {
                LOGGER.error(String.format("The adapter is not deployed for the device : %s", device.getDeviceName()));
                return null;
            }
            ConnectionState state = context.getDeviceInterface().getConnectionState(device);
            appendElement(document, connectionState, CONNECTED,
                String.valueOf(state.isConnected()));
            appendElement(document, connectionState, CONNECTION_CREATION_TIME,
                state.getConnectionCreationTimeFormat());
            Set<String> deviceCaps = state.getDeviceCapability();
            appendElement(document, connectionState, DEVICE_CAPABILITY,
                deviceCaps.toArray(new String[deviceCaps.size()]));
            deviceStateElem.appendChild(connectionState);
            return deviceStateElem;
        }
        return null;
    }

    private Element appendElement(Document document, Element parentElement, String localName, String... textContents) {
        Element lastElement = null;
        for (String textContent : textContents) {
            Element element = document.createElementNS(NS, localName);
            element.setTextContent(textContent);
            parentElement.appendChild(element);
            lastElement = element;
        }
        return lastElement;
    }

    private Device getDevice(String device) {
        return m_deviceManager.getDevice(device);
    }
}
