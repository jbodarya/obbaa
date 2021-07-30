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

package org.broadband_forum.obbaa.nf.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.broadband_forum.obbaa.netconf.stack.api.annotations.YangChild;
import org.broadband_forum.obbaa.netconf.stack.api.annotations.YangContainer;
import org.broadband_forum.obbaa.netconf.stack.api.annotations.YangParentId;
import org.broadband_forum.obbaa.netconf.stack.api.annotations.YangSchemaPath;

/**
 * <p>
 * Entity class for GRPC Client
 * </p>
 * Created by Ranjitha.B.R (Nokia) on 10/05/2021.
 */
@Entity
@YangContainer(name = NetworkFunctionNSConstants.GRPC_TRANSPORT, namespace = NetworkFunctionNSConstants.NS,
        revision = NetworkFunctionNSConstants.REVISION)
public class GrpcClient {
    @Id
    @YangParentId
    @Column(name = YangParentId.PARENT_ID_FIELD_NAME)
    private String parentId;

    @YangSchemaPath
    @Column(length = 1000)
    private String schemaPath;

    @YangChild
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private GrpcClientParameters grpcClientParameters;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSchemaPath() {
        return schemaPath;
    }

    public void setSchemaPath(String schemaPath) {
        this.schemaPath = schemaPath;
    }

    public GrpcClientParameters getGrpcClientParameters() {
        return grpcClientParameters;
    }

    public void setGrpcClientParameters(GrpcClientParameters grpcClientParameters) {
        this.grpcClientParameters = grpcClientParameters;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        GrpcClient that = (GrpcClient) other;

        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) {
            return false;
        }
        if (grpcClientParameters != null ? !grpcClientParameters.equals(that.grpcClientParameters) : that.grpcClientParameters != null) {
            return false;
        }
        return schemaPath != null ? schemaPath.equals(that.schemaPath) : that.schemaPath == null;

    }

    @Override
    public int hashCode() {
        int result = parentId != null ? parentId.hashCode() : 0;
        result = 31 * result + (schemaPath != null ? schemaPath.hashCode() : 0);
        result = 31 * result + (grpcClientParameters != null ? grpcClientParameters.hashCode() : 0);
        return result;
    }

}
