package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.springframework.transaction.annotation.Transactional;

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
     */
    @Transactional
    Discussion createDiscussion(Topic topic, Discussion discussion) throws AccessDeniedException;

    /**
     * Get all discussions in the forum based on its topic.
     *
     * @param topic topic of the discussions
     * @return list of Discussion
     */
    @Transactional
    List<Discussion> getDiscussionsByTopic(Topic topic) throws AccessDeniedException;

    /**
     * Get a discussion in the forum based on its id.
     *
     * @param discussionId discussion id
     * @return discussion by id
     */
    @Transactional
    Discussion getDiscussionById(long discussionId) throws AccessDeniedException;

    /**
     * Remove a discussion
     *
     * @param discussion discussion to remove
     */
    @Transactional
    void removeDiscussion(Discussion discussion) throws AccessDeniedException;

    /**
     * Returns the author of the last post in the discussion.
     * @param discussion
     * @return Author of the last post. Empty string if discussion has no posts.
     */
    @Transactional
    IDiscussionUser getLastPostAuthor(Discussion discussion) throws DiscussionUserNotFoundException, AccessDeniedException;
}
