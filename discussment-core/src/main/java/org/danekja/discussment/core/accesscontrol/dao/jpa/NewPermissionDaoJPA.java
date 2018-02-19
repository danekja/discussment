package org.danekja.discussment.core.accesscontrol.dao.jpa;

import org.danekja.discussment.core.accesscontrol.dao.NewPermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.*;
import org.danekja.discussment.core.dao.jpa.GenericDaoJPA;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;

public class NewPermissionDaoJPA extends GenericDaoJPA<PermissionId, AbstractPermission> implements NewPermissionDao {

    public NewPermissionDaoJPA(EntityManager em) {
        super(AbstractPermission.class, em);
    }

    @Override
    public List<PostPermission> findForUser(IDiscussionUser user, Long discussionId, Long topicId, Long categoryId) {
        TypedQuery<PostPermission> q = em.createNamedQuery(PostPermission.QUERY_BY_USER, PostPermission.class);

        q.setParameter(PostPermission.PARAM_USER_ID, user.getDiscussionUserId());
        q.setParameter(PostPermission.PARAM_DISCUSSION_ID, discussionId);
        q.setParameter(PostPermission.PARAM_TOPIC_ID, topicId);
        q.setParameter(PostPermission.PARAM_CATEGORY_ID, categoryId);

        return q.getResultList();
    }

    @Override
    public List<DiscussionPermission> findForUser(IDiscussionUser user, Long topicId, Long categoryId) {
        TypedQuery<DiscussionPermission> q = em.createNamedQuery(DiscussionPermission.QUERY_BY_USER, DiscussionPermission.class);

        q.setParameter(DiscussionPermission.PARAM_USER_ID, user.getDiscussionUserId());
        q.setParameter(DiscussionPermission.PARAM_TOPIC_ID, topicId);
        q.setParameter(DiscussionPermission.PARAM_CATEGORY_ID, categoryId);

        return q.getResultList();
    }

    @Override
    public List<TopicPermission> findForUser(IDiscussionUser user, Long categoryId) {
        TypedQuery<TopicPermission> q = em.createNamedQuery(TopicPermission.QUERY_BY_USER, TopicPermission.class);

        q.setParameter(TopicPermission.PARAM_USER_ID, user.getDiscussionUserId());
        q.setParameter(TopicPermission.PARAM_CATEGORY_ID, categoryId);

        return q.getResultList();
    }

    @Override
    public List<CategoryPermission> findForUser(IDiscussionUser user) {
        TypedQuery<CategoryPermission> q = em.createNamedQuery(CategoryPermission.QUERY_BY_USER, CategoryPermission.class);

        q.setParameter(TopicPermission.PARAM_USER_ID, user.getDiscussionUserId());

        return q.getResultList();
    }

    @Override
    public <Z extends AbstractPermission> List<Z> findByType(IDiscussionUser user, Class<Z> type, PermissionLevel level, Long itemId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Z> cq = cb.createQuery(type);
        Path<PermissionId> root = cq.from(type).get("id");

        Predicate userPredicate = cb.equal(root.get("userId"), user.getDiscussionUserId());
        Predicate levelPredicate = cb.equal(root.get("level"), level);
        Predicate itemPredicate = cb.equal(root.get("itemId"), itemId);
        cq.where(userPredicate, levelPredicate, itemPredicate);

        TypedQuery<Z> q = em.createQuery(cq);

        return q.getResultList();
    }
}
