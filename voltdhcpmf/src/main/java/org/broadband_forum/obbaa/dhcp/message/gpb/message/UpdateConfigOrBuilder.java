// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: tr451_vomci_nbi_message.proto

package org.broadband_forum.obbaa.dhcp.message.gpb.message;

public interface UpdateConfigOrBuilder extends
        // @@protoc_insertion_point(interface_extends:tr451_vomci_nbi_message.v1.UpdateConfig)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.tr451_vomci_nbi_message.v1.UpdateConfigInstance update_config_inst = 1;</code>
     *
     * @return Whether the updateConfigInst field is set.
     */
    boolean hasUpdateConfigInst();

    /**
     * <code>.tr451_vomci_nbi_message.v1.UpdateConfigInstance update_config_inst = 1;</code>
     *
     * @return The updateConfigInst.
     */
    UpdateConfigInstance getUpdateConfigInst();

    /**
     * <code>.tr451_vomci_nbi_message.v1.UpdateConfigInstance update_config_inst = 1;</code>
     */
    UpdateConfigInstanceOrBuilder getUpdateConfigInstOrBuilder();

    /**
     * <code>.tr451_vomci_nbi_message.v1.UpdateConfigReplica update_config_replica = 2;</code>
     *
     * @return Whether the updateConfigReplica field is set.
     */
    boolean hasUpdateConfigReplica();

    /**
     * <code>.tr451_vomci_nbi_message.v1.UpdateConfigReplica update_config_replica = 2;</code>
     *
     * @return The updateConfigReplica.
     */
    UpdateConfigReplica getUpdateConfigReplica();

    /**
     * <code>.tr451_vomci_nbi_message.v1.UpdateConfigReplica update_config_replica = 2;</code>
     */
    UpdateConfigReplicaOrBuilder getUpdateConfigReplicaOrBuilder();

    public UpdateConfig.ReqTypeCase getReqTypeCase();
}
