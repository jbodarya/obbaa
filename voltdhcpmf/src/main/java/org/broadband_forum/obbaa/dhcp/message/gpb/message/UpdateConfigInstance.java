// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: tr451_vomci_nbi_message.proto

package org.broadband_forum.obbaa.dhcp.message.gpb.message;

/**
 * Protobuf type {@code tr451_vomci_nbi_message.v1.UpdateConfigInstance}
 */
public final class UpdateConfigInstance extends
        com.google.protobuf.GeneratedMessageV3 implements
        // @@protoc_insertion_point(message_implements:tr451_vomci_nbi_message.v1.UpdateConfigInstance)
        UpdateConfigInstanceOrBuilder {
    private static final long serialVersionUID = 0L;

    // Use UpdateConfigInstance.newBuilder() to construct.
    private UpdateConfigInstance(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private UpdateConfigInstance() {
        currentConfigInst_ = com.google.protobuf.ByteString.EMPTY;
        deltaConfig_ = com.google.protobuf.ByteString.EMPTY;
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
            UnusedPrivateParameter unused) {
        return new UpdateConfigInstance();
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
        return this.unknownFields;
    }

    private UpdateConfigInstance(
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

                        currentConfigInst_ = input.readBytes();
                        break;
                    }
                    case 18: {

                        deltaConfig_ = input.readBytes();
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
        return Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_UpdateConfigInstance_descriptor;
    }

    @Override
    protected FieldAccessorTable
    internalGetFieldAccessorTable() {
        return Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_UpdateConfigInstance_fieldAccessorTable
                .ensureFieldAccessorsInitialized(
                        UpdateConfigInstance.class, Builder.class);
    }

    public static final int CURRENT_CONFIG_INST_FIELD_NUMBER = 1;
    private com.google.protobuf.ByteString currentConfigInst_;

    /**
     * <pre>
     * Full current configuration
     * </pre>
     *
     * <code>bytes current_config_inst = 1;</code>
     *
     * @return The currentConfigInst.
     */
    @Override
    public com.google.protobuf.ByteString getCurrentConfigInst() {
        return currentConfigInst_;
    }

    public static final int DELTA_CONFIG_FIELD_NUMBER = 2;
    private com.google.protobuf.ByteString deltaConfig_;

    /**
     * <pre>
     * instance
     * </pre>
     *
     * <code>bytes delta_config = 2;</code>
     *
     * @return The deltaConfig.
     */
    @Override
    public com.google.protobuf.ByteString getDeltaConfig() {
        return deltaConfig_;
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
        if (!currentConfigInst_.isEmpty()) {
            output.writeBytes(1, currentConfigInst_);
        }
        if (!deltaConfig_.isEmpty()) {
            output.writeBytes(2, deltaConfig_);
        }
        unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
        int size = memoizedSize;
        if (size != -1) return size;

        size = 0;
        if (!currentConfigInst_.isEmpty()) {
            size += com.google.protobuf.CodedOutputStream
                    .computeBytesSize(1, currentConfigInst_);
        }
        if (!deltaConfig_.isEmpty()) {
            size += com.google.protobuf.CodedOutputStream
                    .computeBytesSize(2, deltaConfig_);
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
        if (!(obj instanceof UpdateConfigInstance)) {
            return super.equals(obj);
        }
        UpdateConfigInstance other = (UpdateConfigInstance) obj;

        if (!getCurrentConfigInst()
                .equals(other.getCurrentConfigInst())) return false;
        if (!getDeltaConfig()
                .equals(other.getDeltaConfig())) return false;
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
        hash = (37 * hash) + CURRENT_CONFIG_INST_FIELD_NUMBER;
        hash = (53 * hash) + getCurrentConfigInst().hashCode();
        hash = (37 * hash) + DELTA_CONFIG_FIELD_NUMBER;
        hash = (53 * hash) + getDeltaConfig().hashCode();
        hash = (29 * hash) + unknownFields.hashCode();
        memoizedHashCode = hash;
        return hash;
    }

    public static UpdateConfigInstance parseFrom(
            java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static UpdateConfigInstance parseFrom(
            java.nio.ByteBuffer data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static UpdateConfigInstance parseFrom(
            com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static UpdateConfigInstance parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static UpdateConfigInstance parseFrom(byte[] data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static UpdateConfigInstance parseFrom(
            byte[] data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static UpdateConfigInstance parseFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }

    public static UpdateConfigInstance parseFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static UpdateConfigInstance parseDelimitedFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input);
    }

    public static UpdateConfigInstance parseDelimitedFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static UpdateConfigInstance parseFrom(
            com.google.protobuf.CodedInputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }

    public static UpdateConfigInstance parseFrom(
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

    public static Builder newBuilder(UpdateConfigInstance prototype) {
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
     * Protobuf type {@code tr451_vomci_nbi_message.v1.UpdateConfigInstance}
     */
    public static final class Builder extends
            com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
            // @@protoc_insertion_point(builder_implements:tr451_vomci_nbi_message.v1.UpdateConfigInstance)
            UpdateConfigInstanceOrBuilder {
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_UpdateConfigInstance_descriptor;
        }

        @Override
        protected FieldAccessorTable
        internalGetFieldAccessorTable() {
            return Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_UpdateConfigInstance_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            UpdateConfigInstance.class, Builder.class);
        }

        // Construct using org.broadband_forum.obbaa.dhcp.message.gpb.message.UpdateConfigInstance.newBuilder()
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
            currentConfigInst_ = com.google.protobuf.ByteString.EMPTY;

            deltaConfig_ = com.google.protobuf.ByteString.EMPTY;

            return this;
        }

        @Override
        public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
            return Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_UpdateConfigInstance_descriptor;
        }

        @Override
        public UpdateConfigInstance getDefaultInstanceForType() {
            return UpdateConfigInstance.getDefaultInstance();
        }

        @Override
        public UpdateConfigInstance build() {
            UpdateConfigInstance result = buildPartial();
            if (!result.isInitialized()) {
                throw newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public UpdateConfigInstance buildPartial() {
            UpdateConfigInstance result = new UpdateConfigInstance(this);
            result.currentConfigInst_ = currentConfigInst_;
            result.deltaConfig_ = deltaConfig_;
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
            if (other instanceof UpdateConfigInstance) {
                return mergeFrom((UpdateConfigInstance) other);
            } else {
                super.mergeFrom(other);
                return this;
            }
        }

        public Builder mergeFrom(UpdateConfigInstance other) {
            if (other == UpdateConfigInstance.getDefaultInstance()) return this;
            if (other.getCurrentConfigInst() != com.google.protobuf.ByteString.EMPTY) {
                setCurrentConfigInst(other.getCurrentConfigInst());
            }
            if (other.getDeltaConfig() != com.google.protobuf.ByteString.EMPTY) {
                setDeltaConfig(other.getDeltaConfig());
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
            UpdateConfigInstance parsedMessage = null;
            try {
                parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                parsedMessage = (UpdateConfigInstance) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } finally {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
            }
            return this;
        }

        private com.google.protobuf.ByteString currentConfigInst_ = com.google.protobuf.ByteString.EMPTY;

        /**
         * <pre>
         * Full current configuration
         * </pre>
         *
         * <code>bytes current_config_inst = 1;</code>
         *
         * @return The currentConfigInst.
         */
        @Override
        public com.google.protobuf.ByteString getCurrentConfigInst() {
            return currentConfigInst_;
        }

        /**
         * <pre>
         * Full current configuration
         * </pre>
         *
         * <code>bytes current_config_inst = 1;</code>
         *
         * @param value The currentConfigInst to set.
         * @return This builder for chaining.
         */
        public Builder setCurrentConfigInst(com.google.protobuf.ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }

            currentConfigInst_ = value;
            onChanged();
            return this;
        }

        /**
         * <pre>
         * Full current configuration
         * </pre>
         *
         * <code>bytes current_config_inst = 1;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearCurrentConfigInst() {

            currentConfigInst_ = getDefaultInstance().getCurrentConfigInst();
            onChanged();
            return this;
        }

        private com.google.protobuf.ByteString deltaConfig_ = com.google.protobuf.ByteString.EMPTY;

        /**
         * <pre>
         * instance
         * </pre>
         *
         * <code>bytes delta_config = 2;</code>
         *
         * @return The deltaConfig.
         */
        @Override
        public com.google.protobuf.ByteString getDeltaConfig() {
            return deltaConfig_;
        }

        /**
         * <pre>
         * instance
         * </pre>
         *
         * <code>bytes delta_config = 2;</code>
         *
         * @param value The deltaConfig to set.
         * @return This builder for chaining.
         */
        public Builder setDeltaConfig(com.google.protobuf.ByteString value) {
            if (value == null) {
                throw new NullPointerException();
            }

            deltaConfig_ = value;
            onChanged();
            return this;
        }

        /**
         * <pre>
         * instance
         * </pre>
         *
         * <code>bytes delta_config = 2;</code>
         *
         * @return This builder for chaining.
         */
        public Builder clearDeltaConfig() {

            deltaConfig_ = getDefaultInstance().getDeltaConfig();
            onChanged();
            return this;
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


        // @@protoc_insertion_point(builder_scope:tr451_vomci_nbi_message.v1.UpdateConfigInstance)
    }

    // @@protoc_insertion_point(class_scope:tr451_vomci_nbi_message.v1.UpdateConfigInstance)
    private static final UpdateConfigInstance DEFAULT_INSTANCE;

    static {
        DEFAULT_INSTANCE = new UpdateConfigInstance();
    }

    public static UpdateConfigInstance getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<UpdateConfigInstance>
            PARSER = new com.google.protobuf.AbstractParser<UpdateConfigInstance>() {
        @Override
        public UpdateConfigInstance parsePartialFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return new UpdateConfigInstance(input, extensionRegistry);
        }
    };

    public static com.google.protobuf.Parser<UpdateConfigInstance> parser() {
        return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<UpdateConfigInstance> getParserForType() {
        return PARSER;
    }

    @Override
    public UpdateConfigInstance getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

}

