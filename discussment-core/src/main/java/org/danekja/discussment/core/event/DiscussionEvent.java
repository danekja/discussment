package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.Discussion;

/**
 * Abstract event with {@link Discussion} object as payload.
 */
public abstract class DiscussionEvent {
    private final Discussion discussion;

    public DiscussionEvent(Discussion discussion) {
        this.discussion = discussion;
    }

    public Discussion getDiscussion() {
        return discussion;
    }
}
