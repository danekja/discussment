package org.danekja.discussment.spring.form.registration;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.accesscontrol.domain.PermissionData;
import org.danekja.discussment.spring.core.domain.User;

/**
 * Created by Martin Bláha on 29.05.17.
 */
public class RegistrationFormComponent extends Panel {

    private IModel<PermissionData> categoryPermissionModel;
    private IModel<PermissionData> topicPermissionModel;
    private IModel<PermissionData> discussionPermissionModel;
    private IModel<PermissionData> postPermissionModel;

    public RegistrationFormComponent(String id,
                                     IModel<User> userModel,
                                     IModel<PermissionData> categoryPermissionModel,
                                     IModel<PermissionData> topicPermissionModel,
                                     IModel<PermissionData> discussionPermissionModel,
                                     IModel<PermissionData> postPermissionModel) {
        super(id, userModel);
        this.categoryPermissionModel = categoryPermissionModel;
        this.topicPermissionModel = topicPermissionModel;
        this.discussionPermissionModel = discussionPermissionModel;
        this.postPermissionModel = postPermissionModel;

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextField<String> username = new TextField<String>("usernameRegistration", new PropertyModel<String>(getDefaultModel(), "username"));
        username.setRequired(true);
        add(username);

        add(new CheckBox("createCategoryRegistration", new PropertyModel<Boolean>(categoryPermissionModel, "create")));
        add(new CheckBox("removeCategoryRegistration", new PropertyModel<Boolean>(categoryPermissionModel, "delete")));
        add(new CheckBox("editCategoryRegistration", new PropertyModel<Boolean>(categoryPermissionModel, "edit")));
        add(new CheckBox("viewCategoryRegistration", new PropertyModel<Boolean>(categoryPermissionModel, "view")));

        add(new CheckBox("createTopicRegistration", new PropertyModel<Boolean>(topicPermissionModel, "create")));
        add(new CheckBox("removeTopicRegistration", new PropertyModel<Boolean>(topicPermissionModel, "delete")));
        add(new CheckBox("editTopicRegistration", new PropertyModel<Boolean>(topicPermissionModel, "edit")));
        add(new CheckBox("viewTopicRegistration", new PropertyModel<Boolean>(topicPermissionModel, "view")));

        add(new CheckBox("createDiscussionRegistration", new PropertyModel<Boolean>(discussionPermissionModel, "create")));
        add(new CheckBox("removeDiscussionRegistration", new PropertyModel<Boolean>(discussionPermissionModel, "delete")));
        add(new CheckBox("editDiscussionRegistration", new PropertyModel<Boolean>(discussionPermissionModel, "edit")));
        add(new CheckBox("viewDiscussionRegistration", new PropertyModel<Boolean>(discussionPermissionModel, "view")));

        add(new CheckBox("createPostRegistration", new PropertyModel<Boolean>(postPermissionModel, "create")));
        add(new CheckBox("removePostRegistration", new PropertyModel<Boolean>(postPermissionModel, "delete")));
        add(new CheckBox("editPostRegistration", new PropertyModel<Boolean>(postPermissionModel, "edit")));
        add(new CheckBox("viewPostRegistration", new PropertyModel<Boolean>(postPermissionModel, "view")));

        //add(new CheckBox("readPrivateDiscussionRegistration", new PropertyModel<Boolean>(permissionModel, "readPrivateDiscussion")));

    }

    /*public IModel<Permission> getPermissionModel() {
        return permissionModel;
    }*/
}
