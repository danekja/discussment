package org.danekja.discussment.ui.wicket.form.topic;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.domain.Topic;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class TopicFormComponent extends Panel {

    public TopicFormComponent(String id, IModel<Topic> topicModel) {
        super(id, topicModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextField<String> name = new TextField<String>("name", new PropertyModel<String>(getDefaultModel(), "name"));
        name.setRequired(true);
        add(name);

        TextField<String> description = new TextField<String>("description", new PropertyModel<String>(getDefaultModel(), "description"));
        add(description);

    }

}
