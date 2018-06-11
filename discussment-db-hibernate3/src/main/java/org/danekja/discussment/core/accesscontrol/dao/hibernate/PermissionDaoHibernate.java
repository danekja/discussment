package org.danekja.discussment.core.accesscontrol.dao.hibernate;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.*;
import org.danekja.discussment.core.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

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
        Query q = session.getNamedQuery(PostPermission.QUERY_BY_USER);

        q.setParameter(PostPermission.PARAM_USER_ID, user.getDiscussionUserId());
        q.setParameter(PostPermission.PARAM_DISCUSSION_ID, discussionId);
        q.setParameter(PostPermission.PARAM_TOPIC_ID, topicId);
        q.setParameter(PostPermission.PARAM_CATEGORY_ID, categoryId);

        return q.list();
    }

    @Override
    public List<DiscussionPermission> findForUser(IDiscussionUser user, Long topicId, Long categoryId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery(DiscussionPermission.QUERY_BY_USER);

        q.setParameter(DiscussionPermission.PARAM_USER_ID, user.getDiscussionUserId());
        q.setParameter(DiscussionPermission.PARAM_TOPIC_ID, topicId);
        q.setParameter(DiscussionPermission.PARAM_CATEGORY_ID, categoryId);

        return q.list();
    }

    @Override
    public List<TopicPermission> findForUser(IDiscussionUser user, Long categoryId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery(TopicPermission.QUERY_BY_USER);

        q.setParameter(TopicPermission.PARAM_USER_ID, user.getDiscussionUserId());
        q.setParameter(TopicPermission.PARAM_CATEGORY_ID, categoryId);

        return q.list();
    }

    @Override
    public List<CategoryPermission> findForUser(IDiscussionUser user) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery(CategoryPermission.QUERY_BY_USER);

        q.setParameter(TopicPermission.PARAM_USER_ID, user.getDiscussionUserId());

        return q.list();
    }

    @Override
    public <Z extends AbstractPermission> List<Z> findByType(IDiscussionUser user, Class<Z> type, PermissionLevel level, Long itemId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria cq = session.createCriteria(type);
        cq.add(Restrictions.eq("userId", user.getDiscussionUserId()));
        cq.add(Restrictions.eq("level", level));
        cq.add(Restrictions.eq("itemId", itemId));

        return cq.list();
    }
}
