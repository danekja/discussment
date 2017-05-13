package org.danekja.discussment.core.service;

import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 */
public interface ITopicService {
    Topic createTopic(Topic topic);

    Topic createTopic(Topic topic, Category category);

    Topic getTopicById(long topicId);

    List<Topic> getTopicsByCategory(Category category);

    List<Topic> getTopicsWithoutCategory();

    void removeTopic(Topic topic);
}
