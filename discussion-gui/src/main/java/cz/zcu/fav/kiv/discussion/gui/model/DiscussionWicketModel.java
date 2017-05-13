package cz.zcu.fav.kiv.discussion.gui.model;

import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.TopicEntity;
import cz.zcu.fav.kiv.discussion.core.service.DiscussionService;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class DiscussionWicketModel implements IModel<List<DiscussionEntity>> {

    private TopicEntity topicEntity;

    public DiscussionWicketModel(TopicEntity topicEntity) {

        this.topicEntity = topicEntity;

    }

    public TopicEntity getTopic() {
        return topicEntity;
    }

    public void setTopic(TopicEntity topicEntity) {
        this.topicEntity = topicEntity;
    }

    public void detach() {
    }

    public List<DiscussionEntity> getObject() {
        return DiscussionService.getDiscussionsByTopic(topicEntity);
    }

    public void setObject(List<DiscussionEntity> object) {
    }
}
