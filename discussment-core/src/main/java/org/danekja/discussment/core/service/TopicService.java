package org.danekja.discussment.core.service;

import org.danekja.discussment.core.dao.ITopicDao;
import org.danekja.discussment.core.dao.jpa.TopicJPA;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;

import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class TopicService {

    private static ITopicDao topicDao = new TopicJPA();

    public static Topic createTopic(Topic topic) {
        return createTopic(topic, null);
    }


    public static Topic createTopic(Topic topic, Category category) {

        if (category == null) {
            category = CategoryService.getCategoryById(Category.WITHOUT_CATEGORY);
        }

        category.getTopics().add(topic);
        topic.setCategory(category);

        return topicDao.save(topic);

    }

    public static Topic getTopicById(long topicId) {
        return topicDao.getById(topicId);
    }

    public static List<Topic> getTopicsByCategory(Category category) {

        return topicDao.getTopicsByCategory(category);
    }

    public static List<Topic> getTopicsWithoutCategory() {

        return topicDao.getTopicsWithoutCategory();
    }

    public static void removeTopic(Topic topic) {

        topic.getCategory().getTopics().remove(topic);
        topicDao.remove(topic);
    }

}
