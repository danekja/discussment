package org.danekja.discussment.example.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.example.core.User;
import org.danekja.discussment.example.core.UserService;
import org.danekja.discussment.example.form.login.LoginFormComponent;
import org.danekja.discussment.ui.wicket.session.SessionUtil;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class LoginForm extends Form {

    private UserService userService;

    private IModel<User> userModel;

    public LoginForm(String id, UserService userService, IModel<User> userModel) {
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
            SessionUtil.setUser(user);
        } else {
            SessionUtil.setError("username");
        }

        userModel.setObject(new User());
    }

}
