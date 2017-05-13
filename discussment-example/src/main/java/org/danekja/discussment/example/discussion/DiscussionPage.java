package org.danekja.discussment.example.discussion;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.dao.jpa.*;
import org.danekja.discussment.core.service.*;
import org.danekja.discussment.core.service.imp.*;
import org.danekja.discussment.example.base.BasePage;
import org.danekja.discussment.ui.wicket.panel.forum.ForumPanel;


/**
 * Homepage
 */
public class DiscussionPage extends BasePage {

	private static final long serialVersionUID = 1L;

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public DiscussionPage(final PageParameters parameters) {

        IDiscussionService discussionService = new DiscussionService(new DiscussionJPA());
        ICategoryService categoryService = new CategoryService(new CategoryJPA());
        ITopicService topicService = new TopicService(new TopicJPA(), new CategoryJPA());
        IPostService postService = new PostService(new PostJPA());
        IUserService userService = new UserService(new UserJPA(), new PermissionJPA());

        add(new ForumPanel("content", parameters, discussionService, topicService, categoryService, postService, userService));

    }

    @Override
    public String getTitle() {
        return "Post page";
    }
}

