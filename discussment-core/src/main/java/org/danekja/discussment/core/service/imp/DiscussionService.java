package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.dao.IDiscussionDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.IDiscussionService;

import java.util.List;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class DiscussionService implements IDiscussionService {

    private IDiscussionDao discussionDao;

    public DiscussionService(IDiscussionDao discussionDao) {
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

}

