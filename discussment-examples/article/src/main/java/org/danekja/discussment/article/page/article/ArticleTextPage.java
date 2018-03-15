package org.danekja.discussment.article.page.article;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.article.WicketApplication;
import org.danekja.discussment.article.core.dao.jpa.ArticleDaoJPA;
import org.danekja.discussment.article.core.dao.jpa.UserDaoJPA;
import org.danekja.discussment.article.core.domain.Article;
import org.danekja.discussment.article.core.service.ArticleService;
import org.danekja.discussment.article.core.service.UserService;
import org.danekja.discussment.article.core.service.imp.DefaultArticleService;
import org.danekja.discussment.article.core.service.imp.DefaultUserService;
import org.danekja.discussment.article.page.base.BasePage;
import org.danekja.discussment.article.ui.wicket.panel.article.ArticleTextPanel;
import org.danekja.discussment.core.accesscontrol.dao.jpa.PermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
import org.danekja.discussment.core.dao.jpa.*;
import org.danekja.discussment.core.service.*;
import org.danekja.discussment.core.service.imp.*;
import org.danekja.discussment.ui.wicket.panel.notLoggedIn.NotLoggedInPanel;

import javax.persistence.EntityManager;


public class ArticleTextPage extends BasePage {
    private static final long serialVersionUID = 1L;

    private EntityManager em;

    private ArticleService articleService;
    private CategoryService categoryService;
    private DiscussionService discussionService;
    private TopicService topicService;
    private PostService postService;
    private UserService userService;
    private AccessControlService accessControlService;
    private PostReputationService postReputationService;

    private IModel<Article> articleModel;

    final PageParameters parameters;

    /**
     * Constructor that is invoked when page is invoked without a session.
     *
     * @param parameters Page parameters
     */
    public ArticleTextPage(final PageParameters parameters) {
        this.em = WicketApplication.factory.createEntityManager();
        this.parameters = parameters;

        CategoryDaoJPA categoryDaoJPA = new CategoryDaoJPA(em);
        DiscussionDaoJPA discussionDaoJPA = new DiscussionDaoJPA(em);
        TopicDaoJPA topicDaoJPA = new TopicDaoJPA(em);
        PostDaoJPA postDaoJPA = new PostDaoJPA(em);
        UserDaoJPA userDaoJPA = new UserDaoJPA(em);
        PermissionDaoJPA permissionDaoJPA = new PermissionDaoJPA(em);
        ArticleDaoJPA articleDaoJPA = new ArticleDaoJPA(em);
        UserPostReputationDaoJPA userPostReputationDaoJPA = new UserPostReputationDaoJPA(em);

        this.userService = new DefaultUserService(userDaoJPA);
        this.accessControlService = new PermissionService(permissionDaoJPA, userService);
        this.categoryService = new DefaultCategoryService(categoryDaoJPA, accessControlService, userService);
        this.topicService = new DefaultTopicService(topicDaoJPA, categoryService, accessControlService, userService);
        this.discussionService = new DefaultDiscussionService(discussionDaoJPA, postDaoJPA, topicService, accessControlService, userService);
        this.articleService = new DefaultArticleService(articleDaoJPA, discussionService, topicService, accessControlService);
        this.postReputationService = new DefaultPostReputationService(userPostReputationDaoJPA, postDaoJPA, userService, accessControlService);
        this.postService = new DefaultPostService(postDaoJPA, userService, accessControlService);

        this.articleModel = new Model<Article>();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        if(userService.getCurrentlyLoggedUser() == null) {
            add(new NotLoggedInPanel("content"));
        } else {
            try {
                articleModel.setObject(articleService.getArticleById(Integer.parseInt(parameters.get("articleId").toString())));
                add(new ArticleTextPanel("content", articleModel, postService, userService, postReputationService, accessControlService));
            } catch (NumberFormatException e) {
                setResponsePage(ArticlePage.class);
            }
        }
    }

    @Override
    public String getTitle() {
        return "Article text page";
    }
}

