package org.danekja.discussment.core.service;

import javafx.geometry.Pos;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.exception.MaxReplyLevelExceeded;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface contains service methods for working with the posts.
 * Each method should check permissions of currently logged user.
 */
public interface PostService {

    /**
     * Remove a post in the discussion and its chain of replies.
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
     * @param post post to which the reply is added
     * @return reply
     * @throws MaxReplyLevelExceeded Thrown when adding reply to post would exceed maximum reply level.
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to add posts to parent discussion.
     */
    Post sendReply(Post reply, Post post) throws MaxReplyLevelExceeded, AccessDeniedException;

    /**
     * Send a new post in the discussion
     *
     * @param discussion discussion to which post is added
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
     * Returns the post's author.
     * @param post post for which author is returned
     * @return Post's author.
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to view the post.
     * @throws DiscussionUserNotFoundException Thrown if the user can be found by post.getUserId() id.
     */
    IDiscussionUser getPostAuthor(Post post) throws DiscussionUserNotFoundException, AccessDeniedException;

    /**
     * Returns true if the current user is post author.
     * @param post post for which author is checked
     * @return True if the current user is post author, otherwise false.
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to view the post.
     * @throws DiscussionUserNotFoundException Thrown if the user can be found by post.getUserId() id.
     */
    boolean isPostAuthor(Post post) throws DiscussionUserNotFoundException, AccessDeniedException;

    /**
     * Returns the last post in the discussion.
     * @param discussion discussion to get the last post for
     * @return Last post in the discussion.
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to view posts in the discussion.
     */
    Post getLastPost(Discussion discussion) throws AccessDeniedException;

    /**
     *
     * @param post post for which the replies are returned
     * @return list of all replies belonging to the post
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to view the post.
     */
    List<Post> getReplies(Post post) throws AccessDeniedException;

    /**
     * Counts how many posts are in the discussion
     * @param discussion discussion to count posts
     * @return number of posts in the discussion
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to view posts in the discussion.
     */
    long getNumberOfPosts(Discussion discussion) throws AccessDeniedException;

    /**
     * Counts how many posts are in discussions
     * @param discussionIds identifiers of discussions to count posts for
     * @return number of posts in the discussion
     */
    Map<Long, Long> getNumbersOfPosts(List<Long> discussionIds);

    /**
     * Returns authors for given posts. This method can be used to fix N+1 problem which raises when
     * loading authors of posts in discussion one by one.
     *
     * @param posts Collections of posts.
     * @return Map of postId -> author.
     */
    default Map<Long, IDiscussionUser> getPostsAuthors(List<Post> posts) {
        return Collections.emptyMap();
    }
}
