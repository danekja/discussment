package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.domain.Action;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.PermissionType;
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

    public void removePost(Post post) throws AccessDeniedException {
        if( accessControlService.canRemovePost(post)) {
            if (post.getPost() != null) {
                post.getPost().getReplies().remove(post);
            } else {
                post.getDiscussion().getPosts().remove(post);
            }
            postDao.remove(post);
        } else {
            throw new AccessDeniedException(Action.DELETE, getCurrentUserId(),post.getId(), PermissionType.POST);
        }
    }

    public Post getPostById(long postId) throws AccessDeniedException {
        Post post = postDao.getById(postId);

        if(accessControlService.canViewPost(post)) {
            return post;
        } else {
            throw new AccessDeniedException(Action.VIEW, getCurrentUserId(),postId, PermissionType.POST);
        }
    }

    public Post sendReply(Post reply, Post post) throws AccessDeniedException {
        reply.setPost(post);
        return sendPost(post.getDiscussion(), reply);
    }

    public Post sendPost(Discussion discussion, Post post) throws AccessDeniedException {
        if(accessControlService.canAddPost(discussion)) {
            post.setDiscussion(discussion);

            return postDao.save(post);
        } else {
            throw new AccessDeniedException(Action.CREATE, getCurrentUserId(),discussion.getId(), PermissionType.POST);
        }
    }

    public Post disablePost(Post post) throws AccessDeniedException {
        if (accessControlService.canEditPost(post)) {
            post.setDisabled(true);
            return postDao.save(post);
        } else {
            throw new AccessDeniedException(Action.EDIT, getCurrentUserId(),post.getId(), PermissionType.POST);
        }
    }

    public Post enablePost(Post post) throws AccessDeniedException {
        if (accessControlService.canEditPost(post)) {
            post.setDisabled(false);
            return postDao.save(post);
        } else {
            throw new AccessDeniedException(Action.EDIT, getCurrentUserId(),post.getId(), PermissionType.POST);
        }
    }

    public List<Post> listPostHierarchy(Discussion discussion) throws AccessDeniedException {
        if (accessControlService.canViewPosts(discussion)) {
            return postDao.getPostsByDiscussion(discussion);
        } else {
            throw new AccessDeniedException(Action.VIEW, getCurrentUserId(),discussion.getId(), PermissionType.POST);
        }
    }

    public String getPostAuthor(Post post) throws DiscussionUserNotFoundException, AccessDeniedException {
        if(accessControlService.canViewPost(post)) {
            return discussionUserService.getUserById(post.getUserId()).getDisplayName();
        } else {
            throw new AccessDeniedException(Action.VIEW, getCurrentUserId(),post.getId(), PermissionType.POST);
        }
    }

    /**
     * Returns the id of the currently logged user. Used when throwing access denied exception.
     * @return Id of the currently logged user or null if no user is logged.
     */
    private String getCurrentUserId() {
        IDiscussionUser user = discussionUserService.getCurrentlyLoggedUser();
        return user == null ? null : user.getDiscussionUserId();
    }
}
