package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.dao.UserPostReputationDao;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.PostReputation;
import org.danekja.discussment.core.domain.UserPostReputation;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
/**
 * JPA implementation of the UserPostReputationDao interface.
 *
 * Date: 18.2.18
 *
 * @author Jiri Kryda
 */
public class UserPostReputationDaoJPA extends GenericDaoJPA<Long, UserPostReputation> implements UserPostReputationDao {

    /**
     * Constructor used with container managed entity manager
     */
    public UserPostReputationDaoJPA(){ super(UserPostReputation.class); }

    /**
     * @param em entity manager to be used by this dao
     */
    public UserPostReputationDaoJPA(EntityManager em){ super(UserPostReputation.class, em); }

    public UserPostReputation getForUser(IDiscussionUser user, Post post){
        TypedQuery<UserPostReputation> q = em.createNamedQuery(UserPostReputation.GET_FOR_USER, UserPostReputation.class);
        q.setParameter("userId", user.getDiscussionUserId());
        q.setParameter("postId", post.getId());
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

