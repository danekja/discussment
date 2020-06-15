package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.UserPostReputation;

public class UserPostReputationChangedEvent extends UserPostReputationEvent {
    public UserPostReputationChangedEvent(UserPostReputation userPostReputation) {
        super(userPostReputation);
    }
}
