package org.danekja.discussment.core.mock;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;

public class User implements IDiscussionUser {
    private final String userId;
    private final String displayName;

    public User(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    @Override
    public String getDiscussionUserId() {
        return userId;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}