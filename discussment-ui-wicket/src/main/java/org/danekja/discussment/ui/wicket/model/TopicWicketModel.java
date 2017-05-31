package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.TopicService;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 *
 * The class for getting topic in the category, if the category is sets, via topic service.
 */
public class TopicWicketModel extends LoadableDetachableModel<List<Topic>> {

    private TopicService topicService;
    private IModel<Category> categoryModel;

    /**
     * Constructor for creating a instance of getting the topic in the discussion.
     *
     * @param topicService instance of topic service
     */
    public TopicWicketModel(TopicService topicService) {

        this(new Model<Category>(), topicService);
    }

    /**
     * Constructor for creating a instance of getting topic in the forum.
     *
     * @param categoryModel variable contains the discussion for getting the posts in the forum
     * @param topicService instance of the topic service
     */
    public TopicWicketModel(IModel<Category> categoryModel, TopicService topicService) {

        this.categoryModel = categoryModel;
        this.topicService = topicService;
    }

    protected List<Topic> load() {

        if (categoryModel.getObject() == null) {
            return topicService.getTopicsWithoutCategory();
        } else {
            return topicService.getTopicsByCategory(categoryModel.getObject());
        }
    }

}
