package org.danekja.discussment.ui.wicket.form.panel.password;

import org.danekja.discussment.core.service.IDiscussionService;
import org.danekja.discussment.core.service.IUserService;
import org.danekja.discussment.ui.wicket.form.PasswordForm;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class PasswordModalFormPanel extends Panel {


    private PasswordForm passwordForm;

    public PasswordModalFormPanel(String id, IUserService userService, IDiscussionService discussionService) {
        super(id);

        passwordForm = new PasswordForm("passwordForm", userService, discussionService);
        add(passwordForm);

    }

    public PasswordForm getPasswordForm() {
        return passwordForm;
    }

}
