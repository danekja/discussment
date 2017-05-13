package cz.zcu.fav.kiv.discussion.core.dao;

import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.TopicEntity;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 */
public interface IDiscussionDao extends IGenericDao<DiscussionEntity> {
    List<DiscussionEntity> getDiscussionsByTopic(TopicEntity topicEntity);
}
