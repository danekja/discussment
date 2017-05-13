package cz.zcu.fav.kiv.discussion.core.dao.jpa;

import cz.zcu.fav.kiv.discussion.core.dao.IDiscussionDao;
import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.TopicEntity;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Martin Bláha on 19.01.17.
 */
public class DiscussionJPA extends GenericJPA<DiscussionEntity> implements IDiscussionDao {

    public DiscussionJPA() {
        super(DiscussionEntity.class);
    }

    public List<DiscussionEntity> getDiscussionsByTopic(TopicEntity topicEntity) {
        TypedQuery<DiscussionEntity> q = em.createNamedQuery(DiscussionEntity.GET_DISCUSSIONS_BY_TOPIC_ID, DiscussionEntity.class);
        q.setParameter("topicId", topicEntity.getId());
        return q.getResultList();
    }
}
