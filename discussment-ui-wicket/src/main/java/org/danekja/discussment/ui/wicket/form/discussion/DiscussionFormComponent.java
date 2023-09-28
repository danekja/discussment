package org.danekja.discussment.ui.wicket.form.discussion;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.domain.Discussion;

/**
 * Created by Martin Bláha on 03.02.17.
 *
 * The class contains the input fields for getting a name and password
 */
public class DiscussionFormComponent extends Panel {


    /**
     * Constructor for creating a instance of getting the name and password of the discussion.
     *
     * @param id id of the element into which the panel is inserted
     * @param discussionModel variable contains the category for setting the name and password
     */
    public DiscussionFormComponent(String id, IModel<Discussion> discussionModel) {
        super(id, discussionModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextField<String> name = new TextField<>("name", new PropertyModel<String>(getDefaultModel(), "name"));
        name.setRequired(true);
        add(name);

        add(new CheckBox("priv", Model.of(Boolean.FALSE)));

        TextField<String> password = new TextField<>("password", new PropertyModel<String>(getDefaultModel(), "pass"));
        add(password);

    }
}
