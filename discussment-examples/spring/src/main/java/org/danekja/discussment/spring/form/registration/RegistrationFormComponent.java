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

    private IModel<PermissionData> categoryPermission;
    private IModel<PermissionData> defaultTopicPermission;
    private IModel<PermissionData> globalTopicPermission;
    private IModel<PermissionData> defaultDiscussionPermission;
    private IModel<PermissionData> globalDiscussionPermission;
    private IModel<PermissionData> defaultPostPermission;
    private IModel<PermissionData> globalPostPermission;

    public RegistrationFormComponent(String id,
                                     IModel<User> userModel,
                                     IModel<PermissionData> categoryPermission,
                                     IModel<PermissionData> defaultTopicPermission,
                                     IModel<PermissionData> globalTopicPermission,
                                     IModel<PermissionData> defaultDiscussionPermission,
                                     IModel<PermissionData> globalDiscussionPermission,
                                     IModel<PermissionData> defaultPostPermission,
                                     IModel<PermissionData> globalPostPermission) {
        super(id, userModel);
        this.categoryPermission = categoryPermission;
        this.defaultTopicPermission = defaultTopicPermission;
        this.globalTopicPermission = globalTopicPermission;
        this.defaultDiscussionPermission = defaultDiscussionPermission;
        this.globalDiscussionPermission = globalDiscussionPermission;
        this.defaultPostPermission = defaultPostPermission;
        this.globalPostPermission = globalPostPermission;

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextField<String> username = new TextField<String>("usernameRegistration", new PropertyModel<String>(getDefaultModel(), "username"));
        username.setRequired(true);
        add(username);

        add(new CheckBox("createCategoryRegistration", new PropertyModel<Boolean>(categoryPermission, "create")));
        add(new CheckBox("removeCategoryRegistration", new PropertyModel<Boolean>(categoryPermission, "delete")));
        add(new CheckBox("editCategoryRegistration", new PropertyModel<Boolean>(categoryPermission, "edit")));
        add(new CheckBox("viewCategoryRegistration", new PropertyModel<Boolean>(categoryPermission, "view")));

        add(new CheckBox("createDefaultTopicRegistration", new PropertyModel<Boolean>(defaultTopicPermission, "create")));
        add(new CheckBox("removeDefaultTopicRegistration", new PropertyModel<Boolean>(defaultTopicPermission, "delete")));
        add(new CheckBox("editDefaultTopicRegistration", new PropertyModel<Boolean>(defaultTopicPermission, "edit")));
        add(new CheckBox("viewDefaultTopicRegistration", new PropertyModel<Boolean>(defaultTopicPermission, "view")));

        add(new CheckBox("createGlobalTopicRegistration", new PropertyModel<Boolean>(globalTopicPermission, "create")));
        add(new CheckBox("removeGlobalTopicRegistration", new PropertyModel<Boolean>(globalTopicPermission, "delete")));
        add(new CheckBox("editGlobalTopicRegistration", new PropertyModel<Boolean>(globalTopicPermission, "edit")));
        add(new CheckBox("viewGlobalTopicRegistration", new PropertyModel<Boolean>(globalTopicPermission, "view")));

        add(new CheckBox("createDefaultDiscussionRegistration", new PropertyModel<Boolean>(defaultDiscussionPermission, "create")));
        add(new CheckBox("removeDefaultDiscussionRegistration", new PropertyModel<Boolean>(defaultDiscussionPermission, "delete")));
        add(new CheckBox("editDefaultDiscussionRegistration", new PropertyModel<Boolean>(defaultDiscussionPermission, "edit")));
        add(new CheckBox("viewDefaultDiscussionRegistration", new PropertyModel<Boolean>(defaultDiscussionPermission, "view")));

        add(new CheckBox("createGlobalDiscussionRegistration", new PropertyModel<Boolean>(globalDiscussionPermission, "create")));
        add(new CheckBox("removeGlobalDiscussionRegistration", new PropertyModel<Boolean>(globalDiscussionPermission, "delete")));
        add(new CheckBox("editGlobalDiscussionRegistration", new PropertyModel<Boolean>(globalDiscussionPermission, "edit")));
        add(new CheckBox("viewGlobalDiscussionRegistration", new PropertyModel<Boolean>(globalDiscussionPermission, "view")));

        add(new CheckBox("createDefaultPostRegistration", new PropertyModel<Boolean>(defaultPostPermission, "create")));
        add(new CheckBox("removeDefaultPostRegistration", new PropertyModel<Boolean>(defaultPostPermission, "delete")));
        add(new CheckBox("editDefaultPostRegistration", new PropertyModel<Boolean>(defaultPostPermission, "edit")));
        add(new CheckBox("viewDefaultPostRegistration", new PropertyModel<Boolean>(defaultPostPermission, "view")));

        add(new CheckBox("createGlobalPostRegistration", new PropertyModel<Boolean>(globalPostPermission, "create")));
        add(new CheckBox("removeGlobalPostRegistration", new PropertyModel<Boolean>(globalPostPermission, "delete")));
        add(new CheckBox("editGlobalPostRegistration", new PropertyModel<Boolean>(globalPostPermission, "edit")));
        add(new CheckBox("viewGlobalPostRegistration", new PropertyModel<Boolean>(globalPostPermission, "view")));
    }
}
