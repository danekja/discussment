package org.danekja.discussment.learning.service.imp;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.learning.dao.ArticleDao;
import org.danekja.discussment.learning.domain.Article;
import org.danekja.discussment.learning.service.ArticleService;
import org.danekja.discussment.core.service.DiscussionService;

import java.util.List;

/**
 * Implementation of the ArticleSevice interface.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class DefaultArticleService implements ArticleService {
    private ArticleDao articleDao;

    public DefaultArticleService(ArticleDao articleDao){ this.articleDao = articleDao; }

    public Article createArticle(Article entity, DiscussionService discussionService) {

        Discussion discussion = discussionService.createDiscussion(new Discussion(entity.getName(), (null)));
        entity.setDiscussion(discussion);

        return articleDao.save(entity);
    }

    public Article getArticleById(long articleId){
        return articleDao.getById(articleId);
    }

    public List<Article> getArticles() {
        return articleDao.getArticles();
    }

    public void removeArticle(Article entity){
        articleDao.remove(entity);
    }
}
