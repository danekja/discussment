package cz.zcu.fav.kiv.discussion.example;

import cz.zcu.fav.kiv.discussion.core.entity.PermissionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;
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
        permissions.put(PermissionEntity.CREATE_CATEGORY, createCategoryRegistration);
        permissions.put(PermissionEntity.REMOVE_CATEGORY, removeCategoryRegistration);

        permissions.put(PermissionEntity.CREATE_TOPIC, createTopicRegistration);
        permissions.put(PermissionEntity.REMOVE_TOPIC, removeTopicRegistration);

        permissions.put(PermissionEntity.CREATE_DISCUSSION, createDiscussionRegistration);
        permissions.put(PermissionEntity.REMOVE_DISCUSSION, removeDiscussionRegistration);

        permissions.put(PermissionEntity.CREATE_POST, createPostRegistration);
        permissions.put(PermissionEntity.REMOVE_POST, removePostRegistration);
        permissions.put(PermissionEntity.DISABLE_POST, disablePostRegistration);

        permissions.put(PermissionEntity.READ_PRIVATE_DISCUSSION, readPrivateDiscussionRegistration);

        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setPermissions(permissions);

        UserEntity user = new UserEntity();
        user.setUsername(usernameRegistration);
        user.setName("");
        user.setLastname("");

        getSession().setAttribute("user", UserService.addUser(user, permissionEntity));

        setResponsePage(DashboardPage.class);
    }
}
