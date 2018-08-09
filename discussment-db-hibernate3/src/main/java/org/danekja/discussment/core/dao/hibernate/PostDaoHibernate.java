package org.danekja.discussment.core.dao.hibernate;

import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Jakub Danek on 19.01.17.
 */
public class PostDaoHibernate extends GenericDaoHibernate<Long, Post> implements PostDao {


    public PostDaoHibernate(SessionFactory sessionFactory) {
        super(Post.class, sessionFactory);
    }

    public List<Post> getPostsByDiscussion(Discussion discussion) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery(Post.GET_BY_DISCUSSION);
        q.setParameter("discussionId", discussion.getId());
        return q.list();
    }

    public List<Post> getBasePostsByDiscussion(Discussion discussion) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery(Post.GET_BASE_POSTS_BY_DISCUSSION);
        q.setParameter("discussionId", discussion.getId());
        return q.list();
    }

    public List<Post> getRepliesForPost(Post post) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery(Post.GET_REPLIES_FOR_POST);
        q.setParameter("postId", post.getId());
        return q.list();
    }

    public Post getLastPost(Discussion discussion) {
        List<Post> posts = getPostsByDiscussion(discussion);
        if (posts.size() != 0) {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    return o2.getCreated().compareTo(o1.getCreated());
                }
            });
            return posts.get(0);
        }
        return null;
    }

    public long getNumberOfPosts(Discussion discussion) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery(Post.COUNT_BY_DISCUSSION);
        q.setParameter("discussionId", discussion.getId());
        return (Long) q.uniqueResult();
    }

    public List<Object[]> getNumbersOfPosts(List<Long> discussionIds) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.getNamedQuery(Post.COUNT_BY_DISCUSSIONS);
        q.setParameterList("discussionIds", discussionIds);
        return q.list();
    }
}
