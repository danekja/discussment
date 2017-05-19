package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class DiscussionWicketModel extends LoadableDetachableModel {

    private DiscussionService discussionService;

    private Topic topic;

    public DiscussionWicketModel(Topic topic, DiscussionService discussionService) {

        this.topic = topic;
        this.discussionService = discussionService;
    }

    protected Object load() {
        return discussionService.getDiscussionsByTopic(topic);
    }


}
