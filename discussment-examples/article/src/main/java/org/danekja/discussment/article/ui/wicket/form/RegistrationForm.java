package org.danekja.discussment.article.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.article.core.domain.User;
import org.danekja.discussment.article.core.service.UserService;
import org.danekja.discussment.article.ui.wicket.form.registration.RegistrationFormComponent;
import org.danekja.discussment.core.accesscontrol.domain.PermissionData;
import org.danekja.discussment.core.accesscontrol.service.PermissionManagementService;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.TopicService;


/**
 * Created by Martin Bláha on 21.01.17.
 */
public class RegistrationForm extends Form {

    private final UserService userService;
    private final PermissionManagementService permissionService;
    private final CategoryService categoryService;
    private final TopicService topicService;
    private final DiscussionService discussionService;

    private final IModel<User> userModel;

    private final IModel<PermissionData> categoryPermissions;
    private final IModel<PermissionData> topicPermissions;
    private final IModel<PermissionData> discussionPermissions;
    private final IModel<PermissionData> postPermissions;


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
        this.discussionPermissions = new Model<PermissionData>(new PermissionData(true, false, false, true));
        this.topicPermissions = new Model<PermissionData>(new PermissionData(false, false, false, true));
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
        permissionService.configureTopicPermissions(u, categoryService.getDefaultCategory(), topicPermissions.getObject());
        permissionService.configureDiscussionPermissions(u, topicService.getDefaultTopic(), discussionPermissions.getObject());
        permissionService.configurePostPermissions(u, topicService.getDefaultTopic(), postPermissions.getObject());

        getSession().setAttribute("user", u);

        setResponsePage(getWebPage().getClass());
    }
}