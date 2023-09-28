package org.danekja.discussment.article.ui.wicket.model;

import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.article.core.domain.Article;
import org.danekja.discussment.article.core.service.ArticleService;

import java.util.List;

/**
 * The class for getting the articles via the article service.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class ArticleWicketModel extends LoadableDetachableModel<List<Article>> {

    private final ArticleService articleService;

    /**
     * Constructor for creating a instance of getting the articles.
     *
     * @param articleService instance of the category article
     */
    public ArticleWicketModel(ArticleService articleService){ this.articleService = articleService; }

    @Override
    protected List<Article> load(){ return articleService.getArticles(); }
}
