package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 */
public interface DiscussionDao extends GenericDao<Discussion> {
    List<Discussion> getDiscussionsByTopic(Topic ITopic);
}
