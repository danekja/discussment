package org.danekja.discussment.forum.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.accesscontrol.domain.PermissionData;
import org.danekja.discussment.core.accesscontrol.service.PermissionManagementService;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.forum.core.domain.User;
import org.danekja.discussment.forum.core.service.UserService;
import org.danekja.discussment.forum.form.registration.RegistrationFormComponent;
import org.danekja.discussment.forum.session.UserSession;


/**
 * Created by Martin Bláha on 21.01.17.
 */
public class RegistrationForm extends Form {

    private CategoryService categoryService;
    private TopicService topicService;
    private DiscussionService discussionService;
    private UserService userService;
    private PermissionManagementService permissionService;

    private IModel<User> userModel;

    private IModel<PermissionData> categoryPermissions;

    private IModel<PermissionData> defaultTopicPermissions;
    private IModel<PermissionData> globalTopicPermissions;

    private IModel<PermissionData> defaultDiscussionPermissions;
    private IModel<PermissionData> globalDiscussionPermissions;

    private IModel<PermissionData> defaultPostPermissions;
    private IModel<PermissionData> globalPostPermissions;

    private RegistrationFormComponent registrationFormComponent;

    public RegistrationForm(String id,
                            UserService userService,
                            IModel<User> userModel,
                            PermissionManagementService permissionService,
                            CategoryService categoryService,
                            TopicService topicService,
                            DiscussionService discussionService) {
        super(id);

        this.userService = userService;
        this.userModel = userModel;
        this.permissionService = permissionService;
        this.categoryService = categoryService;
        this.topicService = topicService;
        this.discussionService = discussionService;

        this.categoryPermissions = new Model<PermissionData>(new PermissionData());

        this.defaultDiscussionPermissions = new Model<PermissionData>(new PermissionData());
        this.globalTopicPermissions = new Model<PermissionData>(new PermissionData());

        this.defaultTopicPermissions = new Model<PermissionData>(new PermissionData());
        this.globalDiscussionPermissions = new Model<PermissionData>(new PermissionData());

        this.defaultPostPermissions = new Model<PermissionData>(new PermissionData());
        this.globalPostPermissions = new Model<PermissionData>(new PermissionData());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        registrationFormComponent = new RegistrationFormComponent("registrationFormComponent",
                userModel,
                categoryPermissions,
                defaultTopicPermissions,
                globalTopicPermissions,
                defaultDiscussionPermissions,
                globalDiscussionPermissions,
                defaultPostPermissions,
                globalPostPermissions
        );
        add(registrationFormComponent);
    }

    @Override
    protected void onSubmit() {

        User u = userService.addUser(userModel.getObject());

        permissionService.configureCategoryPermissions(u, categoryPermissions.getObject());
        permissionService.configureTopicPermissions(u, globalTopicPermissions.getObject());
        permissionService.configureDiscussionPermissions(u, globalDiscussionPermissions.getObject());
        permissionService.configurePostPermissions(u, globalPostPermissions.getObject());
        permissionService.configureTopicPermissions(u, categoryService.getDefaultCategory(), defaultTopicPermissions.getObject());
        permissionService.configureDiscussionPermissions(u, topicService.getDefaultTopic(), defaultDiscussionPermissions.getObject());
        permissionService.configurePostPermissions(u, discussionService.getDefaultDiscussion(), defaultPostPermissions.getObject());

        UserSession userSession = (UserSession) getSession();
        userSession.signIn(u.getUsername(), null);

        setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
    }
}