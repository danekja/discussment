package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.domain.Action;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.PermissionType;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.DiscussionDao;
import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Discussion service which uses the new permission system. Will replace DefaultDiscussionService.
 * Methods without user parameter will use currently logged user.
 *
 */
@Transactional
public class DefaultDiscussionService implements DiscussionService {

    private DiscussionDao discussionDao;
    private PostDao postDao;
    private AccessControlService accessControlService;
    private DiscussionUserService discussionUserService;

    public DefaultDiscussionService(DiscussionDao discussionDao, PostDao postDao, AccessControlService accessControlService, DiscussionUserService discussionUserService) {
        this.discussionDao = discussionDao;
        this.postDao = postDao;
        this.accessControlService = accessControlService;
        this.discussionUserService = discussionUserService;
    }

    public Discussion createDiscussion(Topic topic, Discussion discussion) throws AccessDeniedException {
        if(accessControlService.canAddDiscussion(topic)) {
            discussion.setTopic(topic);
            return discussionDao.save(discussion);
        } else {
            throw new AccessDeniedException(Action.CREATE, discussionUserService.getCurrentlyLoggedUser().getDiscussionUserId(), topic.getId(), PermissionType.DISCUSSION);
        }
    }

    public List<Discussion> getDiscussionsByTopic(Topic topic) throws AccessDeniedException {
        if(accessControlService.canViewDiscussions(topic)) {
            return discussionDao.getDiscussionsByTopic(topic);
        } else {
            throw new AccessDeniedException(Action.VIEW, discussionUserService.getCurrentlyLoggedUser().getDiscussionUserId(), topic.getId(), PermissionType.DISCUSSION);
        }
    }

    public Discussion getDiscussionById(long discussionId) throws AccessDeniedException {
        Discussion d = discussionDao.getById(discussionId);
        if(d == null || accessControlService.canViewDiscussions(d.getTopic())) {
            return d;
        } else {
            throw new AccessDeniedException(Action.VIEW, discussionUserService.getCurrentlyLoggedUser().getDiscussionUserId(), d.getTopic().getId(), PermissionType.DISCUSSION);
        }
    }

    public void removeDiscussion(Discussion discussion) throws AccessDeniedException {
        if(accessControlService.canRemoveDiscussion(discussion)) {
            discussionDao.remove(discussion);
        } else {
            throw new AccessDeniedException(Action.DELETE, discussionUserService.getCurrentlyLoggedUser().getDiscussionUserId(), discussion.getId(), PermissionType.DISCUSSION);
        }
    }

    public IDiscussionUser getLastPostAuthor(Discussion discussion) throws DiscussionUserNotFoundException, AccessDeniedException {
        if(accessControlService.canViewPosts(discussion)) {
            Post post = postDao.getLastPost(discussion);
            if(post == null) {
                return null;
            } else {
                return discussionUserService.getUserById(post.getUserId());
            }
        } else {
            throw new AccessDeniedException(Action.VIEW, discussionUserService.getCurrentlyLoggedUser().getDiscussionUserId(), discussion.getId(), PermissionType.POST);
        }
    }
}
