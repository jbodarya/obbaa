/*
 */

package org.broadband_forum.obbaa.dhcp.exception;

/**
 * <p>
 * Used when something goes wrong during the process of formatting a message
 * </p>
 */
public class MessageFormatterException extends Exception {

    public MessageFormatterException(String message) {
        super(message);
    }

    public MessageFormatterException(String message, Throwable cause) {
        super(message, cause);
    }
}
