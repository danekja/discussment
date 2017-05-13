package cz.zcu.fav.kiv.discussion.core.dao;

import cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity;
import cz.zcu.fav.kiv.discussion.core.entity.TopicEntity;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 */
public interface ITopicDao extends IGenericDao<TopicEntity> {
    List<TopicEntity> getTopicsByCategory(CategoryEntity category);

    List<TopicEntity> getTopicsWithoutCategory();
}
