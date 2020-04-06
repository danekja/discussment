package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface contains service methods for working with discussions.
 * Each method should check permissions of currently logged user.
 */
public interface DiscussionService {

    /**
     * Create a new discussion in the topic
     *
     * @param topic topic of the discussion
     * @param discussion new discussion
     * @return new discussion
     * @throws AccessDeniedException when user is not allowed to commit the action
     */
    Discussion createDiscussion(Topic topic, Discussion discussion) throws AccessDeniedException;

    /**
     * Get all discussions in the forum based on its topic.
     *
     * @param topic topic of the discussions
     * @return list of Discussion
     * @throws AccessDeniedException when user is not allowed to commit the action
     */
    List<Discussion> getDiscussionsByTopic(Topic topic) throws AccessDeniedException;

    /**
     * Get a discussion in the forum based on its id.
     *
     * @param discussionId discussion id
     * @return discussion by id
     * @throws AccessDeniedException when user is not allowed to commit the action
     */
    Discussion getDiscussionById(long discussionId) throws AccessDeniedException;

    /**
     * Gets default discussion or creates one if there isn't any.
     * Its id can be found in Discussion.java variable DEFAULT_DISCUSSION_ID.
     *
     * @return default discussion
     */
    Discussion getDefaultDiscussion();

    /**
     * Remove a discussion
     *
     * @param discussion discussion to remove
     * @throws AccessDeniedException when user is not allowed to commit the action
     */
    void removeDiscussion(Discussion discussion) throws AccessDeniedException;
}
