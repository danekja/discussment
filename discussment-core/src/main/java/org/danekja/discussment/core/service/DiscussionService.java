package org.danekja.discussment.core.service;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 */
public interface DiscussionService {
    Discussion createDiscussion(Discussion discussion);

    Discussion createDiscussion(Discussion discussion, Topic topic);

    List<Discussion> getDiscussionsByTopic(Topic topic);

    Discussion getDiscussionById(long discussionId);

    void removeDiscussion(Discussion discussion);
}
