package org.danekja.discussment.core.accesscontrol.dao.hibernate;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.*;
import org.danekja.discussment.core.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Hibernate-based implementation of PermissionDao.
 */
public class PermissionDaoHibernate extends GenericDaoHibernate<PermissionId, AbstractPermission> implements PermissionDao {

    public PermissionDaoHibernate(SessionFactory sessionFactory) {
        super(AbstractPermission.class, sessionFactory);
    }

    @Override
    public List<PostPermission> findForUser(IDiscussionUser user, Long discussionId, Long topicId, Long categoryId) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<PostPermission> q = session.createNamedQuery(PostPermission.QUERY_BY_USER, PostPermission.class);

        q.setParameter(PostPermission.PARAM_USER_ID, user.getDiscussionUserId());
        q.setParameter(PostPermission.PARAM_DISCUSSION_ID, discussionId);
        q.setParameter(PostPermission.PARAM_TOPIC_ID, topicId);
        q.setParameter(PostPermission.PARAM_CATEGORY_ID, categoryId);

        return q.getResultList();
    }

    @Override
    public List<DiscussionPermission> findForUser(IDiscussionUser user, Long topicId, Long categoryId) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<DiscussionPermission> q = session.createNamedQuery(DiscussionPermission.QUERY_BY_USER, DiscussionPermission.class);

        q.setParameter(DiscussionPermission.PARAM_USER_ID, user.getDiscussionUserId());
        q.setParameter(DiscussionPermission.PARAM_TOPIC_ID, topicId);
        q.setParameter(DiscussionPermission.PARAM_CATEGORY_ID, categoryId);

        return q.getResultList();
    }

    @Override
    public List<TopicPermission> findForUser(IDiscussionUser user, Long categoryId) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<TopicPermission> q = session.createNamedQuery(TopicPermission.QUERY_BY_USER, TopicPermission.class);

        q.setParameter(TopicPermission.PARAM_USER_ID, user.getDiscussionUserId());
        q.setParameter(TopicPermission.PARAM_CATEGORY_ID, categoryId);

        return q.getResultList();
    }

    @Override
    public List<CategoryPermission> findForUser(IDiscussionUser user) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<CategoryPermission> q = session.createNamedQuery(CategoryPermission.QUERY_BY_USER, CategoryPermission.class);

        q.setParameter(TopicPermission.PARAM_USER_ID, user.getDiscussionUserId());

        return q.getResultList();
    }

    @Override
    public <Z extends AbstractPermission> List<Z> findByType(IDiscussionUser user, Class<Z> type, PermissionLevel level, Long itemId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Z> cq = cb.createQuery(type);
        Path<PermissionId> root = cq.from(type).get("id");

        Predicate userPredicate = cb.equal(root.get("userId"), user.getDiscussionUserId());
        Predicate levelPredicate = cb.equal(root.get("level"), level);
        Predicate itemPredicate = cb.equal(root.get("itemId"), itemId);
        cq.where(userPredicate, levelPredicate, itemPredicate);

        TypedQuery<Z> q = session.createQuery(cq);

        return q.getResultList();
    }
}
