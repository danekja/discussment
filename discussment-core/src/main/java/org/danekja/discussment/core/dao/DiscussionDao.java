package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.domain.UserDiscussion;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface extends GenericDao on methods for working with discussions in a database
 */
public interface DiscussionDao extends GenericDao<Long, Discussion> {


    /**
     * Get discussions in a database based on its topic
     *
     * @param topic Topic containing discussions
     * @return list of Discussion
     */
    List<Discussion> getDiscussionsByTopic(Topic topic);

    /**
     * Saves the userDiscussion entity.
     * @param userDiscussion Connection between user and discussion to be saved.
     * @return Saved object.
     */
    UserDiscussion addAccessToDiscussion(UserDiscussion userDiscussion);
}
