package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.dao.DiscussionDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Martin Bláha on 19.01.17.
 */
public class DiscussionDaoJPA extends GenericDaoJPA<Discussion> implements DiscussionDao {

    public DiscussionDaoJPA() {
        super(Discussion.class);
    }

    public List<Discussion> getDiscussionsByTopic(Topic topic) {
        TypedQuery<Discussion> q = em.createNamedQuery(Discussion.GET_DISCUSSIONS_BY_TOPIC_ID, Discussion.class);
        q.setParameter("topicId", topic.getId());
        return q.getResultList();
    }
}

