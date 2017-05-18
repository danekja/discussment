package org.danekja.discussment.ui.wicket.form.password;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class PasswordFormComponent extends FormComponentPanel {

    private TextField<String> password;

    private IModel<String> passwordModel;

    public PasswordFormComponent(String id, IModel<String> passwordModel) {
        super(id, passwordModel);

        this.passwordModel = passwordModel;

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        password = new TextField<String>("password", new Model<String>(""));
        add(password);

    }

    @Override
    public void updateModel() {
        super.updateModel();

        passwordModel.setObject(password.getModelObject());

    }

}
