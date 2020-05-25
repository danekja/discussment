package org.danekja.discussment.core.configuration.service;

/**
 * Service holding configuration values.
 */
public interface ConfigurationService {
    int UNLIMITED_REPLY_LEVEL = Integer.MAX_VALUE;

    /**
     * Default for VARCHAR(255).
     */
    int DEFAULT_MESSAGE_LIMIT = 255;

    /**
     * @return Max reply level of replies sent to a post. When set to {@link #UNLIMITED_REPLY_LEVEL}, no restriction is applied.
     */
    int maxReplyLevel();

    /**
     * @return Max length of post's text. Should correspond with the type used in database.
     */
    int messageLengthLimit();
}
