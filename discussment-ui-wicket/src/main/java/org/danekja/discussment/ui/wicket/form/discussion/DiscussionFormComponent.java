package org.danekja.discussment.ui.wicket.form.discussion;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Discussion;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class DiscussionFormComponent extends FormComponentPanel {

    private TextField<String> name;
    private CheckBox priv;
    private TextField<String> password;

    private IModel<Discussion> discussionModel;


    public DiscussionFormComponent(String id, IModel<Discussion> discussionModel) {
        super(id, discussionModel);

        this.discussionModel = discussionModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        name = new TextField<String>("name", new Model<String>(""));
        add(name);

        priv = new CheckBox("priv", Model.of(Boolean.FALSE));
        add(priv);

        password = new TextField<String>("password", new Model<String>(""));
        add(password);

    }

    @Override
    public void updateModel() {
        super.updateModel();

        discussionModel.setObject(new Discussion(name.getModelObject(), password.getModelObject()));

    }


}
