package org.danekja.discussment.core.exception;

/**
 * Thrown when maximum reply level configured in {@link org.danekja.discussment.core.configuration.service.ConfigurationService}
 * is exceeded when adding a reply.
 */
public class MaxReplyLevelExceeded extends Exception {
    public MaxReplyLevelExceeded() {
        super("Maximum level of replies added to a post exceeded!");
    }
}
