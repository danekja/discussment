package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Martin Bláha on 19.01.17.
 */
public class PostDaoJPA extends GenericDaoJPA<Long, Post> implements PostDao {

    public PostDaoJPA() {
        super(Post.class);
    }

    public List<Post> getPostsByDiscussion(Discussion discussion) {
        TypedQuery<Post> q = em.createNamedQuery(Post.GET_BY_DISCUSSION, Post.class);
        q.setParameter("discussionId", discussion.getId());
        return q.getResultList();
    }
}
