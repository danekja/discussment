package org.danekja.discussment.article.page.article;


import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.article.WicketApplication;
import org.danekja.discussment.article.core.dao.jpa.ArticleDaoJPA;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.article.core.service.ArticleService;
import org.danekja.discussment.article.core.service.imp.DefaultArticleService;
import org.danekja.discussment.core.service.imp.DefaultDiscussionService;
import org.danekja.discussment.article.page.base.BasePage;
import org.danekja.discussment.article.ui.wicket.panel.article.ArticlePanel;

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

	private ArticleService articleService;
	private DiscussionService discussionService;

    final PageParameters parameters;

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters Page parameters
	 */
    public ArticlePage(final PageParameters parameters) {
        this.em = WicketApplication.factory.createEntityManager();
        this.parameters = parameters;

        this.discussionService = new DefaultDiscussionService(new DiscussionDaoJPA(em));
        this.articleService = new DefaultArticleService(new ArticleDaoJPA(em), discussionService);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new ArticlePanel("content", articleService));
    }

    @Override
    public String getTitle() {
        return "Article page";
    }
}
