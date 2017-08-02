package org.danekja.discussment.example.page.discussion;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.dao.jpa.*;
import org.danekja.discussment.core.service.*;
import org.danekja.discussment.core.service.imp.*;
import org.danekja.discussment.example.core.DefaultUserService;
import org.danekja.discussment.example.core.UserDao;
import org.danekja.discussment.example.core.UserDaoMock;
import org.danekja.discussment.example.core.UserService;
import org.danekja.discussment.example.page.base.BasePage;
import org.danekja.discussment.ui.wicket.panel.forum.ForumPanel;

import java.util.HashMap;


/**
 * Homepage
 */
public class DiscussionPage extends BasePage {

	private static final long serialVersionUID = 1L;

    private DiscussionService discussionService;
    private CategoryService categoryService;
    private TopicService topicService;
    private PostService postService;
    private UserService userService;
    private PermissionService permissionService;

    private IModel<HashMap<String, Integer>> parametersModel;

    final PageParameters parameters;

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public DiscussionPage(final PageParameters parameters) {

        this.parameters = parameters;

        CategoryDaoJPA categoryDaoJPA = new CategoryDaoJPA();
        TopicDaoJPA topicJPA = new TopicDaoJPA();
        UserDao userDao = new UserDaoMock();
        DiscussionDaoJPA discussionJPA = new DiscussionDaoJPA();
        PermissionDaoJPA permissionJPA = new PermissionDaoJPA();
        PostDaoJPA postJPA = new PostDaoJPA();

        this.permissionService = new DefaultPermissionService(permissionJPA);
        this.discussionService = new DefaultDiscussionService(discussionJPA, permissionService);
        this.categoryService = new DefaultCategoryService(categoryDaoJPA);
        this.topicService = new DefaultTopicService(topicJPA, categoryDaoJPA);
        this.postService = new DefaultPostService(postJPA);
        this.userService = new DefaultUserService(userDao, permissionJPA);

        parametersModel = new Model<HashMap<String, Integer>>();
        parametersModel.setObject(new HashMap<String, Integer>());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        parametersModel.getObject().put("topicId", parameters.get("topicId").isNull() ? -1 : Integer.parseInt(parameters.get("topicId").toString()));
        parametersModel.getObject().put("discussionId", parameters.get("discussionId").isNull() ? -1 : Integer.parseInt(parameters.get("discussionId").toString()));

        add(new ForumPanel("content", parametersModel, discussionService, topicService, categoryService, postService, permissionService));
    }

    @Override
    public String getTitle() {
        return "Post page";
    }
}

