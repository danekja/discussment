package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.dao.DiscussionDao;
import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.TopicEntity;

import java.util.List;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class DiscussionService {

    private static DiscussionDao discussionDao = new DiscussionDao();

    public static DiscussionEntity createDiscussion(DiscussionEntity discussionEntity) {

        return discussionDao.save(discussionEntity);
    }

    public static DiscussionEntity createDiscussion(DiscussionEntity discussionEntity, TopicEntity topic) {

        topic.getDiscussions().add(discussionEntity);
        discussionEntity.setTopic(topic);

        return discussionDao.save(discussionEntity);
    }

    public static List<DiscussionEntity> getDiscussionsByTopic(TopicEntity topicEntity) {

        return discussionDao.getDiscussionsByTopic(topicEntity);
    }

    public static DiscussionEntity getDiscussionById(long discussionId) {

        return discussionDao.getById(discussionId);
    }

    public static void removeDiscussion(DiscussionEntity discussionEntity) {

        if (discussionEntity.getTopic() != null) {
            discussionEntity.getTopic().getDiscussions().remove(discussionEntity);
        }
        discussionDao.remove(discussionEntity);
    }

}

