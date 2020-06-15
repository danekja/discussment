package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.Post;

public class PostEnabledEvent extends PostEvent {
    public PostEnabledEvent(Post post) {
        super(post);
    }
}
