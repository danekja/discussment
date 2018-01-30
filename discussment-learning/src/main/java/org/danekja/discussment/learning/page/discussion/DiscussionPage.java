package org.danekja.discussment.learning.page.discussion;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.dao.jpa.*;
import org.danekja.discussment.core.service.*;
import org.danekja.discussment.core.service.imp.*;
import org.danekja.discussment.learning.WicketApplication;
import org.danekja.discussment.learning.page.base.BasePage;
import org.danekja.discussment.ui.wicket.panel.forum.ForumPanel;

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
        UserDaoJPA userJPA = new UserDaoJPA(em);
        DiscussionDaoJPA discussionJPA = new DiscussionDaoJPA(em);
        PermissionDaoJPA permissionJPA = new PermissionDaoJPA(em);
        PostDaoJPA postJPA = new PostDaoJPA(em);

        this.discussionService = new DefaultDiscussionService(discussionJPA);
        this.categoryService = new DefaultCategoryService(categoryDaoJPA);
        this.topicService = new DefaultTopicService(topicJPA, categoryDaoJPA);
        this.postService = new DefaultPostService(postJPA);
        this.userService = new DefaultUserService(userJPA, permissionJPA);

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

