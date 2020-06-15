package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.Post;

public class PostDisabledEvent extends PostEvent {
    public PostDisabledEvent(Post post) {
        super(post);
    }
}
