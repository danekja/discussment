package org.danekja.discussment.ui.wicket.model;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class DiscussionWicketModel implements IModel<List<Discussion>> {

    private Topic topic;

    public DiscussionWicketModel(Topic topic) {

        this.topic = topic;

    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void detach() {
    }

    public List<Discussion> getObject() {
        return DiscussionService.getDiscussionsByTopic(topic);
    }

    public void setObject(List<Discussion> object) {
    }
}
