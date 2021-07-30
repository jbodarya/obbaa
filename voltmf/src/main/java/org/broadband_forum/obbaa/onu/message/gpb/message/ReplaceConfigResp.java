// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: tr451_vomci_nbi_message.proto

package org.broadband_forum.obbaa.onu.message.gpb.message;

/**
 * Protobuf type {@code tr451_vomci_nbi_message.v1.ReplaceConfigResp}
 */
public final class ReplaceConfigResp extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:tr451_vomci_nbi_message.v1.ReplaceConfigResp)
    ReplaceConfigRespOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ReplaceConfigResp.newBuilder() to construct.
  private ReplaceConfigResp(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ReplaceConfigResp() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new ReplaceConfigResp();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ReplaceConfigResp(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            org.broadband_forum.obbaa.onu.message.gpb.message.Status.Builder subBuilder = null;
            if (statusResp_ != null) {
              subBuilder = statusResp_.toBuilder();
            }
            statusResp_ = input.readMessage(org.broadband_forum.obbaa.onu.message.gpb.message.Status.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(statusResp_);
              statusResp_ = subBuilder.buildPartial();
            }

            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.broadband_forum.obbaa.onu.message.gpb.message.Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_ReplaceConfigResp_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.broadband_forum.obbaa.onu.message.gpb.message.Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_ReplaceConfigResp_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp.class, org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp.Builder.class);
  }

  public static final int STATUS_RESP_FIELD_NUMBER = 1;
  private org.broadband_forum.obbaa.onu.message.gpb.message.Status statusResp_;
  /**
   * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
   * @return Whether the statusResp field is set.
   */
  @java.lang.Override
  public boolean hasStatusResp() {
    return statusResp_ != null;
  }
  /**
   * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
   * @return The statusResp.
   */
  @java.lang.Override
  public org.broadband_forum.obbaa.onu.message.gpb.message.Status getStatusResp() {
    return statusResp_ == null ? org.broadband_forum.obbaa.onu.message.gpb.message.Status.getDefaultInstance() : statusResp_;
  }
  /**
   * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
   */
  @java.lang.Override
  public org.broadband_forum.obbaa.onu.message.gpb.message.StatusOrBuilder getStatusRespOrBuilder() {
    return getStatusResp();
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (statusResp_ != null) {
      output.writeMessage(1, getStatusResp());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (statusResp_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getStatusResp());
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp)) {
      return super.equals(obj);
    }
    org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp other = (org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp) obj;

    if (hasStatusResp() != other.hasStatusResp()) return false;
    if (hasStatusResp()) {
      if (!getStatusResp()
          .equals(other.getStatusResp())) return false;
    }
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasStatusResp()) {
      hash = (37 * hash) + STATUS_RESP_FIELD_NUMBER;
      hash = (53 * hash) + getStatusResp().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code tr451_vomci_nbi_message.v1.ReplaceConfigResp}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:tr451_vomci_nbi_message.v1.ReplaceConfigResp)
      org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigRespOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.broadband_forum.obbaa.onu.message.gpb.message.Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_ReplaceConfigResp_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.broadband_forum.obbaa.onu.message.gpb.message.Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_ReplaceConfigResp_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp.class, org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp.Builder.class);
    }

    // Construct using org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (statusRespBuilder_ == null) {
        statusResp_ = null;
      } else {
        statusResp_ = null;
        statusRespBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.broadband_forum.obbaa.onu.message.gpb.message.Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_ReplaceConfigResp_descriptor;
    }

    @java.lang.Override
    public org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp getDefaultInstanceForType() {
      return org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp.getDefaultInstance();
    }

    @java.lang.Override
    public org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp build() {
      org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp buildPartial() {
      org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp result = new org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp(this);
      if (statusRespBuilder_ == null) {
        result.statusResp_ = statusResp_;
      } else {
        result.statusResp_ = statusRespBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp) {
        return mergeFrom((org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp other) {
      if (other == org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp.getDefaultInstance()) return this;
      if (other.hasStatusResp()) {
        mergeStatusResp(other.getStatusResp());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private org.broadband_forum.obbaa.onu.message.gpb.message.Status statusResp_;
    private com.google.protobuf.SingleFieldBuilderV3<
        org.broadband_forum.obbaa.onu.message.gpb.message.Status, org.broadband_forum.obbaa.onu.message.gpb.message.Status.Builder, org.broadband_forum.obbaa.onu.message.gpb.message.StatusOrBuilder> statusRespBuilder_;
    /**
     * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
     * @return Whether the statusResp field is set.
     */
    public boolean hasStatusResp() {
      return statusRespBuilder_ != null || statusResp_ != null;
    }
    /**
     * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
     * @return The statusResp.
     */
    public org.broadband_forum.obbaa.onu.message.gpb.message.Status getStatusResp() {
      if (statusRespBuilder_ == null) {
        return statusResp_ == null ? org.broadband_forum.obbaa.onu.message.gpb.message.Status.getDefaultInstance() : statusResp_;
      } else {
        return statusRespBuilder_.getMessage();
      }
    }
    /**
     * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
     */
    public Builder setStatusResp(org.broadband_forum.obbaa.onu.message.gpb.message.Status value) {
      if (statusRespBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        statusResp_ = value;
        onChanged();
      } else {
        statusRespBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
     */
    public Builder setStatusResp(
        org.broadband_forum.obbaa.onu.message.gpb.message.Status.Builder builderForValue) {
      if (statusRespBuilder_ == null) {
        statusResp_ = builderForValue.build();
        onChanged();
      } else {
        statusRespBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
     */
    public Builder mergeStatusResp(org.broadband_forum.obbaa.onu.message.gpb.message.Status value) {
      if (statusRespBuilder_ == null) {
        if (statusResp_ != null) {
          statusResp_ =
            org.broadband_forum.obbaa.onu.message.gpb.message.Status.newBuilder(statusResp_).mergeFrom(value).buildPartial();
        } else {
          statusResp_ = value;
        }
        onChanged();
      } else {
        statusRespBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
     */
    public Builder clearStatusResp() {
      if (statusRespBuilder_ == null) {
        statusResp_ = null;
        onChanged();
      } else {
        statusResp_ = null;
        statusRespBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
     */
    public org.broadband_forum.obbaa.onu.message.gpb.message.Status.Builder getStatusRespBuilder() {
      
      onChanged();
      return getStatusRespFieldBuilder().getBuilder();
    }
    /**
     * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
     */
    public org.broadband_forum.obbaa.onu.message.gpb.message.StatusOrBuilder getStatusRespOrBuilder() {
      if (statusRespBuilder_ != null) {
        return statusRespBuilder_.getMessageOrBuilder();
      } else {
        return statusResp_ == null ?
            org.broadband_forum.obbaa.onu.message.gpb.message.Status.getDefaultInstance() : statusResp_;
      }
    }
    /**
     * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        org.broadband_forum.obbaa.onu.message.gpb.message.Status, org.broadband_forum.obbaa.onu.message.gpb.message.Status.Builder, org.broadband_forum.obbaa.onu.message.gpb.message.StatusOrBuilder> 
        getStatusRespFieldBuilder() {
      if (statusRespBuilder_ == null) {
        statusRespBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            org.broadband_forum.obbaa.onu.message.gpb.message.Status, org.broadband_forum.obbaa.onu.message.gpb.message.Status.Builder, org.broadband_forum.obbaa.onu.message.gpb.message.StatusOrBuilder>(
                getStatusResp(),
                getParentForChildren(),
                isClean());
        statusResp_ = null;
      }
      return statusRespBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:tr451_vomci_nbi_message.v1.ReplaceConfigResp)
  }

  // @@protoc_insertion_point(class_scope:tr451_vomci_nbi_message.v1.ReplaceConfigResp)
  private static final org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp();
  }

  public static org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ReplaceConfigResp>
      PARSER = new com.google.protobuf.AbstractParser<ReplaceConfigResp>() {
    @java.lang.Override
    public ReplaceConfigResp parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ReplaceConfigResp(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ReplaceConfigResp> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ReplaceConfigResp> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.broadband_forum.obbaa.onu.message.gpb.message.ReplaceConfigResp getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

