package cz.zcu.fav.kiv.discussion.core.dao.jpa;

import cz.zcu.fav.kiv.discussion.core.dao.ITopicDao;
import cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity;
import cz.zcu.fav.kiv.discussion.core.entity.TopicEntity;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class TopicJPA extends GenericJPA<TopicEntity> implements ITopicDao {

    public TopicJPA() {
        super(TopicEntity.class);
    }

    public List<TopicEntity> getTopicsByCategory(CategoryEntity category) {
        TypedQuery<TopicEntity> q = em.createNamedQuery(TopicEntity.GET_TOPICS_BY_CATEGORY_ID, TopicEntity.class);
        q.setParameter("categoryId", category.getId());
        return q.getResultList();
    }

    public List<TopicEntity> getTopicsWithoutCategory() {
        TypedQuery<TopicEntity> q = em.createNamedQuery(TopicEntity.GET_TOPICS_WITHOUT_CATEGORY, TopicEntity.class);
        return q.getResultList();
    }

}
