package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.accesscontrol.service.PermissionService;
import org.danekja.discussment.core.dao.DiscussionDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
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

    public String getLastPostAuthor(Discussion discussion) throws DiscussionUserNotFoundException {
        Post lasPost = discussion.getLastPost();
        if(lasPost == null) {
            return "";
        }

        return userService.getUserById(lasPost.getUserId()).getDisplayName();
    }
}

