package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.Permission;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.accesscontrol.service.PermissionService;
import org.danekja.discussment.core.dao.DiscussionDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.domain.UserDiscussion;
import org.danekja.discussment.core.service.DiscussionService;

import java.util.List;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class DefaultDiscussionService implements DiscussionService {

    private DiscussionDao discussionDao;

    private PermissionService permissionService;
    private DiscussionUserService userService;

    public DefaultDiscussionService(DiscussionDao discussionDao, PermissionService permissionService, DiscussionUserService userService) {
        this.discussionDao = discussionDao;
        this.permissionService = permissionService;
        this.userService = userService;
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
        UserDiscussion userDiscussion = new UserDiscussion(entity.getDiscussionUserId(), en);
        discussionDao.addAccessToDiscussion(userDiscussion);
    }

    public boolean isAccessToDiscussion(IDiscussionUser user, Discussion discussion) {
        Permission p = permissionService.getUsersPermissions(user);

        if(p == null) {
            return  false;
        }

        UserDiscussion ud = new UserDiscussion(user.getDiscussionUserId(), discussion);
        return discussion.getPass() == null || p.isReadPrivateDiscussion() || discussion.getUserAccessList().contains(ud);
    }

    public void addCurrentUserToDiscussion(Discussion en) {
        IDiscussionUser user = userService.getCurrentlyLoggedUser();
        if(user != null) {
            addAccessToDiscussion(user, en);
        } else {
        }
    }

    public boolean hasCurrentUserAccessToDiscussion(Discussion discussion) {
        IDiscussionUser user = userService.getCurrentlyLoggedUser();
        if(user != null) {
            return isAccessToDiscussion(user, discussion);
        }

        return false;
    }

    public String getLastPostAuthor(Discussion discussion) throws DiscussionUserNotFoundException {
        Post lasPost = discussion.getLastPost();
        if(lasPost == null) {
            return "";
        }

        return userService.getUserById(lasPost.getUserId()).getDisplayName();
    }
}

