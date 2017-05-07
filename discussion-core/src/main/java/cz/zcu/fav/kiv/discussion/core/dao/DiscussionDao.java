package cz.zcu.fav.kiv.discussion.core.dao;

import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Martin Bláha on 19.01.17.
 */
public class DiscussionDao extends GenericDao<DiscussionEntity> {

    public DiscussionDao() {
        super(DiscussionEntity.class);
    }

    public List<DiscussionEntity> getDiscussionsByTopicId(long topicId) {
        TypedQuery<DiscussionEntity> q = em.createNamedQuery(DiscussionEntity.GET_DISCUSSIONS_BY_TOPIC_ID, DiscussionEntity.class);
        q.setParameter("topicId", topicId);
        return q.getResultList();
    }
}
