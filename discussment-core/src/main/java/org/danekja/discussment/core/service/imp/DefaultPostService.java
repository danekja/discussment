package org.danekja.discussment.core.service.imp;


import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostService;

import java.util.List;

/**
 * Created by Martin Bláha on 07.02.17.
 */
public class DefaultPostService implements PostService {

    private PostDao postDao;
    private DiscussionUserService userService;

    public DefaultPostService(PostDao postDao, DiscussionUserService userService) {
        this.postDao = postDao;
        this.userService = userService;
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

    public Post sendPost(Discussion discussion, Post post) {

        post.setDiscussion(discussion);

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

    public List<Post> listPostHierarchy(Discussion discussion) {

        return postDao.getPostsByDiscussion(discussion);
    }

    public Post sendPostAsCurrentUser(Discussion discussion, Post post) {
        IDiscussionUser user = userService.getCurrentlyLoggedUser();
        if(user == null) {
            return null;
        }

        post.setUserId(user.getDiscussionUserId());
        return sendPost(discussion, post);
    }

    public Post sendReplyAsCurrentUser(Post reply, Post post) {
        IDiscussionUser user = userService.getCurrentlyLoggedUser();
        if(user == null) {
            return null;
        }

        reply.setUserId(user.getDiscussionUserId());
        return sendReply(reply, post);
    }

    public String getPostAuthor(Post post) throws DiscussionUserNotFoundException {
        return userService.getUserById(post.getUserId()).getDisplayName();
    }
}
