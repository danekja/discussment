package org.danekja.discussment.example.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.example.core.User;
import org.danekja.discussment.example.core.UserService;
import org.danekja.discussment.example.form.registration.RegistrationFormComponent;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class RegistrationForm extends Form {

    private UserService userService;

    private IModel<User> userModel;

    public RegistrationForm(String id, UserService userService, IModel<User> userModel) {
        super(id);

        this.userService = userService;
        this.userModel = userModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new RegistrationFormComponent("registrationFormComponent", userModel));
    }

    @Override
    protected void onSubmit() {

        getSession().setAttribute("user", userService.addUser(userModel.getObject(), userModel.getObject().getPermissions()));

        setResponsePage(getWebPage().getClass());
    }
}
