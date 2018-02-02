package org.danekja.discussment.article.ui.wicket.form.registration;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.domain.User;

/**
 * Created by Martin Bláha on 29.05.17.
 */
public class RegistrationFormComponent extends Panel {

    public RegistrationFormComponent(String id, IModel<User> userModel) {
        super(id, userModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextField<String> username = new TextField<String>("usernameRegistration", new PropertyModel<String>(getDefaultModel(), "username"));
        username.setRequired(true);
        add(username);

        add(new CheckBox("createCategoryRegistration", new PropertyModel<Boolean>(getDefaultModel(), "permissions.createCategory")));
        add(new CheckBox("removeCategoryRegistration", new PropertyModel<Boolean>(getDefaultModel(), "permissions.removeCategory")));

        add(new CheckBox("createPostRegistration", new PropertyModel<Boolean>(getDefaultModel(), "permissions.createPost")));
        add(new CheckBox("removePostRegistration", new PropertyModel<Boolean>(getDefaultModel(), "permissions.removePost")));
        add(new CheckBox("disablePostRegistration", new PropertyModel<Boolean>(getDefaultModel(), "permissions.disablePost")));
    }

}
