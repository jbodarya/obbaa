/*
 */

package org.broadband_forum.obbaa.dhcp.exception;

/**
 * <p>
 * Used when the configuration is already synchronous
 * </p>
 */
public class MessageFormatterSyncException extends MessageFormatterException {

    public MessageFormatterSyncException(String message) {
        super(message);
    }

    public MessageFormatterSyncException(String message, Throwable cause) {
        super(message, cause);
    }
}
