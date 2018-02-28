package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface contains service methods for working with the topics
 */
public interface TopicService {

    /**
     * Add a new topic to the discussion with category
     *
     * @param category category of the topic
     * @param topic new topic
     * @return New topic
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to post to category.
     */
    Topic createTopic(Category category, Topic topic) throws AccessDeniedException;

    /**
     * Get a topic in the discussion based on its id.
     *
     * @param topicId topic id
     * @return topic by id
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to view topics in parent category.
     */
    Topic getTopicById(long topicId) throws AccessDeniedException;

    /**
     * Get all topics in the discussion based on its category.
     *
     * @param category category of the topic
     * @return list of Topic
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to view topics in parent category.
     */
    List<Topic> getTopicsByCategory(Category category) throws AccessDeniedException;

    /**
     * Get all topics in a discussion without a category.
     *
     * @return list of Topic
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to view topics in the default category.
     */
    List<Topic> getTopicsWithoutCategory();

    /**
     * Remove the topic in the discussion
     *
     * @param topic topic to remove
     * @throws AccessDeniedException Thrown if the current user doesn't have permissions to remove the topic.
     */
    void removeTopic(Topic topic) throws AccessDeniedException;
}
