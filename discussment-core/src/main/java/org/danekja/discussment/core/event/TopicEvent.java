package org.danekja.discussment.core.event;

import org.danekja.discussment.core.domain.Topic;

/**
 * Abstract event with {@link Topic} object as payload.
 */
public abstract class TopicEvent {
    private final Topic topic;

    public TopicEvent(Topic topic) {
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }
}
