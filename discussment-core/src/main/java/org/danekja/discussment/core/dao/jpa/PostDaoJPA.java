package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Martin Bláha on 19.01.17.
 */
public class PostDaoJPA extends GenericDaoJPA<Long, Post> implements PostDao {

    public PostDaoJPA() {
        super(Post.class);
    }

    public PostDaoJPA(EntityManager em) {
        super(Post.class, em);
    }

    @Override
    public Post save(Post obj) {
        if(obj.isNew()) {
            obj = super.save(obj);
            obj.appendToChainId(obj.getId().toString());
        }
        return super.save(obj);
    }

    public List<Post> getPostsByDiscussion(Discussion discussion) {
        TypedQuery<Post> q = em.createNamedQuery(Post.GET_BY_DISCUSSION, Post.class);
        q.setParameter("discussionId", discussion.getId());
        return q.getResultList();
    }

    public List<Post> getBasePostsByDiscussion(Discussion discussion) {
        TypedQuery<Post> q = em.createNamedQuery(Post.GET_BASE_POSTS_BY_DISCUSSION, Post.class);
        q.setParameter("discussionId", discussion.getId());
        return q.getResultList();
    }

    public List<Post> getRepliesForPost(Post post){
        TypedQuery<Post> q = em.createNamedQuery(Post.GET_REPLIES_FOR_POST, Post.class);
        q.setParameter("postId", post.getId());
        return q.getResultList();
    }

    public Post getLastPost(Discussion discussion){
        List<Post> posts = getPostsByDiscussion(discussion);
        if(posts.size() != 0) {
            posts.sort(Comparator.comparing(o -> o.getCreated(), Comparator.reverseOrder()));
            return posts.get(0);
        }
        return null;
    }

    public long getNumberOfPosts(Discussion discussion){
        return getPostsByDiscussion(discussion).size();
    }
}
