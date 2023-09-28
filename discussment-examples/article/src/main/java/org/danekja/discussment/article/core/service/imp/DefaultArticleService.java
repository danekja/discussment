package org.danekja.discussment.article.core.service.imp;

import org.danekja.discussment.article.core.dao.ArticleDao;
import org.danekja.discussment.article.core.domain.Article;
import org.danekja.discussment.article.core.service.ArticleService;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.TopicService;

import java.util.List;

/**
 * Implementation of the ArticleSevice interface.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class DefaultArticleService implements ArticleService {

    private final ArticleDao articleDao;
    private final DiscussionService discussionService;
    private final TopicService topicService;
    private final AccessControlService accessControlService;

    public DefaultArticleService(ArticleDao articleDao, DiscussionService discussionService, TopicService topicService, AccessControlService accessControlService){
        this.articleDao = articleDao;
        this.discussionService = discussionService;
        this.topicService = topicService;
        this.accessControlService = accessControlService;
    }

    @Override
    public Article createArticle(Article entity) {

        try {
            Topic topic = topicService.getDefaultTopic();
            Discussion discussion = discussionService.createDiscussion(topic, new Discussion(entity.getName(), (null)));
            entity.setDiscussion(discussion);
        } catch (AccessDeniedException e) {
            //todo: not yet implemented
        }

        return articleDao.save(entity);
    }

    @Override
    public Article getArticleById(long articleId) {
        return articleDao.getById(articleId);
    }

    @Override
    public List<Article> getArticles() {
        return articleDao.getArticles();
    }

    @Override
    public void removeArticle(Article entity) {
        articleDao.remove(entity);
    }
}
