package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.dao.IPostDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.IPostService;

/**
 * Created by Martin Bláha on 07.02.17.
 */
public class PostService implements IPostService {

    private IPostDao postDao;

    public PostService(IPostDao postDao) {
        this.postDao = postDao;
    }

    public void removePost(Post post) {

        if (post.getPost() != null) {
            post.getPost().getReplies().remove(post);
        } else {
            post.getDiscussion().getPosts().remove(post);
        }
        postDao.remove(post);
    }

    public Post getPostById(long postId) {
        return postDao.getById(postId);
    }

    public Post sendReply(Post reply, Post IPost) {

        int level = IPost.getLevel();
        reply.setLevel(++level);

        IPost.addReply(reply);

        return postDao.save(reply);

    }

    public Post sendPost(Discussion IDiscussion, Post post) {

        IDiscussion.addPost(post);

        return postDao.save(post);
    }

    public Post disablePost(Post post) {

        post.setDisabled(true);

        return postDao.save(post);

    }

    public Post enablePost(Post post) {

        post.setDisabled(false);

        return postDao.save(post);

    }

}
