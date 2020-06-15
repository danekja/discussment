package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.Topic;

public class TopicCreatedEvent extends TopicEvent {
    public TopicCreatedEvent(Topic topic) {
        super(topic);
    }
}
