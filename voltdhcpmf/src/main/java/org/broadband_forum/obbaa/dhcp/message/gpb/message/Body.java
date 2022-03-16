// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: tr451_vomci_nbi_message.proto

package org.broadband_forum.obbaa.dhcp.message.gpb.message;

/**
 * Protobuf type {@code tr451_vomci_nbi_message.v1.Body}
 */
public final class Body extends
        com.google.protobuf.GeneratedMessageV3 implements
        // @@protoc_insertion_point(message_implements:tr451_vomci_nbi_message.v1.Body)
        BodyOrBuilder {
    private static final long serialVersionUID = 0L;

    // Use Body.newBuilder() to construct.
    private Body(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Body() {
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
            UnusedPrivateParameter unused) {
        return new Body();
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
        return this.unknownFields;
    }

    private Body(
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
                        Request.Builder subBuilder = null;
                        if (msgBodyCase_ == 1) {
                            subBuilder = ((Request) msgBody_).toBuilder();
                        }
                        msgBody_ =
                                input.readMessage(Request.parser(), extensionRegistry);
                        if (subBuilder != null) {
                            subBuilder.mergeFrom((Request) msgBody_);
                            msgBody_ = subBuilder.buildPartial();
                        }
                        msgBodyCase_ = 1;
                        break;
                    }
                    case 18: {
                        Response.Builder subBuilder = null;
                        if (msgBodyCase_ == 2) {
                            subBuilder = ((Response) msgBody_).toBuilder();
                        }
                        msgBody_ =
                                input.readMessage(Response.parser(), extensionRegistry);
                        if (subBuilder != null) {
                            subBuilder.mergeFrom((Response) msgBody_);
                            msgBody_ = subBuilder.buildPartial();
                        }
                        msgBodyCase_ = 2;
                        break;
                    }
                    case 26: {
                        Notification.Builder subBuilder = null;
                        if (msgBodyCase_ == 3) {
                            subBuilder = ((Notification) msgBody_).toBuilder();
                        }
                        msgBody_ =
                                input.readMessage(Notification.parser(), extensionRegistry);
                        if (subBuilder != null) {
                            subBuilder.mergeFrom((Notification) msgBody_);
                            msgBody_ = subBuilder.buildPartial();
                        }
                        msgBodyCase_ = 3;
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
        return Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_Body_descriptor;
    }

    @Override
    protected FieldAccessorTable
    internalGetFieldAccessorTable() {
        return Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_Body_fieldAccessorTable
                .ensureFieldAccessorsInitialized(
                        Body.class, Builder.class);
    }

    private int msgBodyCase_ = 0;
    private Object msgBody_;

    public enum MsgBodyCase
            implements com.google.protobuf.Internal.EnumLite,
            InternalOneOfEnum {
        REQUEST(1),
        RESPONSE(2),
        NOTIFICATION(3),
        MSGBODY_NOT_SET(0);
        private final int value;

        private MsgBodyCase(int value) {
            this.value = value;
        }

        /**
         * @param value The number of the enum to look for.
         * @return The enum associated with the given number.
         * @deprecated Use {@link #forNumber(int)} instead.
         */
        @Deprecated
        public static MsgBodyCase valueOf(int value) {
            return forNumber(value);
        }

        public static MsgBodyCase forNumber(int value) {
            switch (value) {
                case 1:
                    return REQUEST;
                case 2:
                    return RESPONSE;
                case 3:
                    return NOTIFICATION;
                case 0:
                    return MSGBODY_NOT_SET;
                default:
                    return null;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    ;

    public MsgBodyCase
    getMsgBodyCase() {
        return MsgBodyCase.forNumber(
                msgBodyCase_);
    }

    public static final int REQUEST_FIELD_NUMBER = 1;

    /**
     * <code>.tr451_vomci_nbi_message.v1.Request request = 1;</code>
     *
     * @return Whether the request field is set.
     */
    @Override
    public boolean hasRequest() {
        return msgBodyCase_ == 1;
    }

    /**
     * <code>.tr451_vomci_nbi_message.v1.Request request = 1;</code>
     *
     * @return The request.
     */
    @Override
    public Request getRequest() {
        if (msgBodyCase_ == 1) {
            return (Request) msgBody_;
        }
        return Request.getDefaultInstance();
    }

    /**
     * <code>.tr451_vomci_nbi_message.v1.Request request = 1;</code>
     */
    @Override
    public RequestOrBuilder getRequestOrBuilder() {
        if (msgBodyCase_ == 1) {
            return (Request) msgBody_;
        }
        return Request.getDefaultInstance();
    }

    public static final int RESPONSE_FIELD_NUMBER = 2;

    /**
     * <code>.tr451_vomci_nbi_message.v1.Response response = 2;</code>
     *
     * @return Whether the response field is set.
     */
    @Override
    public boolean hasResponse() {
        return msgBodyCase_ == 2;
    }

    /**
     * <code>.tr451_vomci_nbi_message.v1.Response response = 2;</code>
     *
     * @return The response.
     */
    @Override
    public Response getResponse() {
        if (msgBodyCase_ == 2) {
            return (Response) msgBody_;
        }
        return Response.getDefaultInstance();
    }

    /**
     * <code>.tr451_vomci_nbi_message.v1.Response response = 2;</code>
     */
    @Override
    public ResponseOrBuilder getResponseOrBuilder() {
        if (msgBodyCase_ == 2) {
            return (Response) msgBody_;
        }
        return Response.getDefaultInstance();
    }

    public static final int NOTIFICATION_FIELD_NUMBER = 3;

    /**
     * <code>.tr451_vomci_nbi_message.v1.Notification notification = 3;</code>
     *
     * @return Whether the notification field is set.
     */
    @Override
    public boolean hasNotification() {
        return msgBodyCase_ == 3;
    }

    /**
     * <code>.tr451_vomci_nbi_message.v1.Notification notification = 3;</code>
     *
     * @return The notification.
     */
    @Override
    public Notification getNotification() {
        if (msgBodyCase_ == 3) {
            return (Notification) msgBody_;
        }
        return Notification.getDefaultInstance();
    }

    /**
     * <code>.tr451_vomci_nbi_message.v1.Notification notification = 3;</code>
     */
    @Override
    public NotificationOrBuilder getNotificationOrBuilder() {
        if (msgBodyCase_ == 3) {
            return (Notification) msgBody_;
        }
        return Notification.getDefaultInstance();
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
        if (msgBodyCase_ == 1) {
            output.writeMessage(1, (Request) msgBody_);
        }
        if (msgBodyCase_ == 2) {
            output.writeMessage(2, (Response) msgBody_);
        }
        if (msgBodyCase_ == 3) {
            output.writeMessage(3, (Notification) msgBody_);
        }
        unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
        int size = memoizedSize;
        if (size != -1) return size;

        size = 0;
        if (msgBodyCase_ == 1) {
            size += com.google.protobuf.CodedOutputStream
                    .computeMessageSize(1, (Request) msgBody_);
        }
        if (msgBodyCase_ == 2) {
            size += com.google.protobuf.CodedOutputStream
                    .computeMessageSize(2, (Response) msgBody_);
        }
        if (msgBodyCase_ == 3) {
            size += com.google.protobuf.CodedOutputStream
                    .computeMessageSize(3, (Notification) msgBody_);
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
        if (!(obj instanceof Body)) {
            return super.equals(obj);
        }
        Body other = (Body) obj;

        if (!getMsgBodyCase().equals(other.getMsgBodyCase())) return false;
        switch (msgBodyCase_) {
            case 1:
                if (!getRequest()
                        .equals(other.getRequest())) return false;
                break;
            case 2:
                if (!getResponse()
                        .equals(other.getResponse())) return false;
                break;
            case 3:
                if (!getNotification()
                        .equals(other.getNotification())) return false;
                break;
            case 0:
            default:
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
        switch (msgBodyCase_) {
            case 1:
                hash = (37 * hash) + REQUEST_FIELD_NUMBER;
                hash = (53 * hash) + getRequest().hashCode();
                break;
            case 2:
                hash = (37 * hash) + RESPONSE_FIELD_NUMBER;
                hash = (53 * hash) + getResponse().hashCode();
                break;
            case 3:
                hash = (37 * hash) + NOTIFICATION_FIELD_NUMBER;
                hash = (53 * hash) + getNotification().hashCode();
                break;
            case 0:
            default:
        }
        hash = (29 * hash) + unknownFields.hashCode();
        memoizedHashCode = hash;
        return hash;
    }

    public static Body parseFrom(
            java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Body parseFrom(
            java.nio.ByteBuffer data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Body parseFrom(
            com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Body parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Body parseFrom(byte[] data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Body parseFrom(
            byte[] data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Body parseFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }

    public static Body parseFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Body parseDelimitedFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input);
    }

    public static Body parseDelimitedFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Body parseFrom(
            com.google.protobuf.CodedInputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }

    public static Body parseFrom(
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

    public static Builder newBuilder(Body prototype) {
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
     * Protobuf type {@code tr451_vomci_nbi_message.v1.Body}
     */
    public static final class Builder extends
            com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
            // @@protoc_insertion_point(builder_implements:tr451_vomci_nbi_message.v1.Body)
            BodyOrBuilder {
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_Body_descriptor;
        }

        @Override
        protected FieldAccessorTable
        internalGetFieldAccessorTable() {
            return Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_Body_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            Body.class, Builder.class);
        }

        // Construct using org.broadband_forum.obbaa.dhcp.message.gpb.message.Body.newBuilder()
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
            msgBodyCase_ = 0;
            msgBody_ = null;
            return this;
        }

        @Override
        public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
            return Tr451VomciNbiMessage.internal_static_tr451_vomci_nbi_message_v1_Body_descriptor;
        }

        @Override
        public Body getDefaultInstanceForType() {
            return Body.getDefaultInstance();
        }

        @Override
        public Body build() {
            Body result = buildPartial();
            if (!result.isInitialized()) {
                throw newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Body buildPartial() {
            Body result = new Body(this);
            if (msgBodyCase_ == 1) {
                if (requestBuilder_ == null) {
                    result.msgBody_ = msgBody_;
                } else {
                    result.msgBody_ = requestBuilder_.build();
                }
            }
            if (msgBodyCase_ == 2) {
                if (responseBuilder_ == null) {
                    result.msgBody_ = msgBody_;
                } else {
                    result.msgBody_ = responseBuilder_.build();
                }
            }
            if (msgBodyCase_ == 3) {
                if (notificationBuilder_ == null) {
                    result.msgBody_ = msgBody_;
                } else {
                    result.msgBody_ = notificationBuilder_.build();
                }
            }
            result.msgBodyCase_ = msgBodyCase_;
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
            if (other instanceof Body) {
                return mergeFrom((Body) other);
            } else {
                super.mergeFrom(other);
                return this;
            }
        }

        public Builder mergeFrom(Body other) {
            if (other == Body.getDefaultInstance()) return this;
            switch (other.getMsgBodyCase()) {
                case REQUEST: {
                    mergeRequest(other.getRequest());
                    break;
                }
                case RESPONSE: {
                    mergeResponse(other.getResponse());
                    break;
                }
                case NOTIFICATION: {
                    mergeNotification(other.getNotification());
                    break;
                }
                case MSGBODY_NOT_SET: {
                    break;
                }
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
            Body parsedMessage = null;
            try {
                parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                parsedMessage = (Body) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } finally {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
            }
            return this;
        }

        private int msgBodyCase_ = 0;
        private Object msgBody_;

        public MsgBodyCase
        getMsgBodyCase() {
            return MsgBodyCase.forNumber(
                    msgBodyCase_);
        }

        public Builder clearMsgBody() {
            msgBodyCase_ = 0;
            msgBody_ = null;
            onChanged();
            return this;
        }


        private com.google.protobuf.SingleFieldBuilderV3<
                Request, Request.Builder, RequestOrBuilder> requestBuilder_;

        /**
         * <code>.tr451_vomci_nbi_message.v1.Request request = 1;</code>
         *
         * @return Whether the request field is set.
         */
        @Override
        public boolean hasRequest() {
            return msgBodyCase_ == 1;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Request request = 1;</code>
         *
         * @return The request.
         */
        @Override
        public Request getRequest() {
            if (requestBuilder_ == null) {
                if (msgBodyCase_ == 1) {
                    return (Request) msgBody_;
                }
                return Request.getDefaultInstance();
            } else {
                if (msgBodyCase_ == 1) {
                    return requestBuilder_.getMessage();
                }
                return Request.getDefaultInstance();
            }
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Request request = 1;</code>
         */
        public Builder setRequest(Request value) {
            if (requestBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                msgBody_ = value;
                onChanged();
            } else {
                requestBuilder_.setMessage(value);
            }
            msgBodyCase_ = 1;
            return this;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Request request = 1;</code>
         */
        public Builder setRequest(
                Request.Builder builderForValue) {
            if (requestBuilder_ == null) {
                msgBody_ = builderForValue.build();
                onChanged();
            } else {
                requestBuilder_.setMessage(builderForValue.build());
            }
            msgBodyCase_ = 1;
            return this;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Request request = 1;</code>
         */
        public Builder mergeRequest(Request value) {
            if (requestBuilder_ == null) {
                if (msgBodyCase_ == 1 &&
                        msgBody_ != Request.getDefaultInstance()) {
                    msgBody_ = Request.newBuilder((Request) msgBody_)
                            .mergeFrom(value).buildPartial();
                } else {
                    msgBody_ = value;
                }
                onChanged();
            } else {
                if (msgBodyCase_ == 1) {
                    requestBuilder_.mergeFrom(value);
                }
                requestBuilder_.setMessage(value);
            }
            msgBodyCase_ = 1;
            return this;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Request request = 1;</code>
         */
        public Builder clearRequest() {
            if (requestBuilder_ == null) {
                if (msgBodyCase_ == 1) {
                    msgBodyCase_ = 0;
                    msgBody_ = null;
                    onChanged();
                }
            } else {
                if (msgBodyCase_ == 1) {
                    msgBodyCase_ = 0;
                    msgBody_ = null;
                }
                requestBuilder_.clear();
            }
            return this;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Request request = 1;</code>
         */
        public Request.Builder getRequestBuilder() {
            return getRequestFieldBuilder().getBuilder();
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Request request = 1;</code>
         */
        @Override
        public RequestOrBuilder getRequestOrBuilder() {
            if ((msgBodyCase_ == 1) && (requestBuilder_ != null)) {
                return requestBuilder_.getMessageOrBuilder();
            } else {
                if (msgBodyCase_ == 1) {
                    return (Request) msgBody_;
                }
                return Request.getDefaultInstance();
            }
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Request request = 1;</code>
         */
        private com.google.protobuf.SingleFieldBuilderV3<
                Request, Request.Builder, RequestOrBuilder>
        getRequestFieldBuilder() {
            if (requestBuilder_ == null) {
                if (!(msgBodyCase_ == 1)) {
                    msgBody_ = Request.getDefaultInstance();
                }
                requestBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
                        Request, Request.Builder, RequestOrBuilder>(
                        (Request) msgBody_,
                        getParentForChildren(),
                        isClean());
                msgBody_ = null;
            }
            msgBodyCase_ = 1;
            onChanged();
            ;
            return requestBuilder_;
        }

        private com.google.protobuf.SingleFieldBuilderV3<
                Response, Response.Builder, ResponseOrBuilder> responseBuilder_;

        /**
         * <code>.tr451_vomci_nbi_message.v1.Response response = 2;</code>
         *
         * @return Whether the response field is set.
         */
        @Override
        public boolean hasResponse() {
            return msgBodyCase_ == 2;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Response response = 2;</code>
         *
         * @return The response.
         */
        @Override
        public Response getResponse() {
            if (responseBuilder_ == null) {
                if (msgBodyCase_ == 2) {
                    return (Response) msgBody_;
                }
                return Response.getDefaultInstance();
            } else {
                if (msgBodyCase_ == 2) {
                    return responseBuilder_.getMessage();
                }
                return Response.getDefaultInstance();
            }
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Response response = 2;</code>
         */
        public Builder setResponse(Response value) {
            if (responseBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                msgBody_ = value;
                onChanged();
            } else {
                responseBuilder_.setMessage(value);
            }
            msgBodyCase_ = 2;
            return this;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Response response = 2;</code>
         */
        public Builder setResponse(
                Response.Builder builderForValue) {
            if (responseBuilder_ == null) {
                msgBody_ = builderForValue.build();
                onChanged();
            } else {
                responseBuilder_.setMessage(builderForValue.build());
            }
            msgBodyCase_ = 2;
            return this;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Response response = 2;</code>
         */
        public Builder mergeResponse(Response value) {
            if (responseBuilder_ == null) {
                if (msgBodyCase_ == 2 &&
                        msgBody_ != Response.getDefaultInstance()) {
                    msgBody_ = Response.newBuilder((Response) msgBody_)
                            .mergeFrom(value).buildPartial();
                } else {
                    msgBody_ = value;
                }
                onChanged();
            } else {
                if (msgBodyCase_ == 2) {
                    responseBuilder_.mergeFrom(value);
                }
                responseBuilder_.setMessage(value);
            }
            msgBodyCase_ = 2;
            return this;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Response response = 2;</code>
         */
        public Builder clearResponse() {
            if (responseBuilder_ == null) {
                if (msgBodyCase_ == 2) {
                    msgBodyCase_ = 0;
                    msgBody_ = null;
                    onChanged();
                }
            } else {
                if (msgBodyCase_ == 2) {
                    msgBodyCase_ = 0;
                    msgBody_ = null;
                }
                responseBuilder_.clear();
            }
            return this;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Response response = 2;</code>
         */
        public Response.Builder getResponseBuilder() {
            return getResponseFieldBuilder().getBuilder();
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Response response = 2;</code>
         */
        @Override
        public ResponseOrBuilder getResponseOrBuilder() {
            if ((msgBodyCase_ == 2) && (responseBuilder_ != null)) {
                return responseBuilder_.getMessageOrBuilder();
            } else {
                if (msgBodyCase_ == 2) {
                    return (Response) msgBody_;
                }
                return Response.getDefaultInstance();
            }
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Response response = 2;</code>
         */
        private com.google.protobuf.SingleFieldBuilderV3<
                Response, Response.Builder, ResponseOrBuilder>
        getResponseFieldBuilder() {
            if (responseBuilder_ == null) {
                if (!(msgBodyCase_ == 2)) {
                    msgBody_ = Response.getDefaultInstance();
                }
                responseBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
                        Response, Response.Builder, ResponseOrBuilder>(
                        (Response) msgBody_,
                        getParentForChildren(),
                        isClean());
                msgBody_ = null;
            }
            msgBodyCase_ = 2;
            onChanged();
            ;
            return responseBuilder_;
        }

        private com.google.protobuf.SingleFieldBuilderV3<
                Notification, Notification.Builder, NotificationOrBuilder> notificationBuilder_;

        /**
         * <code>.tr451_vomci_nbi_message.v1.Notification notification = 3;</code>
         *
         * @return Whether the notification field is set.
         */
        @Override
        public boolean hasNotification() {
            return msgBodyCase_ == 3;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Notification notification = 3;</code>
         *
         * @return The notification.
         */
        @Override
        public Notification getNotification() {
            if (notificationBuilder_ == null) {
                if (msgBodyCase_ == 3) {
                    return (Notification) msgBody_;
                }
                return Notification.getDefaultInstance();
            } else {
                if (msgBodyCase_ == 3) {
                    return notificationBuilder_.getMessage();
                }
                return Notification.getDefaultInstance();
            }
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Notification notification = 3;</code>
         */
        public Builder setNotification(Notification value) {
            if (notificationBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                msgBody_ = value;
                onChanged();
            } else {
                notificationBuilder_.setMessage(value);
            }
            msgBodyCase_ = 3;
            return this;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Notification notification = 3;</code>
         */
        public Builder setNotification(
                Notification.Builder builderForValue) {
            if (notificationBuilder_ == null) {
                msgBody_ = builderForValue.build();
                onChanged();
            } else {
                notificationBuilder_.setMessage(builderForValue.build());
            }
            msgBodyCase_ = 3;
            return this;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Notification notification = 3;</code>
         */
        public Builder mergeNotification(Notification value) {
            if (notificationBuilder_ == null) {
                if (msgBodyCase_ == 3 &&
                        msgBody_ != Notification.getDefaultInstance()) {
                    msgBody_ = Notification.newBuilder((Notification) msgBody_)
                            .mergeFrom(value).buildPartial();
                } else {
                    msgBody_ = value;
                }
                onChanged();
            } else {
                if (msgBodyCase_ == 3) {
                    notificationBuilder_.mergeFrom(value);
                }
                notificationBuilder_.setMessage(value);
            }
            msgBodyCase_ = 3;
            return this;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Notification notification = 3;</code>
         */
        public Builder clearNotification() {
            if (notificationBuilder_ == null) {
                if (msgBodyCase_ == 3) {
                    msgBodyCase_ = 0;
                    msgBody_ = null;
                    onChanged();
                }
            } else {
                if (msgBodyCase_ == 3) {
                    msgBodyCase_ = 0;
                    msgBody_ = null;
                }
                notificationBuilder_.clear();
            }
            return this;
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Notification notification = 3;</code>
         */
        public Notification.Builder getNotificationBuilder() {
            return getNotificationFieldBuilder().getBuilder();
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Notification notification = 3;</code>
         */
        @Override
        public NotificationOrBuilder getNotificationOrBuilder() {
            if ((msgBodyCase_ == 3) && (notificationBuilder_ != null)) {
                return notificationBuilder_.getMessageOrBuilder();
            } else {
                if (msgBodyCase_ == 3) {
                    return (Notification) msgBody_;
                }
                return Notification.getDefaultInstance();
            }
        }

        /**
         * <code>.tr451_vomci_nbi_message.v1.Notification notification = 3;</code>
         */
        private com.google.protobuf.SingleFieldBuilderV3<
                Notification, Notification.Builder, NotificationOrBuilder>
        getNotificationFieldBuilder() {
            if (notificationBuilder_ == null) {
                if (!(msgBodyCase_ == 3)) {
                    msgBody_ = Notification.getDefaultInstance();
                }
                notificationBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
                        Notification, Notification.Builder, NotificationOrBuilder>(
                        (Notification) msgBody_,
                        getParentForChildren(),
                        isClean());
                msgBody_ = null;
            }
            msgBodyCase_ = 3;
            onChanged();
            ;
            return notificationBuilder_;
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


        // @@protoc_insertion_point(builder_scope:tr451_vomci_nbi_message.v1.Body)
    }

    // @@protoc_insertion_point(class_scope:tr451_vomci_nbi_message.v1.Body)
    private static final Body DEFAULT_INSTANCE;

    static {
        DEFAULT_INSTANCE = new Body();
    }

    public static Body getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Body>
            PARSER = new com.google.protobuf.AbstractParser<Body>() {
        @Override
        public Body parsePartialFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return new Body(input, extensionRegistry);
        }
    };

    public static com.google.protobuf.Parser<Body> parser() {
        return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<Body> getParserForType() {
        return PARSER;
    }

    @Override
    public Body getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

}

