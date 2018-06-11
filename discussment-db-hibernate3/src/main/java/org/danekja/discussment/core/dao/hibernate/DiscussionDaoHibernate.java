package org.danekja.discussment.core.dao.hibernate;

import org.danekja.discussment.core.dao.DiscussionDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Jakub Danek on 19.01.17.
 */
public class DiscussionDaoHibernate extends GenericDaoHibernate<Long, Discussion> implements DiscussionDao {


    public DiscussionDaoHibernate(SessionFactory sessionFactory) {
        super(Discussion.class, sessionFactory);
    }

    public List<Discussion> getDiscussionsByTopic(Topic topic) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery(Discussion.GET_DISCUSSIONS_BY_TOPIC_ID);
        q.setParameter("topicId", topic.getId());
        return q.list();
    }


}

