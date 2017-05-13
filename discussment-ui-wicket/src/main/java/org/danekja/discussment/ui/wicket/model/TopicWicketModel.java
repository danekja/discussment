package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.ITopicService;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class TopicWicketModel implements IModel<List<Topic>> {

    private ITopicService topicService;

    private List<Topic> topics;

    private Category category;

    public TopicWicketModel(ITopicService topicService) {

        this(null, topicService);
    }

    public TopicWicketModel(Category category, ITopicService topicService) {

        this.category = category;
        this.topicService = topicService;
    }

    public void detach() {
    }

    public List<Topic> getObject() {

        if (category == null) {
            return topicService.getTopicsWithoutCategory();
        } else {
            return topicService.getTopicsByCategory(category);
        }

    }

    public void setObject(List<Topic> object) {
    }
}
