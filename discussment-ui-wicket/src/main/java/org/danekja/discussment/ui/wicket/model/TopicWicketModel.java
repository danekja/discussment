package org.danekja.discussment.ui.wicket.model;

import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.TopicService;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class TopicWicketModel implements IModel<List<Topic>> {


    private List<Topic> topics;

    private Category category;

    public TopicWicketModel() {

        this.category = null;

    }

    public TopicWicketModel(Category category) {

        this.category = category;

    }

    public void detach() {
    }

    public List<Topic> getObject() {

        if (category == null) {
            return TopicService.getTopicsWithoutCategory();
        } else {
            return TopicService.getTopicsByCategory(category);
        }

    }

    public void setObject(List<Topic> object) {
    }
}
