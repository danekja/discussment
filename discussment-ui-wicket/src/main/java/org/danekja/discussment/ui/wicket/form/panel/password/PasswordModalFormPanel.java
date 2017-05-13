package org.danekja.discussment.ui.wicket.form.panel.password;

import org.danekja.discussment.ui.wicket.form.PasswordForm;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class PasswordModalFormPanel extends Panel {


    private PasswordForm passwordForm;

    public PasswordModalFormPanel(String id) {
        super(id);

        passwordForm = new PasswordForm("passwordForm");
        add(passwordForm);

    }

    public PasswordForm getPasswordForm() {
        return passwordForm;
    }

}
