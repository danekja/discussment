package org.danekja.discussment.article.page.article;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.core.dao.jpa.PostDaoJPA;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.core.service.imp.DefaultDiscussionService;
import org.danekja.discussment.core.service.imp.DefaultPostService;
import org.danekja.discussment.article.WicketApplication;
import org.danekja.discussment.article.core.dao.jpa.ArticleDaoJPA;
import org.danekja.discussment.article.core.domain.Article;
import org.danekja.discussment.article.page.base.BasePage;
import org.danekja.discussment.article.ui.wicket.panel.article.ArticleTextPanel;
import org.danekja.discussment.article.core.service.ArticleService;
import org.danekja.discussment.article.core.service.imp.DefaultArticleService;

import javax.persistence.EntityManager;


public class ArticleTextPage extends BasePage {
    private static final long serialVersionUID = 1L;

    private EntityManager em;

    private ArticleService articleService;
    private DiscussionService discussionService;
    private PostService postService;

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

        this.discussionService = new DefaultDiscussionService(new DiscussionDaoJPA(em));
        this.articleService = new DefaultArticleService(new ArticleDaoJPA(em), discussionService);
        this.postService = new DefaultPostService(new PostDaoJPA(em));

        this.articleModel = new Model<Article>();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        articleModel.setObject(articleService.getArticleById(Integer.parseInt(parameters.get("articleId").toString())));
        add(new ArticleTextPanel("content", articleModel, postService));
    }

    @Override
    public String getTitle() {
        return "Article text page";
    }
}

