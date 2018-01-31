package org.danekja.discussment.learning.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.UserService;
import org.danekja.discussment.learning.form.login.LoginFormComponent;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class LoginForm extends Form {

    private UserService userService;

    private IModel<User> userModel;

    public LoginForm(String id, IModel<User> userModel, UserService userService) {
        super(id);

        this.userService = userService;
        this.userModel = userModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new LoginFormComponent("loginFormComponent", userModel));
    }

    @Override
    protected void onSubmit() {

        User user = userService.getUserByUsername(userModel.getObject().getUsername());

        if (user != null) {
            getSession().setAttribute("user", user);
        } else {
            getSession().setAttribute("error", "username");
        }

        userModel.setObject(new User());
    }

}
