package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
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

    public TopicForm(String id, IModel<Category> categoryModel) {
        this(id, null, categoryModel);
    }

    public TopicForm(String id, TopicService topicService, IModel<Category> categoryModel) {
        super(id);

        this.categoryModel = categoryModel;
        this.topicService = topicService;

        this.topicModel = new Model<Topic>();
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
        }

    }
}
