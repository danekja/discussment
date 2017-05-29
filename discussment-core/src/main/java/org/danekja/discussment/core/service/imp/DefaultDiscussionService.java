package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.dao.DiscussionDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.domain.User;

import java.util.List;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class DefaultDiscussionService implements org.danekja.discussment.core.service.DiscussionService {

    private DiscussionDao discussionDao;

    public DefaultDiscussionService(DiscussionDao discussionDao) {
        this.discussionDao = discussionDao;
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

    public void addAccessToDiscussion(User entity, Discussion en) {

        en.getUserAccessList().add(entity);

        discussionDao.save(en);
    }

}

