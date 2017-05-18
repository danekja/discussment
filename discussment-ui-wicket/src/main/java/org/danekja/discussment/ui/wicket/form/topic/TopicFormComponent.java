package org.danekja.discussment.ui.wicket.form.topic;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Topic;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class TopicFormComponent extends FormComponentPanel {

    private TextField<String> name;
    private TextField<String> description;

    private IModel<Topic> topicModel;

    public TopicFormComponent(String id, IModel<Topic> topicModel) {
        super(id, topicModel);

        this.topicModel = topicModel;

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        name = new TextField<String>("name", new Model<String>(""));
        add(name);

        description = new TextField<String>("description", new Model<String>(""));
        add(description);

    }

    @Override
    public void updateModel() {
        super.updateModel();

        topicModel.setObject(new Topic(name.getModelObject(), description.getModelObject()));

    }

}
