package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.ui.wicket.form.password.PasswordFormComponent;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class PasswordForm extends Form {

    private IModel<Discussion> discussionModel;
    private DiscussionService discussionService;

    private IModel<Discussion> passwordModel;

    public PasswordForm(String id, IModel<Discussion> discussionModel, IModel<Discussion> passwordModel, IModel<Boolean> accsess) {
        this(id, null, discussionModel, passwordModel);
    }

    public PasswordForm(String id, DiscussionService discussionService, IModel<Discussion> discussionModel, IModel<Discussion> passwordModel) {
        super(id);

        this.discussionService = discussionService;
        this.discussionModel = discussionModel;
        this.passwordModel = passwordModel;

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new PasswordFormComponent("passwordFormComponent", passwordModel));
    }

    public void setDiscussionService(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @Override
    protected void onSubmit() {

        PageParameters pageParameters = new PageParameters();

        if (discussionService != null) {

            if (discussionModel.getObject().getPass().equals(passwordModel.getObject().getPass())) {

                User user = (User) getSession().getAttribute("user");

                if (user != null) {
                    discussionService.addAccessToDiscussion(user, discussionModel.getObject());
                }

                getSession().setAttribute("access", new Boolean(true));
                getSession().setAttribute("discussionId", new Long(discussionModel.getObject().getId()));

                pageParameters.add("discussionId", discussionModel.getObject().getId());
            } else {

                pageParameters.add("topicId", discussionModel.getObject().getTopic().getId());

                getSession().setAttribute("access", new Boolean(false));
                getSession().setAttribute("error", "password");

            }

            passwordModel.setObject(new Discussion());
            setResponsePage(getPage().getClass(), pageParameters);
        }
    }
}
