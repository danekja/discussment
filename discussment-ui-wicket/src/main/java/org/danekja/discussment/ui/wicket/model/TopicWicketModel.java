package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.service.ITopicService;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class TopicWicketModel extends LoadableDetachableModel {

    private ITopicService topicService;
    private Category category;

    public TopicWicketModel(ITopicService topicService) {

        this(null, topicService);
    }

    public TopicWicketModel(Category category, ITopicService topicService) {

        this.category = category;
        this.topicService = topicService;
    }

    protected Object load() {

        if (category == null) {
            return topicService.getTopicsWithoutCategory();
        } else {
            return topicService.getTopicsByCategory(category);
        }
    }

}
