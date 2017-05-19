package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class DiscussionWicketModel extends LoadableDetachableModel {

    private DiscussionService discussionService;

    private IModel<Topic> topicModel;

    public DiscussionWicketModel(IModel<Topic> topicModel, DiscussionService discussionService) {

        this.topicModel = topicModel;
        this.discussionService = discussionService;
    }

    protected Object load() {
        return discussionService.getDiscussionsByTopic(topicModel.getObject());
    }


}
