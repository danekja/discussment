package org.danekja.discussment.core.configuration.service.imp;

import org.danekja.discussment.core.configuration.service.ConfigurationService;

public class DefaultConfigurationService implements ConfigurationService {
    /** maximum level of replies added to a post, zero-based */
    private final int maxReplyLevel;

    public DefaultConfigurationService() {
        this.maxReplyLevel = UNLIMITED_REPLY_LEVEL;
    }

    public DefaultConfigurationService(int maxReplyLevel) {
        this.maxReplyLevel = maxReplyLevel;
    }

    @Override
    public int maxReplyLevel() {
        return maxReplyLevel;
    }
}
