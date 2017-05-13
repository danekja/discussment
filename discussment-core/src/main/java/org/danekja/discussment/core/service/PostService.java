package org.danekja.discussment.core.service;

import org.danekja.discussment.core.dao.IPostDao;
import org.danekja.discussment.core.dao.jpa.PostJPA;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;

/**
 * Created by Martin Bláha on 07.02.17.
 */
public class PostService {

    private static IPostDao postDao = new PostJPA();

    public static void removePost(Post post) {

        if (post.getPost() != null) {
            post.getPost().getReplies().remove(post);
        } else {
            post.getDiscussion().getPosts().remove(post);
        }
        postDao.remove(post);
    }

    public static Post getPostById(long postId) {
        return postDao.getById(postId);
    }

    public static Post sendReply(Post reply, Post post) {

        int level = post.getLevel();
        reply.setLevel(++level);

        post.addReply(reply);

        return postDao.save(reply);

    }

    public static Post sendPost(Discussion discussion, Post post) {

        discussion.addPost(post);

        return postDao.save(post);
    }

    public static Post disablePost(Post post) {

        post.setDisabled(true);

        return postDao.save(post);

    }

    public static Post enablePost(Post post) {

        post.setDisabled(false);

        return postDao.save(post);

    }

}
