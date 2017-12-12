package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface contains service methods for working with the posts.
 * Each method should check permissions of currently logged user.
 */
public interface PostService {

    /**
     * Remove a post in the discussion
     *
     * @param post post to remove
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to remove the post.
     */
    void removePost(Post post) throws AccessDeniedException;

    /**
     * Get a post in the discussion based on its id.
     *
     * @param postId post id
     * @return post by id
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to view the post.
     */
    Post getPostById(long postId) throws AccessDeniedException;

    /**
     * Send reply on the post.
     *
     * @param reply new reply
     * @param post
     * @return reply
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to add posts to parent discussion.
     */
    Post sendReply(Post reply, Post post) throws AccessDeniedException;

    /**
     * Send a new post in the discussion
     *
     * @param discussion
     * @param post new post
     * @return new post
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to add posts to discussion.
     */
    Post sendPost(Discussion discussion, Post post) throws AccessDeniedException;

    /**
     * Disable the post
     *
     * @param post post which to be disabled
     * @return disabled post
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to edit the post.
     */
    Post disablePost(Post post) throws AccessDeniedException;

    /**
     * Enable the post
     *
     * @param post post which to be enabled
     * @return enabled post
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to edit the post.
     */
    Post enablePost(Post post) throws AccessDeniedException;

    /**
     *
     * @param discussion discussion for which the posts are returned
     * @return list of all posts belonging to the discussion including all their replies (and their replies and so on)
     *          and empty list if there are no posts or the discussion doesn't exist/is null.
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to view posts in the discussion.
     */
    List<Post> listPostHierarchy(Discussion discussion) throws AccessDeniedException;

    /**
     * Returns the username of posts's author.
     * @param post
     * @return Name of the post's autor.
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to view the post.
     * @throws DiscussionUserNotFoundException Thrown if the user can be found by post.getUserId() id.
     */
    String getPostAuthor(Post post) throws DiscussionUserNotFoundException, AccessDeniedException;
}
