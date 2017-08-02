package org.danekja.discussment.example.form.registration;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.domain.Permission;
import org.danekja.discussment.example.core.User;

/**
 * Created by Martin Bláha on 29.05.17.
 */
public class RegistrationFormComponent extends Panel {

    private IModel<Permission> permissionModel;

    public RegistrationFormComponent(String id, IModel<User> userModel, IModel<Permission> permissionModel) {
        super(id, userModel);
        this.permissionModel = permissionModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextField<String> username = new TextField<String>("usernameRegistration", new PropertyModel<String>(getDefaultModel(), "username"));
        username.setRequired(true);
        add(username);

        add(new CheckBox("createCategoryRegistration", new PropertyModel<Boolean>(permissionModel, "createCategory")));
        add(new CheckBox("removeCategoryRegistration", new PropertyModel<Boolean>(permissionModel, "removeCategory")));

        add(new CheckBox("createTopicRegistration", new PropertyModel<Boolean>(permissionModel, "createTopic")));
        add(new CheckBox("removeTopicRegistration", new PropertyModel<Boolean>(permissionModel, "removeTopic")));

        add(new CheckBox("createDiscussionRegistration", new PropertyModel<Boolean>(permissionModel, "createDiscussion")));
        add(new CheckBox("removeDiscussionRegistration", new PropertyModel<Boolean>(permissionModel, "removeDiscussion")));

        add(new CheckBox("createPostRegistration", new PropertyModel<Boolean>(permissionModel, "createPost")));
        add(new CheckBox("removePostRegistration", new PropertyModel<Boolean>(permissionModel, "removePost")));
        add(new CheckBox("disablePostRegistration", new PropertyModel<Boolean>(permissionModel, "disablePost")));

        add(new CheckBox("readPrivateDiscussionRegistration", new PropertyModel<Boolean>(permissionModel, "readPrivateDiscussion")));

    }

    public IModel<Permission> getPermissionModel() {
        return permissionModel;
    }
}
