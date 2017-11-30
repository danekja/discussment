package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostService;

import java.util.List;

/**
 * Post service which uses new permission system. Should replace DefaultPostService.
 * Methods without user parameter will use currently logged user.
 *
 * Created by Zdenek Vales on 27.11.2017.
 */
public class NewPostService implements PostService {

    private PostDao postDao;

    private AccessControlService accessControlService;

    private DiscussionUserService discussionUserService;

    public NewPostService(DiscussionUserService discussionUserService, AccessControlService accessControlService, PostDao postDao) {
        this.postDao = postDao;
        this.accessControlService = accessControlService;
        this.discussionUserService = discussionUserService;
    }

    public void removePost(Post post) {
        if( accessControlService.canRemovePost(post)) {
            if (post.getPost() != null) {
                post.getPost().getReplies().remove(post);
            } else {
                post.getDiscussion().getPosts().remove(post);
            }
            postDao.remove(post);
        } else {
            // todo: throw exception
        }
    }

    public Post getPostById(long postId) {
        Post post = postDao.getById(postId);

        if(accessControlService.canViewPost(post)) {
            return post;
        } else {
            // todo: throw exception
        }

        return null;
    }

    public Post sendReply(Post reply, Post post) {
        return null;
    }

    public Post sendReplyAsCurrentUser(Post reply, Post post) {
        return null;
    }

    public Post sendPost(Discussion discussion, Post post) {
        if(accessControlService.canAddPost(discussion)) {
            post.setDiscussion(discussion);

            return postDao.save(post);
        } else {
            // todo: throw exception
        }
        return null;
    }

    public Post sendPostAsCurrentUser(Discussion discussion, Post post) {
        return null;
    }

    public Post disablePost(Post post) {
        if (accessControlService.canEditPost(post)) {
            post.setDisabled(true);
            return postDao.save(post);
        } else {
            // todo: throw exception
        }
        return null;
    }

    public Post enablePost(Post post) {
        if (accessControlService.canEditPost(post)) {
            post.setDisabled(false);
            return postDao.save(post);
        } else {
            // todo: throw exception
        }
        return null;
    }

    public List<Post> listPostHierarchy(Discussion discussion) {
        if (accessControlService.canViewPosts(discussion)) {
            return postDao.getPostsByDiscussion(discussion);
        } else {
            // todo: throw exception
        }
        return null;
    }

    public String getPostAuthor(Post post) throws DiscussionUserNotFoundException {
        // todo: check if post can be accessed
        if(accessControlService.canViewPost(post)) {
            return discussionUserService.getUserById(post.getUserId()).getDisplayName();
        } else {
            // todo: throw exception
            return null;
        }
    }
}
