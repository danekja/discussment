package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.PostReputation;

/**
 * The interface extends GenericDao on methods for working with post reputation in a database
 *
 * Date: 17.2.18
 *
 * @author Jiri Kryda
 */
public interface PostReputationDao extends GenericDao<Long, PostReputation> {

    /**
     * Get post reputation in a database based on its post
     *
     * @param post Post containing post reputation
     * @return Post reputation for post
     */
    PostReputation getByPost(Post post);
}
