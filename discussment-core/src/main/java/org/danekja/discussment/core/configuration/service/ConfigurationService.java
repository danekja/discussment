package org.danekja.discussment.core.configuration.service;

/**
 * Service holding configuration values.
 */
public interface ConfigurationService {
    int UNLIMITED_REPLY_LEVEL = Integer.MAX_VALUE;

    /**
     * @return Max reply level of replies sent to a post. When set to {@link #UNLIMITED_REPLY_LEVEL}, no restriction is applied.
     */
    int maxReplyLevel();
}
