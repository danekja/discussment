package org.danekja.discussment.example;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.IUserService;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class LoginForm extends Form {

    private IUserService userService;

    private String username;

    public LoginForm(String id, IUserService userService) {
        super(id);

        this.userService = userService;

        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextField("username"));
    }

    @Override
    protected void onSubmit() {

        User user = userService.getUserByUsername(username);

        if (user != null) {
            getSession().setAttribute("user", user);
        }
    }

}
