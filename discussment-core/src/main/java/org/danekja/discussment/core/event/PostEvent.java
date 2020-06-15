package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.Post;

/**
 * Abstract event with {@link Post} object as payload.
 */
public abstract class PostEvent {
    private final Post post;

    public PostEvent(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}
