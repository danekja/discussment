package cz.zcu.fav.kiv.discussion.gui.model;

import cz.zcu.fav.kiv.discussion.core.model.DiscussionModel;
import cz.zcu.fav.kiv.discussion.core.service.DiscussionService;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class DiscussionWicketModel implements IModel<List<DiscussionModel>> {

    private long topicId;

    public DiscussionWicketModel(long topicId) {

        this.topicId = topicId;

    }

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    public void detach() {
    }

    public List<DiscussionModel> getObject() {
        return DiscussionService.getDiscussionsByTopicId(topicId);
    }

    public void setObject(List<DiscussionModel> object) {
    }
}
