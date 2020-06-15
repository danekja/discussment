package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.UserPostReputation;

/**
 * Abstract event with {@link UserPostReputation} object as payload.
 */
public abstract class UserPostReputationEvent {
    private final UserPostReputation userPostReputation;

    public UserPostReputationEvent(UserPostReputation userPostReputation) {
        this.userPostReputation = userPostReputation;
    }

    public UserPostReputation getUserPostReputation() {
        return userPostReputation;
    }
}
