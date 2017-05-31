package org.danekja.discussment.core.service;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface contains service methods for working with the posts
 */
public interface PostService {

    /**
     * Remove a post in the discussion
     *
     * @param post post to remove
     */
    void removePost(Post post);

    /**
     * Get a post in the discussion based on its id.
     *
     * @param postId post id
     * @return post by id
     */
    Post getPostById(long postId);

    /**
     * Send reply on the post.
     *
     * @param reply new reply
     * @param post
     * @return reply
     */
    Post sendReply(Post reply, Post post);

    /**
     * Send a new post in the discussion
     *
     * @param discussion
     * @param post new post
     * @return new post
     */
    Post sendPost(Discussion discussion, Post post);

    /**
     * Disable the post
     *
     * @param post post which to be disabled
     * @return disabled post
     */
    Post disablePost(Post post);

    /**
     * Enable the post
     *
     * @param post post which to be enabled
     * @return enabled post
     */
    Post enablePost(Post post);

    /**
     *
     * @param discussion discussion for which the posts are returned
     * @return list of all posts belonging to the discussion including all their replies (end their replies and so on)
     *          and empty list if there are no posts or the discussion doesnt exist/is null
     */
    List<Post> listPostHierarchy(Discussion discussion);
}
