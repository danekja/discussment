package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;

import java.util.List;
import java.util.Map;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface extends GenericDao on methods for working with posts in a database
 */
public interface PostDao extends GenericDao<Long, Post> {

    /**
     * Get posts in a database based on its discussion
     *
     * @param discussion Discussion containing posts
     * @return list of Post
     */
    List<Post> getPostsByDiscussion(Discussion discussion);

    /**
     * Gets base posts in discussion
     *
     * @param discussion Discussion containing posts
     * @return list of Post
     */
    List<Post> getBasePostsByDiscussion(Discussion discussion);

    /**
     * Gets replies for post
     *
     * @param post Post containing replies
     * @return list of replies
     */
    List<Post> getRepliesForPost(Post post);

    /**
     * Get last post in the discussion
     *
     * @param discussion Discussion containing posts
     * @return last post in the discussion or null if discussion has no posts
     */
    Post getLastPost(Discussion discussion);

    /**
     * Gets number of posts in the discussion
     *
     * @param discussion Discussion containing posts
     * @return number of posts in the discussion
     */
    long getNumberOfPosts(Discussion discussion);

    /**
     * Gets numbers of posts in discussions as a map where key is discussion ID and value is number of posts.
     *
     * @param discussionIds Identifiers of discussions.
     */
    Map<Long, Long> getNumbersOfPosts(List<Long> discussionIds);

    /**
     * Returns posts with given ids.
     *
     * @param postIds
     * @return
     */
    List<Post> getPostsByIds(List<Long> postIds);
}
