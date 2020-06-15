package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.Discussion;

public class DiscussionCreatedEvent extends DiscussionEvent {
    public DiscussionCreatedEvent(Discussion discussion) {
        super(discussion);
    }
}
