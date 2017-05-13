package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.dao.IDiscussionDao;
import cz.zcu.fav.kiv.discussion.core.dao.jpa.DiscussionJPA;
import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.TopicEntity;

import java.util.List;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class DiscussionService {

    private static IDiscussionDao discussionJPA = new DiscussionJPA();

    public static DiscussionEntity createDiscussion(DiscussionEntity discussionEntity) {

        return discussionJPA.save(discussionEntity);
    }

    public static DiscussionEntity createDiscussion(DiscussionEntity discussionEntity, TopicEntity topic) {

        topic.getDiscussions().add(discussionEntity);
        discussionEntity.setTopic(topic);

        return discussionJPA.save(discussionEntity);
    }

    public static List<DiscussionEntity> getDiscussionsByTopic(TopicEntity topicEntity) {

        return discussionJPA.getDiscussionsByTopic(topicEntity);
    }

    public static DiscussionEntity getDiscussionById(long discussionId) {

        return discussionJPA.getById(discussionId);
    }

    public static void removeDiscussion(DiscussionEntity discussionEntity) {

        if (discussionEntity.getTopic() != null) {
            discussionEntity.getTopic().getDiscussions().remove(discussionEntity);
        }
        discussionJPA.remove(discussionEntity);
    }

}

