package cz.zcu.fav.kiv.discussion.example;

import cz.zcu.fav.kiv.discussion.core.model.PermissionModel;
import cz.zcu.fav.kiv.discussion.core.model.UserModel;
import cz.zcu.fav.kiv.discussion.core.service.UserService;
import cz.zcu.fav.kiv.discussion.example.dashboard.DashboardPage;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import java.util.HashMap;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class RegistrationForm extends Form {

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

    public RegistrationForm(String id) {
        super(id);

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
        permissions.put(PermissionModel.CREATE_CATEGORY, createCategoryRegistration);
        permissions.put(PermissionModel.REMOVE_CATEGORY, removeCategoryRegistration);

        permissions.put(PermissionModel.CREATE_TOPIC, createTopicRegistration);
        permissions.put(PermissionModel.REMOVE_TOPIC, removeTopicRegistration);

        permissions.put(PermissionModel.CREATE_DISCUSSION, createDiscussionRegistration);
        permissions.put(PermissionModel.REMOVE_DISCUSSION, removeDiscussionRegistration);

        permissions.put(PermissionModel.CREATE_POST, createPostRegistration);
        permissions.put(PermissionModel.REMOVE_POST, removePostRegistration);
        permissions.put(PermissionModel.DISABLE_POST, disablePostRegistration);

        permissions.put(PermissionModel.READ_PRIVATE_DISCUSSION, readPrivateDiscussionRegistration);


        UserService.addUser(usernameRegistration, "", "", permissions);

        UserModel user = UserService.getUserByUsername(usernameRegistration);
        getSession().setAttribute("user", user);

        setResponsePage(DashboardPage.class);
    }
}
