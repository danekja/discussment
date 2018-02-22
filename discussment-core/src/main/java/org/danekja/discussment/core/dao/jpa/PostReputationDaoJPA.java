package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.dao.PostReputationDao;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.PostReputation;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
/**
 * JPA implementation of the PostReputationDao interface.
 *
 * Date: 17.2.18
 *
 * @author Jiri Kryda
 */
public class PostReputationDaoJPA extends GenericDaoJPA<Long, PostReputation> implements PostReputationDao {

    /**
     * @param em entity manager to be used by this dao
     */
    public PostReputationDaoJPA(EntityManager em){ super(PostReputation.class, em);}

    public PostReputation getByPost(Post post) {
        TypedQuery<PostReputation> q = em.createNamedQuery(PostReputation.GET_BY_POST, PostReputation.class);
        q.setParameter("postId", post.getId());
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
