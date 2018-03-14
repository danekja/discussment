package org.danekja.discussment.spring.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.accesscontrol.domain.PermissionData;
import org.danekja.discussment.core.accesscontrol.service.PermissionManagementService;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.spring.core.domain.User;
import org.danekja.discussment.spring.core.service.UserService;
import org.danekja.discussment.spring.form.registration.RegistrationFormComponent;


/**
 * Created by Martin Bláha on 21.01.17.
 */
public class RegistrationForm extends Form {

    private UserService userService;
    private PermissionManagementService permissionService;

    private IModel<User> userModel;

    private IModel<PermissionData> categoryPermissions;
    private IModel<PermissionData> topicPermissions;
    private IModel<PermissionData> discussionPermissions;
    private IModel<PermissionData> postPermissions;


    private RegistrationFormComponent registrationFormComponent;

    public RegistrationForm(String id,
                            UserService userService,
                            IModel<User> userModel,
                            PermissionManagementService permissionService,
                            CategoryService categoryService) {
        super(id);

        this.userService = userService;
        this.userModel = userModel;
        this.permissionService = permissionService;

        this.categoryPermissions = new Model<PermissionData>(new PermissionData());
        this.discussionPermissions = new Model<PermissionData>(new PermissionData());
        this.topicPermissions = new Model<PermissionData>(new PermissionData());
        this.postPermissions = new Model<PermissionData>(new PermissionData());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        registrationFormComponent = new RegistrationFormComponent("registrationFormComponent", userModel, categoryPermissions, topicPermissions, discussionPermissions, postPermissions);
        add(registrationFormComponent);
    }

    @Override
    protected void onSubmit() {

        User u = userService.addUser(userModel.getObject());

        permissionService.configureCategoryPermissions(u, categoryPermissions.getObject());
        permissionService.configureTopicPermissions(u, topicPermissions.getObject());
        permissionService.configureDiscussionPermissions(u, discussionPermissions.getObject());
        permissionService.configurePostPermissions(u, postPermissions.getObject());

        getSession().setAttribute("user", u);

        setResponsePage(getWebPage().getClass());
    }
}
