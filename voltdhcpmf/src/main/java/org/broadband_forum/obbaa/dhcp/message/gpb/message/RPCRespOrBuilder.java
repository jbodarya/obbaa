// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: tr451_vomci_nbi_message.proto

package org.broadband_forum.obbaa.dhcp.message.gpb.message;

public interface RPCRespOrBuilder extends
        // @@protoc_insertion_point(interface_extends:tr451_vomci_nbi_message.v1.RPCResp)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
     *
     * @return Whether the statusResp field is set.
     */
    boolean hasStatusResp();

    /**
     * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
     *
     * @return The statusResp.
     */
    Status getStatusResp();

    /**
     * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
     */
    StatusOrBuilder getStatusRespOrBuilder();

    /**
     * <code>bytes output_data = 2;</code>
     *
     * @return The outputData.
     */
    com.google.protobuf.ByteString getOutputData();
}
