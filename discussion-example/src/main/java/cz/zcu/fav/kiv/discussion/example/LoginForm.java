package cz.zcu.fav.kiv.discussion.example;

import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;
import cz.zcu.fav.kiv.discussion.core.service.UserService;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class LoginForm extends Form {

    private String username;

    public LoginForm(String id) {
        super(id);

        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextField("username"));
    }

    @Override
    protected void onSubmit() {

        UserEntity user = UserService.getUserByUsername(username);

        if (user != null) {
            getSession().setAttribute("user", user);
        }
    }

}
