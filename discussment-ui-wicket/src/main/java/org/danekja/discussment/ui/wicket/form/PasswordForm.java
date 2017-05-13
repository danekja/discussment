package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.IDiscussionService;
import org.danekja.discussment.core.service.IUserService;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class PasswordForm extends Form {

    private IUserService userService;
    private IDiscussionService discussionService;

    private String password;

    private long discussionId;

    public PasswordForm(String id, IUserService userService, IDiscussionService discussionService) {
        super(id);

        this.userService = userService;
        this.discussionService = discussionService;

        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextField("password"));
    }

    public long getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(long discussionId) {
        this.discussionId = discussionId;
    }

    @Override
    protected void onSubmit() {

        Discussion dis = discussionService.getDiscussionById(discussionId);

        PageParameters pageParameters = new PageParameters();

        if (dis.getPass().equals(password)) {

            User user = (User) getSession().getAttribute("user");

            if (user != null) {
                userService.addAccessToDiscussion(user, dis);
            }

            pageParameters.add("discussionId", discussionId);
        } else {
            pageParameters.add("error", "denied");
        }

        setResponsePage(getPage().getClass(), pageParameters);

    }
}
