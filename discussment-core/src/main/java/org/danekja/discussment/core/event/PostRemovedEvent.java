package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.Post;

public class PostRemovedEvent extends PostEvent {
    public PostRemovedEvent(Post post) {
        super(post);
    }
}
