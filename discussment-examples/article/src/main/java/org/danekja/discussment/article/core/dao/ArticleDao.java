package org.danekja.discussment.article.core.dao;

import org.danekja.discussment.article.core.domain.Article;
import org.danekja.discussment.core.dao.GenericDao;

import java.util.List;

/**
 * The interface extends GenericDao on methods for working with articles in a database
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public interface ArticleDao extends GenericDao<Long, Article> {

    /**
     * Get all articles in a database.
     *
     * @return list of Articles
     */
    List<Article> getArticles();

}
