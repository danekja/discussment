package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.DiscussionDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;

import java.util.List;

/**
 * Discussion service which uses the new permission system. Will replace DefaultDiscussionService.
 * Methods without user parameter will use currently logged user.
 *
 */
public class NewDiscussionService implements DiscussionService {

    private DiscussionDao discussionDao;
    private AccessControlService accessControlService;
    private DiscussionUserService discussionUserService;

    public NewDiscussionService(DiscussionDao discussionDao, AccessControlService accessControlService, DiscussionUserService discussionUserService) {
        this.discussionDao = discussionDao;
        this.accessControlService = accessControlService;
        this.discussionUserService = discussionUserService;
    }

    public Discussion createDiscussion(Discussion discussion) {
        return null;
    }

    public Discussion createDiscussion(Discussion discussion, Topic topic) {
        return null;
    }

    public List<Discussion> getDiscussionsByTopic(Topic topic) {
        return null;
    }

    public Discussion getDiscussionById(long discussionId) {
        return null;
    }

    public void removeDiscussion(Discussion discussion) {

    }

    public void addAccessToDiscussion(IDiscussionUser entity, Discussion en) {

    }

    public void addCurrentUserToDiscussion(Discussion en) {

    }

    public boolean isAccessToDiscussion(IDiscussionUser user, Discussion discussion) {
        return false;
    }

    public boolean hasCurrentUserAccessToDiscussion(Discussion discussion) {
        return false;
    }

    public String getLastPostAuthor(Discussion discussion) throws DiscussionUserNotFoundException {
        return null;
    }
}
