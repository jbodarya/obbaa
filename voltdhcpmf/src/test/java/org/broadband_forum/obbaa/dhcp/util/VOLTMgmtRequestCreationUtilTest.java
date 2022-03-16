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

import org.broadband_forum.obbaa.dmyang.entities.Device;
import org.broadband_forum.obbaa.netconf.api.messages.ActionRequest;
import org.broadband_forum.obbaa.netconf.api.messages.NetconfRpcRequest;
import org.broadband_forum.obbaa.netconf.server.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.broadband_forum.obbaa.netconf.server.util.TestUtil.assertXMLEquals;
import static org.junit.Assert.assertNotNull;


public class VOLTMgmtRequestCreationUtilTest {

    private final String ONU_NAME = "onu1";
    private final String OLT_NAME = "OLT1";
    private final String CHANNEL_TERM_REF = "CT_1";
    private final String ONU_ID = "1";

    @Mock
    Device m_onuDevice;
    String createOnuRequestToVomci = "/create-onu-request-to-vomci.xml";
    String setOnuCommVomci = "/set-onu-comm-request-to-vomci.xml";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPrepareCreateOnuRequest() throws IOException, SAXException {
        NetconfRpcRequest vomciRequest = VOLTMgmtRequestCreationUtil.prepareCreateOnuRequest(ONU_NAME);
        assertNotNull(vomciRequest);
        assertXMLEquals(TestUtil.loadAsXml(createOnuRequestToVomci), vomciRequest);
    }


    @Test
    public void testPrepareSetOnuCommunicationRequest() throws IOException, SAXException {
        ActionRequest setOnuCommVomciAction = VOLTMgmtRequestCreationUtil.prepareSetOnuCommunicationRequest(ONU_NAME, true, OLT_NAME, CHANNEL_TERM_REF, ONU_ID, "vOLTMF_Kafka", "proxy-grpc-1", false);
        assertNotNull(setOnuCommVomciAction);
        assertXMLEquals(TestUtil.loadAsXml(setOnuCommVomci), setOnuCommVomciAction);
    }

    private String prepareJsonResponseFromFile(String name) {
        return TestUtil.loadAsString(name).trim();
    }

}
