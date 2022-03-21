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

import org.broadband_forum.obbaa.dhcp.Entity;
import org.broadband_forum.obbaa.dhcp.exception.MessageFormatterException;
import org.broadband_forum.obbaa.dmyang.entities.Device;
import org.broadband_forum.obbaa.netconf.api.util.NetconfMessageBuilderException;

/**
 * <p>
 * Provides APIs to format the messages
 * </p>
 */
public interface MessageFormatter<T> {

    T getFormattedRequest(Entity request, String operationType, Device onuDevice,
                          NetworkWideTag networkWideTag)
            throws NetconfMessageBuilderException, MessageFormatterException;

    ResponseData getResponseData(Object responseObject) throws MessageFormatterException;
}
