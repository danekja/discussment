package org.danekja.discussment.example.discussion;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.dao.jpa.*;
import org.danekja.discussment.core.service.*;
import org.danekja.discussment.core.service.imp.*;
import org.danekja.discussment.example.base.BasePage;
import org.danekja.discussment.ui.wicket.panel.forum.ForumPanel;

import java.util.HashMap;


/**
 * Homepage
 */
public class DiscussionPage extends BasePage {

	private static final long serialVersionUID = 1L;

    private IDiscussionService discussionService;
    private ICategoryService categoryService;
    private ITopicService topicService;
    private IPostService postService;
    private IUserService userService;

    private IModel<HashMap<String, Integer>> parametersModel;

    private PageParameters parameters;

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public DiscussionPage(final PageParameters parameters) {

        this.parameters = parameters;

        CategoryJPA categoryJPA = new CategoryJPA();
        TopicJPA topicJPA = new TopicJPA();
        UserJPA userJPA = new UserJPA();
        DiscussionJPA discussionJPA = new DiscussionJPA();
        PermissionJPA permissionJPA = new PermissionJPA();
        PostJPA postJPA = new PostJPA();

        this.discussionService = new DiscussionService(discussionJPA);
        this.categoryService = new CategoryService(categoryJPA);
        this.topicService = new TopicService(topicJPA, categoryJPA);
        this.postService = new PostService(postJPA);
        this.userService = new UserService(userJPA, permissionJPA);

        parametersModel = new Model<HashMap<String, Integer>>();
        parametersModel.setObject(new HashMap<String, Integer>());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        parametersModel.getObject().put("topicId", parameters.get("topicId").isNull() ? -1 : Integer.parseInt(parameters.get("topicId").toString()));
        parametersModel.getObject().put("discussionId", parameters.get("discussionId").isNull() ? -1 : Integer.parseInt(parameters.get("discussionId").toString()));

        add(new ForumPanel("content", parametersModel, discussionService, topicService, categoryService, postService, userService));
    }

    @Override
    public String getTitle() {
        return "Post page";
    }
}

