package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.dao.CategoryDao;
import org.danekja.discussment.core.dao.TopicDao;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;

import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class DefaultTopicService implements org.danekja.discussment.core.service.TopicService {

    private TopicDao topicDao;
    private DefaultCategoryService categoryService;

    public DefaultTopicService(TopicDao topicDao, CategoryDao categoryDao) {
        this.topicDao = topicDao;
        categoryService = new DefaultCategoryService(categoryDao);
    }

    public Topic createTopic(Topic topic) {
        return createTopic(topic, null);
    }


    public Topic createTopic(Topic topic, Category category) {

        if (category == null) {
            category = categoryService.getCategoryById(Category.WITHOUT_CATEGORY);
        }

        category.getTopics().add(topic);
        topic.setCategory(category);

        return topicDao.save(topic);

    }

    public Topic getTopicById(long topicId) {
        return topicDao.getById(topicId);
    }

    public List<Topic> getTopicsByCategory(Category category) {

        return topicDao.getTopicsByCategory(category);
    }

    public List<Topic> getTopicsWithoutCategory() {

        return topicDao.getTopicsWithoutCategory();
    }

    public void removeTopic(Topic topic) {

        topicDao.remove(topic);
    }

}
