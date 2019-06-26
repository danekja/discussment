package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.domain.Action;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.PermissionType;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.DiscussionDao;
import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.TopicService;
import org.springframework.transaction.annotation.Transactional;

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
    private TopicService topicService;
    private AccessControlService accessControlService;
    private DiscussionUserService discussionUserService;

    public DefaultDiscussionService(DiscussionDao discussionDao, PostDao postDao, TopicService topicService, AccessControlService accessControlService, DiscussionUserService discussionUserService) {
        this.discussionDao = discussionDao;
        this.postDao = postDao;
        this.topicService = topicService;
        this.accessControlService = accessControlService;
        this.discussionUserService = discussionUserService;
    }

    public Discussion createDiscussion(Topic topic, Discussion discussion) throws AccessDeniedException {
        if(accessControlService.canAddDiscussion(topic)) {
            discussion.setTopic(topic);
            return discussionDao.save(discussion);
        } else {
            throw new AccessDeniedException(Action.CREATE, getCurrentUserId(), topic.getId(), PermissionType.DISCUSSION);
        }
    }

    public List<Discussion> getDiscussionsByTopic(Topic topic) throws AccessDeniedException {
        if(accessControlService.canViewDiscussions(topic)) {
            return discussionDao.getDiscussionsByTopic(topic);
        } else {
            throw new AccessDeniedException(Action.VIEW, getCurrentUserId(), topic.getId(), PermissionType.DISCUSSION);
        }
    }

    public Discussion getDiscussionById(long discussionId) throws AccessDeniedException {
        Discussion d = discussionDao.getById(discussionId);
        if(d == null || accessControlService.canViewDiscussions(d.getTopic())) {
            return d;
        } else {
            throw new AccessDeniedException(Action.VIEW, getCurrentUserId(), d.getTopic().getId(), PermissionType.DISCUSSION);
        }
    }

    public Discussion getDefaultDiscussion() {
        Discussion d = discussionDao.getById(Discussion.DEFAULT_DISCUSSION_ID);
        if(d == null){
            d =  new Discussion(Discussion.DEFAULT_DISCUSSION_ID, "default discussion");
            d.setTopic(topicService.getDefaultTopic());
            discussionDao.save(d);
        }
        return d;
    }

    public void removeDiscussion(Discussion discussion) throws AccessDeniedException {
        if(accessControlService.canRemoveDiscussion(discussion)) {
            discussionDao.remove(discussion);
        } else {
            throw new AccessDeniedException(Action.DELETE, getCurrentUserId(), discussion.getId(), PermissionType.DISCUSSION);
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
