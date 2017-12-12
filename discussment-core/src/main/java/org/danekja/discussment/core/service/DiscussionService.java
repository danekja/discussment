package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
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
     * Create a new discussion without a topic
     *
     * @param discussion new discussion
     * @return new discussion
     */
    Discussion createDiscussion(Discussion discussion) throws AccessDeniedException;

    /**
     * Create a new discussion in the topic
     *
     * @param discussion new discussion
     * @param topic topic of the discussion
     * @return new discussion
     */
    Discussion createDiscussion(Discussion discussion, Topic topic) throws AccessDeniedException;

    /**
     * Get all discussions in the forum based on its topic.
     *
     * @param topic topic of the discussions
     * @return list of Discussion
     */
    List<Discussion> getDiscussionsByTopic(Topic topic) throws AccessDeniedException;

    /**
     * Get a discussion in the forum based on its id.
     *
     * @param discussionId discussion id
     * @return discussion by id
     */
    Discussion getDiscussionById(long discussionId) throws AccessDeniedException;

    /**
     * Remove a discussion
     *
     * @param discussion discussion to remove
     */
    void removeDiscussion(Discussion discussion) throws AccessDeniedException;

    /**
     * Returns the username of the last post in the discussion.
     * @param discussion
     * @return Username. Empty string if discussion has no posts.
     */
    String getLastPostAuthor(Discussion discussion) throws DiscussionUserNotFoundException, AccessDeniedException;
}
