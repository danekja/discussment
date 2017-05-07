package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.dao.DiscussionDao;
import cz.zcu.fav.kiv.discussion.core.dao.TopicDao;
import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.TopicEntity;
import cz.zcu.fav.kiv.discussion.core.model.DiscussionModel;
import cz.zcu.fav.kiv.discussion.core.utils.MapUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class DiscussionService {

    private static DiscussionDao discussionDao = new DiscussionDao();
    private static TopicDao topicDao = new TopicDao();

    public static DiscussionModel createDiscussion(String discussionName) {
        DiscussionEntity discussionEntity = new DiscussionEntity(discussionName);


        DiscussionEntity entity = discussionDao.save(discussionEntity);

        return MapUtil.mapDiscussionEntityModal(entity);
    }

    public static DiscussionModel createDiscussion(String discussionName, long topicId) {

        TopicEntity topic = topicDao.getById(topicId);

        DiscussionEntity discussionEntity = new DiscussionEntity(discussionName);

        topic.getDiscussions().add(discussionEntity);
        discussionEntity.setTopic(topic);

        discussionEntity = discussionDao.save(discussionEntity);

        return MapUtil.mapDiscussionEntityModal(discussionEntity);
    }

    public static DiscussionModel createDiscussion(String discussionName, long topicId, String pass) {

        TopicEntity topic = topicDao.getById(topicId);

        DiscussionEntity discussionEntity = new DiscussionEntity(discussionName);

        topic.getDiscussions().add(discussionEntity);
        discussionEntity.setTopic(topic);
        discussionEntity.setPass(pass);


        discussionEntity = discussionDao.save(discussionEntity);

        return MapUtil.mapDiscussionEntityModal(discussionEntity);
    }

    public static List<DiscussionModel> getDiscussionsByTopicId(long topicId) {
        List<DiscussionModel> discussionModels = new ArrayList<DiscussionModel>();

        for (DiscussionEntity discussionEntity : discussionDao.getDiscussionsByTopicId(topicId)) {
            DiscussionModel model = MapUtil.mapDiscussionEntityModal(discussionEntity);
            discussionModels.add(model);
        }

        return discussionModels;
    }

    public static DiscussionModel getDiscussionById(long discussionId) {
        DiscussionEntity entity = discussionDao.getById(discussionId);

        if (entity == null) {
            return null;
        }

        return MapUtil.mapDiscussionEntityModal(entity);
    }

    public static void removeDiscussionById(long discussionId) {

        DiscussionEntity discussionEntity = discussionDao.getById(discussionId);
        if (discussionEntity.getTopic() != null) {
            discussionEntity.getTopic().getDiscussions().remove(discussionEntity);
        }
        discussionDao.remove(discussionEntity);
    }

}

