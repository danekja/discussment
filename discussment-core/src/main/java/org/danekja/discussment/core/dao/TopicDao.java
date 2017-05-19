package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 */
public interface TopicDao extends GenericDao<Topic> {
    List<Topic> getTopicsByCategory(Category category);

    List<Topic> getTopicsWithoutCategory();
}
