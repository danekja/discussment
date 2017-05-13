package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.dao.TopicDao;
import cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity;
import cz.zcu.fav.kiv.discussion.core.entity.TopicEntity;

import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class TopicService {

    private static TopicDao topicDao = new TopicDao();

    public static TopicEntity createTopic(TopicEntity topicEntity) {
        return createTopic(topicEntity, null);
    }


    public static TopicEntity createTopic(TopicEntity topicEntity, CategoryEntity categoryEntity) {

        if (categoryEntity == null) {
            categoryEntity = CategoryService.getCategoryById(CategoryEntity.WITHOUT_CATEGORY);
        }

        categoryEntity.getTopics().add(topicEntity);
        topicEntity.setCategory(categoryEntity);

        return topicDao.save(topicEntity);

    }

    public static TopicEntity getTopicById(long topicId) {
        return topicDao.getById(topicId);
    }

    public static List<TopicEntity> getTopicsByCategory(CategoryEntity categoryEntity) {

        return topicDao.getTopicsByCategory(categoryEntity);
    }

    public static List<TopicEntity> getTopicsWithoutCategory() {

        return topicDao.getTopicsWithoutCategory();
    }

    public static void removeTopic(TopicEntity topic) {

        topic.getCategory().getTopics().remove(topic);
        topicDao.remove(topic);
    }

}
