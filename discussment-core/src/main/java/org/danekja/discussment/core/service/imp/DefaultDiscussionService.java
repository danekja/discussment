package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.dao.DiscussionDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.Permission;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PermissionService;

import java.util.List;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class DefaultDiscussionService implements DiscussionService {

    private DiscussionDao discussionDao;

    private PermissionService permissionService;

    public DefaultDiscussionService(DiscussionDao discussionDao, PermissionService permissionService) {
        this.discussionDao = discussionDao;
        this.permissionService = permissionService;
    }

    public Discussion createDiscussion(Discussion discussion) {

        return discussionDao.save(discussion);
    }

    public Discussion createDiscussion(Discussion discussion, Topic topic) {

        topic.getDiscussions().add(discussion);
        discussion.setTopic(topic);

        return discussionDao.save(discussion);
    }

    public List<Discussion> getDiscussionsByTopic(Topic topic) {

        return discussionDao.getDiscussionsByTopic(topic);
    }

    public Discussion getDiscussionById(long discussionId) {

        return discussionDao.getById(discussionId);
    }

    public void removeDiscussion(Discussion discussion) {

        if (discussion.getTopic() != null) {
            discussion.getTopic().getDiscussions().remove(discussion);
        }

        discussionDao.remove(discussion);
    }

    public void addAccessToDiscussion(IDiscussionUser entity, Discussion en) {

        en.getUserAccessList().add(entity);

        discussionDao.save(en);
    }

    public boolean isAccessToDiscussion(IDiscussionUser user, Discussion discussion) {
        Permission p = permissionService.getUsersPermissions(user);

        if(p == null) {
            return  false;
        }

        if (discussion.getPass() == null || p.isReadPrivateDiscussion() || discussion.getUserAccessList().contains(this)) {
            return true;
        }
        return false;
    }
}

