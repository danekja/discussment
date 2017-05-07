package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.dao.DiscussionDao;
import cz.zcu.fav.kiv.discussion.core.dao.PostDao;
import cz.zcu.fav.kiv.discussion.core.dao.UserDao;
import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.PostEntity;
import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;
import cz.zcu.fav.kiv.discussion.core.model.PostModel;
import cz.zcu.fav.kiv.discussion.core.utils.MapUtil;

/**
 * Created by Martin Bláha on 07.02.17.
 */
public class PostService {

    private static PostDao postDao = new PostDao();
    private static DiscussionDao discussionDao = new DiscussionDao();
    private static UserDao userDao = new UserDao();

    public static void removePostById(long postId) {
        PostEntity post = postDao.getById(postId);
        if (post.getPost() != null) {
            post.getPost().getReplies().remove(post);
        } else {
            post.getDiscussion().getPosts().remove(post);
        }
        postDao.remove(post);
    }

    public static PostModel getPostById(long postId) {
        return MapUtil.mapPostEntityToModel(postDao.getById(postId));
    }

    public static PostModel sendReply(long userId, String text, long postId) {

        PostEntity post = postDao.getById(postId);

        UserEntity user = userDao.getById(userId);
        PostEntity reply = new PostEntity(user, text);
        int level = post.getLevel();
        reply.setLevel(++level);

        post.addReply(reply);

        return MapUtil.mapPostEntityToModel(postDao.save(reply));

    }

    public static PostModel sendPost(long discussionId, long userId, String text) {

        UserEntity user = userDao.getById(userId);
        PostEntity post = new PostEntity(user, text);

        DiscussionEntity discussionEntity = discussionDao.getById(discussionId);

        discussionEntity.addPost(post);

        return MapUtil.mapPostEntityToModel(postDao.save(post));
    }

    public static PostModel disablePost(long postId) {

        PostEntity post = postDao.getById(postId);
        post.setDisabled(true);

        return MapUtil.mapPostEntityToModel(postDao.save(post));

    }

    public static PostModel enablePost(long postId) {

        PostEntity post = postDao.getById(postId);
        post.setDisabled(false);

        return MapUtil.mapPostEntityToModel(postDao.save(post));

    }

}
