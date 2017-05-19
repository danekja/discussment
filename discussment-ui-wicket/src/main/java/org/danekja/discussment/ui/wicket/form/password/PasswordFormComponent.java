package org.danekja.discussment.ui.wicket.form.password;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.domain.Discussion;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class PasswordFormComponent extends Panel {

    public PasswordFormComponent(String id, IModel<Discussion> passwordModel) {
        super(id, passwordModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextField<String> password = new TextField<String>("password", new PropertyModel<String>(getDefaultModel(), "pass"));
        password.setRequired(true);
        add(password);

    }
}
