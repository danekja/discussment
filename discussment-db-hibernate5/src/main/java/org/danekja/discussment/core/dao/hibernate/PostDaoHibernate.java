package org.danekja.discussment.core.dao.hibernate;

import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jakub Danek on 19.01.17.
 */
public class PostDaoHibernate extends GenericDaoHibernate<Long, Post> implements PostDao {


    public PostDaoHibernate(SessionFactory sessionFactory) {
        super(Post.class, sessionFactory);
    }

    public List<Post> getPostsByDiscussion(Discussion discussion) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Post> q = session.createNamedQuery(Post.GET_BY_DISCUSSION, Post.class);
        q.setParameter("discussionId", discussion.getId());
        return q.getResultList();
    }

    public List<Post> getBasePostsByDiscussion(Discussion discussion) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Post> q = session.createNamedQuery(Post.GET_BASE_POSTS_BY_DISCUSSION, Post.class);
        q.setParameter("discussionId", discussion.getId());
        return q.getResultList();
    }

    public List<Post> getRepliesForPost(Post post) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Post> q = session.createNamedQuery(Post.GET_REPLIES_FOR_POST, Post.class);
        q.setParameter("postId", post.getId());
        return q.getResultList();
    }

    public Post getLastPost(Discussion discussion) {
        List<Post> posts = getPostsByDiscussion(discussion);
        if (posts.size() != 0) {
            posts.sort(Comparator.comparing(o -> o.getCreated(), Comparator.reverseOrder()));
            return posts.get(0);
        }
        return null;
    }

    public long getNumberOfPosts(Discussion discussion) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createNamedQuery(Post.COUNT_BY_DISCUSSION);
        q.setParameter("discussionId", discussion.getId());
        return (Long) q.getSingleResult();
    }

    public Map<Long, Long> getNumbersOfPosts(List<Long> discussionIds) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createNamedQuery(Post.COUNT_BY_DISCUSSIONS);
        q.setParameter("discussionIds", discussionIds);
        List<Object[]> numbersOfPosts = q.getResultList();

        Map<Long, Long> resultMap = new HashMap<>();
        for (Object[] numbers : numbersOfPosts) {
            resultMap.put(((Number) numbers[0]).longValue(), ((Number) numbers[1]).longValue());
        }

        return resultMap;
    }
}
