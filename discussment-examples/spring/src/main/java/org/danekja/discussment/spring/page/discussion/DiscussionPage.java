package org.danekja.discussment.spring.page.discussion;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.PermissionManagementService;
import org.danekja.discussment.core.service.*;
import org.danekja.discussment.spring.core.service.UserService;
import org.danekja.discussment.spring.page.base.BasePage;
import org.danekja.discussment.ui.wicket.panel.forum.ForumPanel;

import javax.persistence.EntityManager;
import java.util.HashMap;


/**
 * Homepage
 */
public class DiscussionPage extends BasePage {

	private static final long serialVersionUID = 1L;

    private EntityManager em;

    @SpringBean
    private DiscussionService discussionService;

    @SpringBean
    private CategoryService categoryService;

    @SpringBean
    private TopicService topicService;

    @SpringBean
    private PostService postService;

    @SpringBean
    private UserService userService;

    @SpringBean(name = "accessControlService")
    private AccessControlService accessControlService;

    @SpringBean(name = "permissionManagementService")
    private PermissionManagementService permissionService;

    @SpringBean
    private PostReputationService postReputationService;

    private IModel<HashMap<String, Integer>> parametersModel;

    final PageParameters parameters;

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters Page parameters
	 */
    public DiscussionPage(final PageParameters parameters) {

        this.parameters = parameters;

        parametersModel = new Model<HashMap<String, Integer>>();
        parametersModel.setObject(new HashMap<String, Integer>());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        parametersModel.getObject().put("topicId", parameters.get("topicId").isNull() ? -1 : Integer.parseInt(parameters.get("topicId").toString()));
        parametersModel.getObject().put("discussionId", parameters.get("discussionId").isNull() ? -1 : Integer.parseInt(parameters.get("discussionId").toString()));

        add(new ForumPanel("content", parametersModel, discussionService, topicService, categoryService, postService, userService, postReputationService, accessControlService, permissionService));
    }

    @Override
    public String getTitle() {
        return "Post page";
    }
}

