package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.Topic;

public class TopicRemovedEvent extends TopicEvent {
    public TopicRemovedEvent(Topic topic) {
        super(topic);
    }
}
