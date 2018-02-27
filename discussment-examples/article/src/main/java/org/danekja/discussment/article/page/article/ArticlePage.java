package org.danekja.discussment.article.page.article;


import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.article.WicketApplication;
import org.danekja.discussment.article.core.dao.jpa.ArticleDaoJPA;
import org.danekja.discussment.article.core.dao.jpa.UserDaoJPA;
import org.danekja.discussment.article.core.service.ArticleService;
import org.danekja.discussment.article.core.service.UserService;
import org.danekja.discussment.article.core.service.imp.DefaultArticleService;
import org.danekja.discussment.article.core.service.imp.DefaultUserService;
import org.danekja.discussment.article.page.base.BasePage;
import org.danekja.discussment.article.ui.wicket.panel.article.ArticlePanel;
import org.danekja.discussment.core.accesscontrol.dao.jpa.PermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.core.dao.jpa.PostDaoJPA;
import org.danekja.discussment.core.dao.jpa.TopicDaoJPA;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.core.service.imp.DefaultDiscussionService;
import org.danekja.discussment.core.service.imp.DefaultTopicService;
import org.danekja.discussment.ui.wicket.panel.notLoggedIn.NotLoggedInPanel;

import javax.persistence.EntityManager;


/**
 * The page for creating and viewing articles.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class ArticlePage extends BasePage {

    private static final long serialVersionUID = 1L;

    private EntityManager em;

    private UserService userService;
	private ArticleService articleService;
	private DiscussionService discussionService;
	private TopicService topicService;
	private AccessControlService accessControlService;

    final PageParameters parameters;

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters Page parameters
	 */
    public ArticlePage(final PageParameters parameters) {
        this.em = WicketApplication.factory.createEntityManager();
        this.parameters = parameters;

        UserDaoJPA userDaoJPA = new UserDaoJPA(em);
        PermissionDaoJPA permissionJPA = new PermissionDaoJPA(em);
        PostDaoJPA postJPA = new PostDaoJPA(em);
        TopicDaoJPA topicDaoJPA = new TopicDaoJPA(em);
        DiscussionDaoJPA discussionDaoJPA = new DiscussionDaoJPA(em);
        ArticleDaoJPA articleDaoJPA = new ArticleDaoJPA(em);

        this.userService = new DefaultUserService(userDaoJPA);
        this.accessControlService = new PermissionService(permissionJPA, userService);
        this.discussionService = new DefaultDiscussionService(discussionDaoJPA, postJPA, accessControlService, userService);
        this.topicService = new DefaultTopicService(topicDaoJPA, accessControlService, userService);
        this.articleService = new DefaultArticleService(articleDaoJPA, discussionService, topicService, accessControlService);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        if(userService.getCurrentlyLoggedUser() == null){
            add(new NotLoggedInPanel("content"));
        }else {
            add(new ArticlePanel("content", accessControlService, articleService));
        }
    }

    @Override
    public String getTitle() {
        return "Article page";
    }
}
