package org.danekja.discussment.ui.wicket.form.topic;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.domain.Topic;

/**
 * Created by Martin Bláha on 03.02.17.
 *
 * The class contains the input fields for getting a name and description
 */
public class TopicFormComponent extends Panel {

    /**
     * Constructor for creating a instance of getting a name and description of athe post.
     *
     * @param id id of the element into which the panel is inserted
     * @param topicModel variable contains the topic for setting the name and description
     */
    public TopicFormComponent(String id, IModel<Topic> topicModel) {
        super(id, topicModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextField<String> name = new TextField<>("name", new PropertyModel<String>(getDefaultModel(), "name"));
        name.setRequired(true);
        add(name);

        TextField<String> description = new TextField<>("description", new PropertyModel<String>(getDefaultModel(), "description"));
        add(description);

    }

}
