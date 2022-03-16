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

import org.broadband_forum.obbaa.nf.dao.NetworkFunctionDao;
import org.broadband_forum.obbaa.nm.nwfunctionmgr.NetworkFunctionManager;
import org.broadband_forum.obbaa.nm.nwfunctionmgr.NetworkFunctionStateProvider;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Listens to events such as device addition and deletion from mediated devices
 * </p>
 */
public class MediateDhcpNetworkEventListener implements NetworkFunctionStateProvider {

    private final VOLTDhcpManagement m_voltDhcpManagement;
    private final NetworkFunctionManager m_networkFunctionManager;
    private final NetworkFunctionDao m_networkFunctionDao;
    private static final List<String> types = Arrays.asList("bbf-nf-types:dolt-dhcpra-type");

    public MediateDhcpNetworkEventListener(VOLTDhcpManagement voltDhcpManagement,
                                           NetworkFunctionManager networkFunctionManager, NetworkFunctionDao networkFunctionDao) {
        m_voltDhcpManagement = voltDhcpManagement;
        m_networkFunctionManager = networkFunctionManager;
        m_networkFunctionDao = networkFunctionDao;

    }

    public void init() {
        m_networkFunctionManager.addNetworkFunctionStateProvider(this);
    }

    public void destroy() {
        m_networkFunctionManager.removeNetworkFunctionStateProvider(this);
    }

    @Override
    public void networkFunctionAdded(String networkFunctionName) {
        if (supports(networkFunctionName, types, m_networkFunctionDao)) {
            m_voltDhcpManagement.networkFunctionAdded(networkFunctionName);
        }
    }

    @Override
    public void networkFunctionRemoved(String networkFunctionName) {
        if (supports(networkFunctionName, types, m_networkFunctionDao)) {
            m_voltDhcpManagement.networkFunctionRemoved(networkFunctionName);
        }
    }
}
