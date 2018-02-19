package org.danekja.discussment.article.ui.wicket.form.registration;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.article.core.domain.User;
import org.danekja.discussment.core.accesscontrol.domain.PermissionData;

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

        add(new CheckBox("createPostRegistration", new PropertyModel<Boolean>(postPermissionModel, "create")));
        add(new CheckBox("removePostRegistration", new PropertyModel<Boolean>(postPermissionModel, "delete")));
        add(new CheckBox("editPostRegistration", new PropertyModel<Boolean>(postPermissionModel, "edit")));
        add(new CheckBox("viewPostRegistration", new PropertyModel<Boolean>(postPermissionModel, "view")));

    }
}
