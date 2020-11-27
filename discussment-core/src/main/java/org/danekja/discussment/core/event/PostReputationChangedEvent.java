package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.Post;

public class PostReputationChangedEvent extends PostEvent {
    public PostReputationChangedEvent(Post post) {
        super(post);
    }
}
