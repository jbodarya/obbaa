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
package org.broadband_forum.obbaa.nm.requestmanager.impl;

import org.broadband_forum.obbaa.netconf.api.messages.AbstractNetconfRequest;
import org.broadband_forum.obbaa.nm.requestmanager.RequestFunctionManager;
import org.broadband_forum.obbaa.nm.requestmanager.RequestStateProvider;

import java.util.LinkedHashSet;
import java.util.Set;

public class RequestFunctionManagerImpl implements RequestFunctionManager {

    private Set<RequestStateProvider> m_networkFunctionStateProviders = new LinkedHashSet<>();

    @Override
    public void removeRequestFunctionStateProvider(RequestStateProvider stateProvider) {
        m_networkFunctionStateProviders.remove(stateProvider);
    }

    @Override
    public void addRequestFunctionStateProvider(RequestStateProvider stateProvider) {
        m_networkFunctionStateProviders.add(stateProvider);
    }

    @Override
    public void addRequest(AbstractNetconfRequest request) {
        m_networkFunctionStateProviders.stream()
                .filter( provider -> provider.supports(request))
                .forEach( provider -> provider.addRequest(request));
    }

    @Override
    public void delRequest(AbstractNetconfRequest request) {
        m_networkFunctionStateProviders.stream()
                .filter( provider -> provider.supports(request))
                .forEach( provider -> provider.delRequest(request));
    }
}
