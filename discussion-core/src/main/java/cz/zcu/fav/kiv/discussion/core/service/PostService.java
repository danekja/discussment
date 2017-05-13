package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.dao.IPostDao;
import cz.zcu.fav.kiv.discussion.core.dao.jpa.PostJPA;
import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.PostEntity;

/**
 * Created by Martin Bláha on 07.02.17.
 */
public class PostService {

    private static IPostDao postDao = new PostJPA();

    public static void removePost(PostEntity post) {

        if (post.getPost() != null) {
            post.getPost().getReplies().remove(post);
        } else {
            post.getDiscussion().getPosts().remove(post);
        }
        postDao.remove(post);
    }

    public static PostEntity getPostById(long postId) {
        return postDao.getById(postId);
    }

    public static PostEntity sendReply(PostEntity reply, PostEntity post) {

        int level = post.getLevel();
        reply.setLevel(++level);

        post.addReply(reply);

        return postDao.save(reply);

    }

    public static PostEntity sendPost(DiscussionEntity discussionEntity, PostEntity post) {

        discussionEntity.addPost(post);

        return postDao.save(post);
    }

    public static PostEntity disablePost(PostEntity post) {

        post.setDisabled(true);

        return postDao.save(post);

    }

    public static PostEntity enablePost(PostEntity post) {

        post.setDisabled(false);

        return postDao.save(post);

    }

}
