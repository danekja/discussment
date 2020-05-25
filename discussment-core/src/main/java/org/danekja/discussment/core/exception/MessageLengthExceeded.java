package org.danekja.discussment.core.exception;

/**
 * Exception thrown when trying to save post that is too long.
 */
public class MessageLengthExceeded extends Exception {

    public MessageLengthExceeded(int messageLength, int limit) {
        super("Message length ("+messageLength+") exceeds the limit ("+limit+")");
    }

    public MessageLengthExceeded() {
        super("Message length exceeds the limit");
    }
}
