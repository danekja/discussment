package org.danekja.discussment.example.form.login;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.domain.User;

/**
 * Created by Martin Bláha on 29.05.17.
 */
public class LoginFormComponent extends Panel {

    public LoginFormComponent(String id, IModel<User> userModel) {
        super(id, userModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextField<String> username = new TextField<String>("username", new PropertyModel<String>(getDefaultModel(), "username"));
        username.setRequired(true);
        add(username);

    }

}
