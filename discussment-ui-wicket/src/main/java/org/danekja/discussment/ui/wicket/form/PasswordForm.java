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
     * Constructor for creating a instance of the form for verification the password
     *
     * @param id id of the element into which the panel is inserted
     * @param discussionModel model contains the discussion to verification access to the discussion
     * @param passwordModel model contains the discussion for setting the form
     */
    public PasswordForm(String id,
                        IModel<Discussion> discussionModel,
                        IModel<Discussion> passwordModel,
                        DiscussionUserService userService,
                        AccessControlService accessControlService,
                        PermissionManagementService permissionService) {
        this(id, discussionModel, passwordModel, userService, accessControlService, permissionService, null);
    }

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

        this.discussionService = discussionService;
        this.accessControlService = accessControlService;
        this.userService = userService;
        this.permissionService = permissionService;

        this.discussionModel = discussionModel;
        this.passwordModel = passwordModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new PasswordFormComponent("passwordFormComponent", passwordModel));
    }

    public void setDiscussionService(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @Override
    protected void onSubmit() {

        PageParameters pageParameters = new PageParameters();

        if (discussionService != null) {

            if (discussionModel.getObject().getPass() == null){
                // no password for discussion

                SessionUtil.setAccess(true);
                SessionUtil.setDiscussionId(discussionModel.getObject().getId());
                pageParameters.add("discussionId", discussionModel.getObject().getId());
            } else if (discussionModel.getObject().getPass().equals(passwordModel.getObject().getPass())) {
                if(accessControlService.canViewPosts(discussionModel.getObject()) == false) {
                    permissionService.configurePostPermissions(userService.getCurrentlyLoggedUser(), discussionModel.getObject(), new PermissionData(false, false, false, true));
                }

                SessionUtil.setAccess(true);
                SessionUtil.setDiscussionId(discussionModel.getObject().getId());

                pageParameters.add("discussionId", discussionModel.getObject().getId());
            } else {

                pageParameters.add("topicId", discussionModel.getObject().getTopic().getId());

                SessionUtil.setAccess(true);
                SessionUtil.setError("password");
            }

            passwordModel.setObject(new Discussion());
            setResponsePage(getPage().getClass(), pageParameters);
        }
    }
}
