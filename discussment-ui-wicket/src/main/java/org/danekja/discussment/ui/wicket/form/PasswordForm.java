package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.UserService;
import org.danekja.discussment.ui.wicket.form.password.PasswordFormComponent;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class PasswordForm extends Form {

    private IModel<Discussion> discussionModel;
    private UserService userService;

    private IModel<Discussion> passwordModel;

    public PasswordForm(String id, IModel<Discussion> discussionModel) {
        this(id, null, discussionModel);
    }

    public PasswordForm(String id, UserService userService, IModel<Discussion> discussionModel) {
        super(id);

        this.userService = userService;
        this.discussionModel = discussionModel;

        this.passwordModel = new Model<Discussion>(new Discussion());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new PasswordFormComponent("passwordFormComponent", passwordModel));
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void onSubmit() {

        PageParameters pageParameters = new PageParameters();

        if (userService != null) {

            if (discussionModel.getObject().getPass().equals(passwordModel.getObject().getPass())) {

                User user = (User) getSession().getAttribute("user");

                if (user != null) {
                    userService.addAccessToDiscussion(user, discussionModel.getObject());
                }

                pageParameters.add("discussionId", discussionModel.getObject().getId());
            } else {
                pageParameters.add("topicId", discussionModel.getObject().getTopic().getId());
                pageParameters.add("error", "denied");
            }

            passwordModel.setObject(new Discussion());

            setResponsePage(getPage().getClass(), pageParameters);
        }
    }
}
