package org.danekja.discussment.core.configuration.service.imp;

import org.danekja.discussment.core.configuration.service.ConfigurationService;

public class DefaultConfigurationService implements ConfigurationService {
    /** maximum level of replies added to a post, zero-based */
    private final int maxReplyLevel;

    private final int messageLengthLimit;

    public DefaultConfigurationService() {
        this.maxReplyLevel = UNLIMITED_REPLY_LEVEL;
        this.messageLengthLimit = DEFAULT_MESSAGE_LIMIT;
    }

    public DefaultConfigurationService(int maxReplyLevel, int messageLengthLimit) {
        this.maxReplyLevel = maxReplyLevel;
        this.messageLengthLimit = messageLengthLimit;
    }

    @Override
    public int maxReplyLevel() {
        return maxReplyLevel;
    }

    @Override
    public int messageLengthLimit() {
        return messageLengthLimit;
    }
}
