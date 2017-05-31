package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface extends GenericDao on methods for working with topics in a database
 */
public interface TopicDao extends GenericDao<Topic> {

    /**
     * Get topics in a database based on its category
     *
     * @param category Category containing topics
     * @return list of Topic
     */
    List<Topic> getTopicsByCategory(Category category);


    /**
     * Get topics without category in a database.
     *
     * @return list of Topic
     */
    List<Topic> getTopicsWithoutCategory();
}
