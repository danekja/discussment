package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.PostReputation;
import org.danekja.discussment.core.domain.UserPostReputation;

import java.util.List;

/**
 * The interface extends GenericDao on methods for working with user's post reputation in a database
 *
 * Date: 17.2.18
 *
 * @author Jiri Kryda
 */
public interface UserPostReputationDao extends GenericDao<Long, UserPostReputation> {

    /**
     * Get User post reputation in a database based on its user nad post reputation
     *
     * @param user User to check for votes
     * @param postReputation Post reputation that user voted on
     * @return User post reputation for user and post reputation
     */
    UserPostReputation getForUser(IDiscussionUser user, PostReputation postReputation);
}
