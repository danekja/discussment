package org.danekja.discussment.core.dao.hibernate;

import org.danekja.discussment.core.dao.TopicDao;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Jakub Danek on 28.01.17.
 */
public class TopicDaoHibernate extends GenericDaoHibernate<Long, Topic> implements TopicDao {

    public TopicDaoHibernate(SessionFactory sessionFactory) {
        super(Topic.class, sessionFactory);
    }

    public List<Topic> getTopicsByCategory(Category category) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Topic> q = session.createNamedQuery(Topic.GET_TOPICS_BY_CATEGORY_ID, Topic.class);
        q.setParameter("categoryId", category.getId());
        return q.getResultList();
    }
}
