package org.danekja.discussment.example;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.danekja.discussment.core.domain.Permission;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.IUserService;

import java.util.HashMap;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class RegistrationForm extends Form {

    private IUserService userService;

    private String usernameRegistration;
    private boolean createCategoryRegistration;
    private boolean removeCategoryRegistration;

    private boolean createTopicRegistration;
    private boolean removeTopicRegistration;

    private boolean createDiscussionRegistration;
    private boolean removeDiscussionRegistration;

    private boolean createPostRegistration;
    private boolean removePostRegistration;
    private boolean disablePostRegistration;

    private boolean readPrivateDiscussionRegistration;

    public RegistrationForm(String id, IUserService userService) {
        super(id);

        this.userService = userService;

        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextField("usernameRegistration"));

        add(new CheckBox("createCategoryRegistration"));
        add(new CheckBox("removeCategoryRegistration"));

        add(new CheckBox("createTopicRegistration"));
        add(new CheckBox("removeTopicRegistration"));

        add(new CheckBox("createDiscussionRegistration"));
        add(new CheckBox("removeDiscussionRegistration"));

        add(new CheckBox("createPostRegistration"));
        add(new CheckBox("removePostRegistration"));
        add(new CheckBox("disablePostRegistration"));

        add(new CheckBox("readPrivateDiscussionRegistration"));
    }

    @Override
    protected void onSubmit() {

        HashMap<Integer, Boolean> permissions = new HashMap<Integer, Boolean>();
        permissions.put(Permission.CREATE_CATEGORY, createCategoryRegistration);
        permissions.put(Permission.REMOVE_CATEGORY, removeCategoryRegistration);

        permissions.put(Permission.CREATE_TOPIC, createTopicRegistration);
        permissions.put(Permission.REMOVE_TOPIC, removeTopicRegistration);

        permissions.put(Permission.CREATE_DISCUSSION, createDiscussionRegistration);
        permissions.put(Permission.REMOVE_DISCUSSION, removeDiscussionRegistration);

        permissions.put(Permission.CREATE_POST, createPostRegistration);
        permissions.put(Permission.REMOVE_POST, removePostRegistration);
        permissions.put(Permission.DISABLE_POST, disablePostRegistration);

        permissions.put(Permission.READ_PRIVATE_DISCUSSION, readPrivateDiscussionRegistration);

        Permission permission = new Permission();
        permission.setPermissions(permissions);

        User user = new User();
        user.setUsername(usernameRegistration);
        user.setName("");
        user.setLastname("");

        getSession().setAttribute("user", userService.addUser(user, permission));

        setResponsePage(getWebPage().getClass());
    }
}
