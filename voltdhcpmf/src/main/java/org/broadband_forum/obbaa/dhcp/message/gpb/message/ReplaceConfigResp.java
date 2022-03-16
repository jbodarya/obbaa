// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: tr451_vomci_nbi_message.proto

package org.broadband_forum.obbaa.dhcp.message.gpb.message;

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

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
            UnusedPrivateParameter unused) {
        return new ReplaceConfigResp();
    }

    @Override
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
            throw new NullPointerException();
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
                        Status.Builder subBuilder = null;
                        if (statusResp_ != null) {
                            subBuilder = statusResp_.toBuilder();
                        }
                        statusResp_ = input.readMessage(Status.parser(), extensionRegistry);
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
        return Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_ReplaceConfigResp_descriptor;
    }

    @Override
    protected FieldAccessorTable
    internalGetFieldAccessorTable() {
        return Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_ReplaceConfigResp_fieldAccessorTable
                .ensureFieldAccessorsInitialized(
                        ReplaceConfigResp.class, Builder.class);
    }

    public static final int STATUS_RESP_FIELD_NUMBER = 1;
    private Status statusResp_;

    /**
     * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
     *
     * @return Whether the statusResp field is set.
     */
    @Override
    public boolean hasStatusResp() {
        return statusResp_ != null;
    }

    /**
     * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
     *
     * @return The statusResp.
     */
    @Override
    public Status getStatusResp() {
        return statusResp_ == null ? Status.getDefaultInstance() : statusResp_;
    }

    /**
     * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
     */
    @Override
    public StatusOrBuilder getStatusRespOrBuilder() {
        return getStatusResp();
    }

    private byte memoizedIsInitialized = -1;

    @Override
    public final boolean isInitialized() {
        byte isInitialized = memoizedIsInitialized;
        if (isInitialized == 1) return true;
        if (isInitialized == 0) return false;

        memoizedIsInitialized = 1;
        return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
            throws java.io.IOException {
        if (statusResp_ != null) {
            output.writeMessage(1, getStatusResp());
        }
        unknownFields.writeTo(output);
    }

    @Override
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

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ReplaceConfigResp)) {
            return super.equals(obj);
        }
        ReplaceConfigResp other = (ReplaceConfigResp) obj;

        if (hasStatusResp() != other.hasStatusResp()) return false;
        if (hasStatusResp()) {
            if (!getStatusResp()
                    .equals(other.getStatusResp())) return false;
        }
        if (!unknownFields.equals(other.unknownFields)) return false;
        return true;
    }

    @Override
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

    public static ReplaceConfigResp parseFrom(
            java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ReplaceConfigResp parseFrom(
            java.nio.ByteBuffer data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ReplaceConfigResp parseFrom(
            com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ReplaceConfigResp parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ReplaceConfigResp parseFrom(byte[] data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static ReplaceConfigResp parseFrom(
            byte[] data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static ReplaceConfigResp parseFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }

    public static ReplaceConfigResp parseFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ReplaceConfigResp parseDelimitedFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input);
    }

    public static ReplaceConfigResp parseDelimitedFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static ReplaceConfigResp parseFrom(
            com.google.protobuf.CodedInputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }

    public static ReplaceConfigResp parseFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(ReplaceConfigResp prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    @Override
    public Builder toBuilder() {
        return this == DEFAULT_INSTANCE
                ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
            BuilderParent parent) {
        Builder builder = new Builder(parent);
        return builder;
    }

    /**
     * Protobuf type {@code tr451_vomci_nbi_message.v1.ReplaceConfigResp}
     */
    public static final class Builder extends
            com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
            // @@protoc_insertion_point(builder_implements:tr451_vomci_nbi_message.v1.ReplaceConfigResp)
            ReplaceConfigRespOrBuilder {
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_ReplaceConfigResp_descriptor;
        }

        @Override
        protected FieldAccessorTable
        internalGetFieldAccessorTable() {
            return Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_ReplaceConfigResp_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            ReplaceConfigResp.class, Builder.class);
        }

        // Construct using org.broadband_forum.obbaa.dhcp.message.gpb.message.ReplaceConfigResp.newBuilder()
        private Builder() {
            maybeForceBuilderInitialization();
        }

        private Builder(
                BuilderParent parent) {
            super(parent);
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (com.google.protobuf.GeneratedMessageV3
                    .alwaysUseFieldBuilders) {
            }
        }

        @Override
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

        @Override
        public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
            return Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_ReplaceConfigResp_descriptor;
        }

        @Override
        public ReplaceConfigResp getDefaultInstanceForType() {
            return ReplaceConfigResp.getDefaultInstance();
        }

        @Override
        public ReplaceConfigResp build() {
            ReplaceConfigResp result = buildPartial();
            if (!result.isInitialized()) {
                throw newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public ReplaceConfigResp buildPartial() {
            ReplaceConfigResp result = new ReplaceConfigResp(this);
            if (statusRespBuilder_ == null) {
                result.statusResp_ = statusResp_;
            } else {
                result.statusResp_ = statusRespBuilder_.build();
            }
            onBuilt();
            return result;
        }

        @Override
        public Builder clone() {
            return super.clone();
        }

        @Override
        public Builder setField(
                com.google.protobuf.Descriptors.FieldDescriptor field,
                Object value) {
            return super.setField(field, value);
        }

        @Override
        public Builder clearField(
                com.google.protobuf.Descriptors.FieldDescriptor field) {
            return super.clearField(field);
        }

        @Override
        public Builder clearOneof(
                com.google.protobuf.Descriptors.OneofDescriptor oneof) {
            return super.clearOneof(oneof);
        }

        @Override
        public Builder setRepeatedField(
                com.google.protobuf.Descriptors.FieldDescriptor field,
                int index, Object value) {
            return super.setRepeatedField(field, index, value);
        }

        @Override
        public Builder addRepeatedField(
                com.google.protobuf.Descriptors.FieldDescriptor field,
                Object value) {
            return super.addRepeatedField(field, value);
        }

        @Override
        public Builder mergeFrom(com.google.protobuf.Message other) {
            if (other instanceof ReplaceConfigResp) {
                return mergeFrom((ReplaceConfigResp) other);
            } else {
                super.mergeFrom(other);
                return this;
            }
        }

        public Builder mergeFrom(ReplaceConfigResp other) {
            if (other == ReplaceConfigResp.getDefaultInstance()) return this;
            if (other.hasStatusResp()) {
                mergeStatusResp(other.getStatusResp());
            }
            this.mergeUnknownFields(other.unknownFields);
            onChanged();
            return this;
        }

        @Override
        public final boolean isInitialized() {
            return true;
        }

        @Override
        public Builder mergeFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            ReplaceConfigResp parsedMessage = null;
            try {
                parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                parsedMessage = (ReplaceConfigResp) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } finally {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
            }
            return this;
        }

        private Status statusResp_;
        private com.google.protobuf.SingleFieldBuilderV3<
                Status, Status.Builder, StatusOrBuilder> statusRespBuilder_;

        /**
         * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
         *
         * @return Whether the statusResp field is set.
         */
        public boolean hasStatusResp() {
            return statusRespBuilder_ != null || statusResp_ != null;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
         *
         * @return The statusResp.
         */
        public Status getStatusResp() {
            if (statusRespBuilder_ == null) {
                return statusResp_ == null ? Status.getDefaultInstance() : statusResp_;
            } else {
                return statusRespBuilder_.getMessage();
            }
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
         */
        public Builder setStatusResp(Status value) {
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
                Status.Builder builderForValue) {
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
        public Builder mergeStatusResp(Status value) {
            if (statusRespBuilder_ == null) {
                if (statusResp_ != null) {
                    statusResp_ =
                            Status.newBuilder(statusResp_).mergeFrom(value).buildPartial();
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
        public Status.Builder getStatusRespBuilder() {

            onChanged();
            return getStatusRespFieldBuilder().getBuilder();
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
         */
        public StatusOrBuilder getStatusRespOrBuilder() {
            if (statusRespBuilder_ != null) {
                return statusRespBuilder_.getMessageOrBuilder();
            } else {
                return statusResp_ == null ?
                        Status.getDefaultInstance() : statusResp_;
            }
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Status status_resp = 1;</code>
         */
        private com.google.protobuf.SingleFieldBuilderV3<
                Status, Status.Builder, StatusOrBuilder>
        getStatusRespFieldBuilder() {
            if (statusRespBuilder_ == null) {
                statusRespBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
                        Status, Status.Builder, StatusOrBuilder>(
                        getStatusResp(),
                        getParentForChildren(),
                        isClean());
                statusResp_ = null;
            }
            return statusRespBuilder_;
        }

        @Override
        public final Builder setUnknownFields(
                final com.google.protobuf.UnknownFieldSet unknownFields) {
            return super.setUnknownFields(unknownFields);
        }

        @Override
        public final Builder mergeUnknownFields(
                final com.google.protobuf.UnknownFieldSet unknownFields) {
            return super.mergeUnknownFields(unknownFields);
        }


        // @@protoc_insertion_point(builder_scope:tr451_vomci_nbi_message.v1.ReplaceConfigResp)
    }

    // @@protoc_insertion_point(class_scope:tr451_vomci_nbi_message.v1.ReplaceConfigResp)
    private static final ReplaceConfigResp DEFAULT_INSTANCE;

    static {
        DEFAULT_INSTANCE = new ReplaceConfigResp();
    }

    public static ReplaceConfigResp getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ReplaceConfigResp>
            PARSER = new com.google.protobuf.AbstractParser<ReplaceConfigResp>() {
        @Override
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

    @Override
    public com.google.protobuf.Parser<ReplaceConfigResp> getParserForType() {
        return PARSER;
    }

    @Override
    public ReplaceConfigResp getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

}

