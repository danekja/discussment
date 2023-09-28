package org.danekja.discussment.core.dao.hibernate;

import org.danekja.discussment.core.dao.DiscussionDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Jakub Danek on 19.01.17.
 */
public class DiscussionDaoHibernate extends GenericDaoHibernate<Long, Discussion> implements DiscussionDao {


    public DiscussionDaoHibernate(SessionFactory sessionFactory) {
        super(Discussion.class, sessionFactory);
    }

    @Override
    public List<Discussion> getDiscussionsByTopic(Topic topic) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Discussion> q = session.createNamedQuery(Discussion.GET_DISCUSSIONS_BY_TOPIC_ID, Discussion.class);
        q.setParameter("topicId", topic.getId());
        return q.getResultList();
    }


}

