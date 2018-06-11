package org.danekja.discussment.core.dao.hibernate;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.dao.UserPostReputationDao;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.UserPostReputation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Hibernate implementation of the UserPostReputationDao interface.
 *
 * @author Jakub Danek
 */
public class UserPostReputationDaoHibernate extends GenericDaoHibernate<Long, UserPostReputation> implements UserPostReputationDao {

    /**
     * @param sessionFactory Hibernate session factory to be used by this dao
     */
    public UserPostReputationDaoHibernate(SessionFactory sessionFactory) {
        super(UserPostReputation.class, sessionFactory);
    }

    public UserPostReputation getForUser(IDiscussionUser user, Post post) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<UserPostReputation> q = session.createNamedQuery(UserPostReputation.GET_FOR_USER, UserPostReputation.class);
        q.setParameter("userId", user.getDiscussionUserId());
        q.setParameter("postId", post.getId());
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

