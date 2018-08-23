package org.danekja.discussment.spring.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.spring.core.domain.User;
import org.danekja.discussment.spring.core.service.UserService;
import org.danekja.discussment.spring.form.login.LoginFormComponent;
import org.danekja.discussment.spring.session.UserSession;
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

        User user = userService.getUserByUsername(userModel.getObject().getDisplayName());

        if (user != null) {
            UserSession userSession = (UserSession) getSession();
            userSession.signIn(user.getUsername(), null);
        } else {
            SessionUtil.setError("username");
        }

        userModel.setObject(new User());
        setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
    }

}
