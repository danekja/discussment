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
    private final long TOPIC_ID = 1;

    private ArticleDao articleDao;
    private DiscussionService discussionService;
    private TopicService topicService;
    private AccessControlService accessControlService;

    public DefaultArticleService(ArticleDao articleDao, DiscussionService discussionService, TopicService topicService, AccessControlService accessControlService){
        this.articleDao = articleDao;
        this.discussionService = discussionService;
        this.topicService = topicService;
        this.accessControlService = accessControlService;
    }

    public Article createArticle(Article entity) {

        try {
            Topic topic = topicService.getTopicById(TOPIC_ID);
            Discussion discussion = discussionService.createDiscussion(topic, new Discussion(entity.getName(), (null)));
            entity.setDiscussion(discussion);
        } catch (AccessDeniedException e) {
            //todo: not yet implemented
        }

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
