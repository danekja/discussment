package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.TopicService;

import java.util.Collections;
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

        try {
            return topicService.getTopicsByCategory(categoryModel.getObject());
        } catch (AccessDeniedException e) {
            return Collections.emptyList();
        }
    }
}