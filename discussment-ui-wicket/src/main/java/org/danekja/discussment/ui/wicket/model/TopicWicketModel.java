package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.service.TopicService;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class TopicWicketModel extends LoadableDetachableModel {

    private TopicService topicService;
    private IModel<Category> categoryModel;

    public TopicWicketModel(TopicService topicService) {

        this(new Model<Category>(), topicService);
    }

    public TopicWicketModel(IModel<Category> categoryModel, TopicService topicService) {

        this.categoryModel = categoryModel;
        this.topicService = topicService;
    }

    protected Object load() {

        if (categoryModel.getObject() == null) {
            return topicService.getTopicsWithoutCategory();
        } else {
            return topicService.getTopicsByCategory(categoryModel.getObject());
        }
    }

}
