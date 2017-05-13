package org.danekja.discussment.core.service;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;

/**
 * Created by Martin Bláha on 13.05.17.
 */
public interface IPostService {
    void removePost(Post post);

    Post getPostById(long postId);

    Post sendReply(Post reply, Post post);

    Post sendPost(Discussion discussion, Post post);

    Post disablePost(Post post);

    Post enablePost(Post post);
}
