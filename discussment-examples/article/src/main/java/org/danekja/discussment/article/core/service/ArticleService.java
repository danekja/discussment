package org.danekja.discussment.article.core.service;

import org.danekja.discussment.article.core.domain.Article;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;

import java.util.List;

/**
 * The interface contains the service methods for working with the articles.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public interface ArticleService {


    /**
     * Creates a new article and its discussion.
     *
     * @param entity new article
     * @return new article
     */
    Article createArticle(Article entity);

    /**
     * Get an article based on its id.
     *
     * @param articleId article id
     * @return article by id
     */
    Article getArticleById(long articleId);

    /**
     * Get all articles
     *
     * @return list of Articles
     */
    List<Article> getArticles();

    /**
     * Remove an article
     *
     * @param entity article to remove
     */
    void removeArticle(Article entity) throws AccessDeniedException;
}
