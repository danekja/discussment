package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.Discussion;

public class DiscussionRemovedEvent extends DiscussionEvent {
    public DiscussionRemovedEvent(Discussion discussion) {
        super(discussion);
    }
}
