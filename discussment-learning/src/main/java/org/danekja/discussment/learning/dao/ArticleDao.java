package org.danekja.discussment.learning.dao;

import org.danekja.discussment.core.dao.GenericDao;
import org.danekja.discussment.learning.domain.Article;

import java.util.List;

/**
 * The interface extends GenericDao on methods for working with articles in a database
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */

public interface ArticleDao extends GenericDao<Article> {

    /**
     * Get all articles in a database.
     *
     * @return list of Articles
     */

    List<Article> getArticles();

}
