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
public class TopicDaoJPA extends GenericDaoJPA<Topic> implements TopicDao {

    public TopicDaoJPA(EntityManager em) {
        super(Topic.class, em);
    }

    public List<Topic> getTopicsByCategory(Category category) {
        TypedQuery<Topic> q = em.createNamedQuery(Topic.GET_TOPICS_BY_CATEGORY_ID, Topic.class);
        q.setParameter("categoryId", category.getId());
        return q.getResultList();
    }

    public List<Topic> getTopicsWithoutCategory() {
        TypedQuery<Topic> q = em.createNamedQuery(Topic.GET_TOPICS_WITHOUT_CATEGORY, Topic.class);
        return q.getResultList();
    }

    public void helloWorld(){
        String hw = "Hello world";
    }

}
