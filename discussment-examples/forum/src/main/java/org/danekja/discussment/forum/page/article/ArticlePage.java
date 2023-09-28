package org.danekja.discussment.forum.page.article;

import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.accesscontrol.dao.jpa.PermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
import org.danekja.discussment.core.dao.jpa.*;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.*;
import org.danekja.discussment.core.service.imp.*;
import org.danekja.discussment.forum.WicketApplication;
import org.danekja.discussment.forum.core.dao.jpa.UserDaoJPA;
import org.danekja.discussment.forum.core.service.UserService;
import org.danekja.discussment.forum.core.service.imp.DefaultUserService;
import org.danekja.discussment.forum.page.base.BasePage;
import org.danekja.discussment.ui.wicket.panel.accessDenied.AccessDeniedPanel;
import org.danekja.discussment.ui.wicket.panel.discussion.DiscussionPanel;
import org.danekja.discussment.ui.wicket.panel.notLoggedIn.NotLoggedInPanel;

import javax.persistence.EntityManager;


/**
 * Homepage
 */
public class ArticlePage extends BasePage {

	private static final long serialVersionUID = 1L;

    private final EntityManager em;

    private final CategoryService categoryService;
    private final TopicService topicService;
    private final DiscussionService discussionService;
    private final PostService postService;
    private final AccessControlService accessControlService;
    private final UserService userService;
    private final PostReputationService postReputationService;

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public ArticlePage(final PageParameters parameters) {
        this.em = WicketApplication.factory.createEntityManager();

        this.userService = new DefaultUserService(new UserDaoJPA(em));
        this.accessControlService = new PermissionService(new PermissionDaoJPA(em), userService);
        this.postReputationService = new DefaultPostReputationService(new UserPostReputationDaoJPA(em), new PostDaoJPA(em), userService, accessControlService);
        this.categoryService = new DefaultCategoryService(new CategoryDaoJPA(em), accessControlService, userService);
        this.topicService = new DefaultTopicService(new TopicDaoJPA(em), categoryService, accessControlService, userService);
        this.discussionService = new DefaultDiscussionService(new DiscussionDaoJPA(em), new PostDaoJPA(em), topicService, accessControlService, userService);
        this.postService = new DefaultPostService(new PostDaoJPA(em), userService, accessControlService);

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        try {
            Discussion discussion = discussionService.getDefaultDiscussion();
            if (discussion == null) {
                Topic topic = topicService.getDefaultTopic();
                discussion = discussionService.createDiscussion(topic, new Discussion("article name"));
            }
            add(new DiscussionPanel("content", new Model<Discussion>(discussion), new Model<Post>(), postService, userService, postReputationService, accessControlService));
        } catch (AccessDeniedException e) {
            add(new AccessDeniedPanel("content"));
        } catch (NullPointerException ex) {
            add(new NotLoggedInPanel("content"));
        }
    }

    @Override
    public String getTitle() {
        return "Article page";
    }
}
