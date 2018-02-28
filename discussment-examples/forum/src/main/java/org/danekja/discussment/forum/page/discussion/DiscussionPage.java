package org.danekja.discussment.forum.page.discussion;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.accesscontrol.dao.jpa.PermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.PermissionManagementService;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
import org.danekja.discussment.core.dao.jpa.CategoryDaoJPA;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.core.dao.jpa.PostDaoJPA;
import org.danekja.discussment.core.dao.jpa.TopicDaoJPA;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.core.service.imp.DefaultCategoryService;
import org.danekja.discussment.core.service.imp.DefaultDiscussionService;
import org.danekja.discussment.core.service.imp.DefaultPostService;
import org.danekja.discussment.core.service.imp.DefaultTopicService;
import org.danekja.discussment.forum.WicketApplication;
import org.danekja.discussment.forum.core.dao.UserDao;
import org.danekja.discussment.forum.core.dao.jpa.UserDaoJPA;
import org.danekja.discussment.forum.core.service.UserService;
import org.danekja.discussment.forum.core.service.imp.DefaultUserService;
import org.danekja.discussment.forum.page.base.BasePage;
import org.danekja.discussment.ui.wicket.panel.forum.ForumPanel;
import org.danekja.discussment.ui.wicket.panel.notLoggedIn.NotLoggedInPanel;

import javax.persistence.EntityManager;
import java.util.HashMap;


/**
 * Homepage
 */
public class DiscussionPage extends BasePage {

	private static final long serialVersionUID = 1L;

    private EntityManager em;

    private DiscussionService discussionService;
    private CategoryService categoryService;
    private TopicService topicService;
    private PostService postService;
    private UserService userService;
    private AccessControlService accessControlService;
    private PermissionManagementService permissionService;

    private IModel<HashMap<String, Integer>> parametersModel;

    final PageParameters parameters;

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public DiscussionPage(final PageParameters parameters) {

        this.em = WicketApplication.factory.createEntityManager();
        this.parameters = parameters;


        CategoryDaoJPA categoryDaoJPA = new CategoryDaoJPA(em);
        TopicDaoJPA topicJPA = new TopicDaoJPA(em);
        UserDao userDao = new UserDaoJPA(em);
        DiscussionDaoJPA discussionJPA = new DiscussionDaoJPA(em);
        PermissionDaoJPA permissionJPA = new PermissionDaoJPA(em);
        PostDaoJPA postJPA = new PostDaoJPA(em);

        this.userService = new DefaultUserService(userDao);
        this.accessControlService = new PermissionService(permissionJPA, userService);
        this.discussionService = new DefaultDiscussionService(discussionJPA, postJPA, accessControlService, userService);
        this.categoryService = new DefaultCategoryService(categoryDaoJPA, accessControlService, userService);
        this.topicService = new DefaultTopicService(topicJPA, accessControlService, userService);
        this.postService = new DefaultPostService(postJPA, userService, accessControlService);
        this.permissionService = new PermissionService(permissionJPA, userService);

        parametersModel = new Model<HashMap<String, Integer>>();
        parametersModel.setObject(new HashMap<String, Integer>());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        parametersModel.getObject().put("topicId", parameters.get("topicId").isNull() ? -1 : Integer.parseInt(parameters.get("topicId").toString()));
        parametersModel.getObject().put("discussionId", parameters.get("discussionId").isNull() ? -1 : Integer.parseInt(parameters.get("discussionId").toString()));
        if(userService.getCurrentlyLoggedUser() == null) {
            add(new NotLoggedInPanel("content"));
        } else {
            add(new ForumPanel("content", parametersModel, discussionService, topicService, categoryService, postService, userService, accessControlService, permissionService));
        }
    }

    @Override
    public String getTitle() {
        return "Post page";
    }
}

