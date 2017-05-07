package cz.zcu.fav.kiv.discussion.gui.model;

import cz.zcu.fav.kiv.discussion.core.model.TopicModel;
import cz.zcu.fav.kiv.discussion.core.service.TopicService;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class TopicWicketModel implements IModel<List<TopicModel>> {


    private List<TopicModel> topics;

    private long categoryId;

    public TopicWicketModel() {

        this.categoryId = -1;

    }

    public TopicWicketModel(long categoryId) {

        this.categoryId = categoryId;

    }

    public void detach() {
    }

    public List<TopicModel> getObject() {

        if (categoryId == -1) {
            return TopicService.getTopicsWithoutCategory();
        } else {
            return TopicService.getTopicsByCategoryId(categoryId);
        }

    }

    public void setObject(List<TopicModel> object) {
    }
}
