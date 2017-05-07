package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.dao.CategoryDao;
import cz.zcu.fav.kiv.discussion.core.dao.TopicDao;
import cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity;
import cz.zcu.fav.kiv.discussion.core.entity.TopicEntity;
import cz.zcu.fav.kiv.discussion.core.model.CategoryModel;
import cz.zcu.fav.kiv.discussion.core.model.TopicModel;
import cz.zcu.fav.kiv.discussion.core.utils.MapUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class TopicService {

    private static TopicDao topicDao = new TopicDao();
    private static CategoryDao categoryDao = new CategoryDao();

    public static TopicModel createTopic(String name, String description) {

        return createTopic(name, description, CategoryModel.WITHOUT_CATEGORY);

    }

    public static TopicModel createTopic(String name, String description, long categoryId) {

        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setName(name);
        topicEntity.setDescription(description);

        CategoryEntity categoryEntity = categoryDao.getById(categoryId);

        categoryEntity.getTopics().add(topicEntity);
        topicEntity.setCategory(categoryEntity);

        topicDao.save(topicEntity);

        return MapUtil.mapTopicEntityToModel(topicEntity);

    }

    public static TopicModel getTopicById(long topicId) {
        return MapUtil.mapTopicEntityToModel(topicDao.getById(topicId));
    }

    public static List<TopicModel> getTopicsByCategoryId(long categoryId) {

        List<TopicModel> topics = new ArrayList<TopicModel>();

        List<TopicEntity> entities = topicDao.getTopicsByCategoryId(categoryId);
        for (TopicEntity entity: entities) {
            topics.add(MapUtil.mapTopicEntityToModel(entity));
        }

        return topics;

    }

    public static List<TopicModel> getTopicsWithoutCategory() {

        List<TopicModel> topics = new ArrayList<TopicModel>();

        List<TopicEntity> entities = topicDao.getTopicsWithoutCategory();
        for (TopicEntity entity: entities) {
            topics.add(MapUtil.mapTopicEntityToModel(entity));
        }

        return topics;

    }

    public static void removeTopicById(long topicId) {
        TopicEntity topic = topicDao.getById(topicId);
        topic.getCategory().getTopics().remove(topic);
        topicDao.remove(topic);
    }

}
