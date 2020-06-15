package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.Post;

public class PostCreatedEvent extends PostEvent {
    public PostCreatedEvent(Post post) {
        super(post);
    }
}
