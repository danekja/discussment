package org.danekja.discussment.learning.page.article;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.core.dao.jpa.PostDaoJPA;
import org.danekja.discussment.learning.WicketApplication;
import org.danekja.discussment.learning.dao.jpa.ArticleDaoJPA;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.learning.domain.Article;
import org.danekja.discussment.learning.service.ArticleService;
import org.danekja.discussment.learning.service.imp.DefaultArticleService;
import org.danekja.discussment.core.service.imp.DefaultDiscussionService;
import org.danekja.discussment.core.service.imp.DefaultPostService;
import org.danekja.discussment.learning.page.base.BasePage;
import org.danekja.discussment.learning.panel.article.ArticlePanel;

import javax.persistence.EntityManager;


/**
 * Homepage
 */
public class ArticlePage extends BasePage {

    private static final long serialVersionUID = 1L;

    private EntityManager em;

	private static long articleID;

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
    public ArticlePage(final PageParameters parameters) {
        this.em = WicketApplication.factory.createEntityManager();
        this.parameters = parameters;

        this.articleService = new DefaultArticleService(new ArticleDaoJPA(em));
        this.discussionService = new DefaultDiscussionService(new DiscussionDaoJPA(em));
        this.postService = new DefaultPostService(new PostDaoJPA(em));

        this.articleModel = new Model<Article>();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        articleID = (parameters.get("articleId").isNull() ? -1 : Integer.parseInt(parameters.get("articleId").toString()));

        add(new ArticlePanel("content", articleID, articleModel, articleService, discussionService, postService));
    }

    @Override
    public String getTitle() {
        return "Article page";
    }
}
