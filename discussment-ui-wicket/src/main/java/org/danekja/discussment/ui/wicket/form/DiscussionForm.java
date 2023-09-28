package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.domain.PermissionData;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.accesscontrol.service.PermissionManagementService;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.ui.wicket.form.discussion.DiscussionFormComponent;

/**
 * Created by Martin Bláha on 25.01.17.
 *
 * The class creates the form for creating a new discussion
 */
public class DiscussionForm extends Form {

    private final DiscussionService discussionService;
    private final PermissionManagementService permissionService;
    private final DiscussionUserService userService;
    private final AccessControlService accessControlService;

    private final IModel<Topic> topicModel;
    private final IModel<Discussion> discussionModel;

    /**
     * Constructor for creating a instance of the form for adding a discussion form
     *
     * @param id id of the element into which the panel is inserted
     * @param topicModel model contains the topic for adding a new discussion
     * @param discussionModel model contains the discussion for setting the form
     * @param discussionService instance of the discussion service
     */
    public DiscussionForm(String id,
                          IModel<Topic> topicModel,
                          IModel<Discussion> discussionModel,
                          DiscussionUserService userService,
                          AccessControlService accessControlService,
                          PermissionManagementService permissionService,
                          DiscussionService discussionService) {
        super(id);

        this.topicModel = topicModel;
        this.discussionModel = discussionModel;

        this.userService = userService;
        this.permissionService = permissionService;
        this.accessControlService = accessControlService;
        this.discussionService = discussionService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new DiscussionFormComponent("discussionFormComponent", discussionModel));
    }

    @Override
    protected void onSubmit() {
        try {
            Discussion discussion = discussionService.createDiscussion(topicModel.getObject(), discussionModel.getObject());

            if (!accessControlService.canAddPost(discussion)) {
                permissionService.configurePostPermissions(userService.getCurrentlyLoggedUser(), discussion, new PermissionData(true, false, false, true));
            }

        } catch (AccessDeniedException e) {
            //todo: not yet implemented
        }

        discussionModel.setObject(new Discussion());

        PageParameters pageParameters = new PageParameters();
        pageParameters.add("topicId", topicModel.getObject().getId());
    }
}
