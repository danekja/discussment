package org.danekja.discussment.example.page.article;

import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.accesscontrol.dao.jpa.PermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.core.dao.jpa.PostDaoJPA;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.core.service.imp.DefaultPostService;
import org.danekja.discussment.core.service.imp.NewDiscussionService;
import org.danekja.discussment.example.core.DefaultUserService;
import org.danekja.discussment.example.core.UserDaoMock;
import org.danekja.discussment.example.page.base.BasePage;
import org.danekja.discussment.ui.wicket.panel.discussion.DiscussionPanel;


/**
 * Homepage
 */
public class ArticlePage extends BasePage {

	private static final long serialVersionUID = 1L;

	private static final long DISCUSSION_ID = 0;

	private DiscussionService discussionService;
	private PostService postService;
	private AccessControlService accessControlService;
	private DiscussionUserService userService;

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public ArticlePage(final PageParameters parameters) {

        this.userService = new DefaultUserService(new UserDaoMock(), new PermissionDaoJPA());
        this.accessControlService = new PermissionService(new PermissionDaoJPA(), userService);
        this.discussionService = new NewDiscussionService(new DiscussionDaoJPA(), accessControlService, userService);
        this.postService = new DefaultPostService(new PostDaoJPA(), userService);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Discussion discussion = discussionService.getDiscussionById(DISCUSSION_ID);

        if (discussion == null) {
            discussion = discussionService.createDiscussion(new Discussion("article name"));
        }

        add(new DiscussionPanel("content", new Model<Discussion>(discussion), postService, new Model<Post>(), accessControlService));
    }

    @Override
    public String getTitle() {
        return "Article page";
    }
}
