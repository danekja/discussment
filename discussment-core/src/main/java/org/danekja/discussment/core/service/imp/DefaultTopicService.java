package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.dao.CategoryDao;
import org.danekja.discussment.core.dao.DiscussionDao;
import org.danekja.discussment.core.dao.TopicDao;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.TopicService;

import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class DefaultTopicService implements TopicService {

    private TopicDao topicDao;
    private DefaultCategoryService categoryService;
    private DiscussionDao discussionDao;

    public DefaultTopicService(TopicDao topicDao, CategoryDao categoryDao) {
        this.topicDao = topicDao;
        categoryService = new DefaultCategoryService(categoryDao);
        this.discussionDao = new DiscussionDaoJPA();
    }

    public Topic createTopic(Topic topic) {
        return createTopic(topic, null);
    }


    public Topic createTopic(Topic topic, Category category) {

        // category is null => try to resolve it
        if (category == null) {
            if(topic.getCategory() != null && topic.getCategory().getId() != null) {
                category = categoryService.getCategoryById(topic.getCategory().getId());
            }
        }

        // category was successfully resolved
        if (category != null) {
            category.getTopics().add(topic);
            topic.setCategory(category);
        }


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

        for(Discussion d : topic.getDiscussions()) {
            d.getUserAccessList().clear();
            discussionDao.save(d);
        }

        topicDao.remove(topic);
    }

}
