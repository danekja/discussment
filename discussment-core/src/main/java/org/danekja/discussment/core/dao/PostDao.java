package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;

import java.util.List;

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
     * Gets replies for post
     *
     * @param post Post containing replies
     * @return list of replies
     */
    List<Post> getRepliesForPost(Post post);
}
