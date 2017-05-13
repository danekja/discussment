package org.danekja.discussment.core.service;

import org.danekja.discussment.core.dao.IDiscussionDao;
import org.danekja.discussment.core.dao.jpa.DiscussionJPA;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;

import java.util.List;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class DiscussionService {

    private static IDiscussionDao discussionJPA = new DiscussionJPA();

    public static Discussion createDiscussion(Discussion discussion) {

        return discussionJPA.save(discussion);
    }

    public static Discussion createDiscussion(Discussion discussion, Topic topic) {

        topic.getDiscussions().add(discussion);
        discussion.setTopic(topic);

        return discussionJPA.save(discussion);
    }

    public static List<Discussion> getDiscussionsByTopic(Topic topic) {

        return discussionJPA.getDiscussionsByTopic(topic);
    }

    public static Discussion getDiscussionById(long discussionId) {

        return discussionJPA.getById(discussionId);
    }

    public static void removeDiscussion(Discussion discussion) {

        if (discussion.getTopic() != null) {
            discussion.getTopic().getDiscussions().remove(discussion);
        }
        discussionJPA.remove(discussion);
    }

}

