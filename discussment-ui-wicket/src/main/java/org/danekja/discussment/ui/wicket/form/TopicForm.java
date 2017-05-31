package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.ui.wicket.form.topic.TopicFormComponent;

/**
 * Created by Martin Bláha on 25.01.17.
 *
 * The class creates the form for creating a new topic
 */
public class TopicForm extends Form {

    private TopicService topicService;

    private IModel<Topic> topicModel;
    private IModel<Category> categoryModel;

    /**
     * Constructor for creating a instance of the form for creating a new topic
     *
     * @param id id of the element into which the panel is inserted
     * @param categoryModel model contains the category for adding a new topic
     * @param topicModel model contains the topic for setting the form
     */
    public TopicForm(String id, IModel<Category> categoryModel, IModel<Topic> topicModel) {
        this(id, null, categoryModel, topicModel);
    }

    /**
     * Constructor for creating a instance of the form for creating a new topic
     *
     * @param id id of the element into which the panel is inserted
     * @param topicService instance of the topic service
     * @param categoryModel model contains the category for adding a new topic
     * @param topicModel model contains the topic for setting the form
     */
    public TopicForm(String id, TopicService topicService, IModel<Category> categoryModel, IModel<Topic> topicModel) {
        super(id);

        this.categoryModel = categoryModel;
        this.topicService = topicService;
        this.topicModel = topicModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new TopicFormComponent("topicFormComponent", topicModel));
    }

    public void setTopicService(TopicService topicService) {
        this.topicService = topicService;
    }


    @Override
    protected void onSubmit() {

        if (topicService != null) {
            topicService.createTopic(topicModel.getObject(), categoryModel.getObject());

            topicModel.setObject(new Topic());
            setResponsePage(getWebPage().getClass());
        }

    }
}
