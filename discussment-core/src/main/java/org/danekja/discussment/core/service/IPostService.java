package org.danekja.discussment.core.service;

import java.util.List;

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

    /**
     *
     * @param discussion discussion for which the posts are returned
     * @return list of all posts belonging to the discussion including all their replies (end their replies and so on)
     *          and empty list if there are no posts or the discussion doesnt exist/is null
     */
    List<Post> listPostHierarchy(Discussion discussion);
}
