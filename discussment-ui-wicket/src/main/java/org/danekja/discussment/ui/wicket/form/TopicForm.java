package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.ui.wicket.form.topic.TopicFormComponent;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class TopicForm extends Form {

    private TopicService topicService;

    private IModel<Topic> topicModel;
    private IModel<Category> categoryModel;

    public TopicForm(String id, IModel<Category> categoryModel, IModel<Topic> topicModel) {
        this(id, null, categoryModel, topicModel);
    }

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
