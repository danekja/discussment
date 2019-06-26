package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.accesscontrol.domain.PermissionData;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.accesscontrol.service.PermissionManagementService;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.ui.wicket.form.password.PasswordFormComponent;
import org.danekja.discussment.ui.wicket.session.SessionUtil;

/**
 * Created by Martin Bláha on 25.01.17.
 *
 * The class creates the form for verification a password
 */
public class PasswordForm extends Form {

    private IModel<Discussion> discussionModel;

    private PermissionManagementService permissionService;
    private DiscussionService discussionService;
    private DiscussionUserService userService;
    private AccessControlService accessControlService;

    private IModel<Discussion> passwordModel;

    /**
     * Constructor for creating a instance of the form for verification a password
     *
     * @param id id of the element into which the panel is inserted
     * @param discussionService instance of the discussion service
     * @param discussionModel model contains the discussion to verification access to the discussion
     * @param passwordModel model contains the discussion for setting the form
     */
    public PasswordForm(String id,
                        IModel<Discussion> discussionModel,
                        IModel<Discussion> passwordModel,
                        DiscussionUserService userService,
                        AccessControlService accessControlService,
                        PermissionManagementService permissionService,
                        DiscussionService discussionService) {
        super(id);

        this.discussionModel = discussionModel;
        this.passwordModel = passwordModel;

        this.discussionService = discussionService;
        this.accessControlService = accessControlService;
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new PasswordFormComponent("passwordFormComponent", passwordModel));
    }

    @Override
    protected void onSubmit() {
        PageParameters pageParameters = getPage().getPageParameters();

        if (discussionModel.getObject().getPass() == null) {
            // no password for discussion
            SessionUtil.setAccess(true);
            SessionUtil.setDiscussionId(discussionModel.getObject().getId());

            pageParameters.add("discussionId", discussionModel.getObject().getId());

        } else if (discussionModel.getObject().getPass().equals(passwordModel.getObject().getPass())) {
            if (!accessControlService.canViewPosts(discussionModel.getObject())) {
                permissionService.configurePostPermissions(userService.getCurrentlyLoggedUser(), discussionModel.getObject(), new PermissionData(false, false, false, true));
            }

            SessionUtil.setAccess(true);
            SessionUtil.setDiscussionId(discussionModel.getObject().getId());

            pageParameters.add("discussionId", discussionModel.getObject().getId());

        } else {
            SessionUtil.setAccess(true);
            SessionUtil.setError("password");

            pageParameters.add("topicId", discussionModel.getObject().getTopic().getId());
        }

        passwordModel.setObject(new Discussion());

        setResponsePage(getPage().getPageClass(), pageParameters);
    }
}
