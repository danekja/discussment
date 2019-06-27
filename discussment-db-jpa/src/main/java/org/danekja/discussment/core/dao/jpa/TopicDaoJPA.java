package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.dao.TopicDao;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class TopicDaoJPA extends GenericDaoJPA<Long, Topic> implements TopicDao {

    /**
     * Constructor used with container managed entity manager
     */
    public TopicDaoJPA() {
        super(Topic.class);
    }

    public TopicDaoJPA(EntityManager em) {
        super(Topic.class, em);
    }

    public List<Topic> getTopicsByCategory(Category category) {
        TypedQuery<Topic> q = em.createNamedQuery(Topic.GET_TOPICS_BY_CATEGORY_ID, Topic.class);
        q.setParameter("categoryId", category.getId());
        return q.getResultList();
    }
}
