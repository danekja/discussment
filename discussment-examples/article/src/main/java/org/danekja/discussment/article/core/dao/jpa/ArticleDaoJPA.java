package org.danekja.discussment.article.core.dao.jpa;

import org.danekja.discussment.core.dao.jpa.GenericDaoJPA;
import org.danekja.discussment.article.core.dao.ArticleDao;
import org.danekja.discussment.article.core.domain.Article;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * JPA implementation of the UserDao interface.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class ArticleDaoJPA extends GenericDaoJPA<Article> implements ArticleDao{

    /**
     * @param em entity manager to be used by this dao
     */
    public ArticleDaoJPA(EntityManager em) {
        super(Article.class, em);
    }

    public List<Article> getArticles() {
        TypedQuery<Article> q = em.createNamedQuery(Article.GET_ARTICLES, Article.class);
        return q.getResultList();
    }
}
