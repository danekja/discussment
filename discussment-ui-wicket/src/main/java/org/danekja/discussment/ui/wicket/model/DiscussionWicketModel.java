package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.IDiscussionService;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class DiscussionWicketModel implements IModel<List<Discussion>> {

    private IDiscussionService discussionService;

    private Topic topic;

    public DiscussionWicketModel(Topic topic, IDiscussionService discussionService) {

        this.topic = topic;
        this.discussionService = discussionService;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic ITopic) {
        this.topic = topic;
    }

    public void detach() {
    }

    public List<Discussion> getObject() {
        return discussionService.getDiscussionsByTopic(topic);
    }

    public void setObject(List<Discussion> object) {
    }
}
