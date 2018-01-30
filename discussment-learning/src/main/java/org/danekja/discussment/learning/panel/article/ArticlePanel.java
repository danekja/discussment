package org.danekja.discussment.learning.panel.article;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.learning.domain.Article;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.learning.service.ArticleService;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.learning.form.ArticleForm;
import org.danekja.discussment.learning.list.ArticleContent.ArticleContentListPanel;
import org.danekja.discussment.learning.model.ArticleWicketModel;

/**
 * The class creates the panel which contains the article page. Can be added to a separate page.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class ArticlePanel extends Panel{

    private ArticleService articleService;
    private DiscussionService discussionService;
    private PostService postService;
    private long articleId;

    private IModel<Article> articleModel;
    private IModel<Discussion> discussionModel;

    /**
     * Constructor for creating the panel which contains the article page.
     *
     * @param id id of the element into which the panel is inserted
     * @param articleId id of the article
     * @param article article in the panel
     * @param articleService instance of the article service
     * @param discussionService instance of the discussion service
     * @param postService instance of the post service
     */
    public ArticlePanel (String id, Long articleId, IModel<Article> article,  ArticleService articleService, DiscussionService discussionService, PostService postService){
        super(id);

        this.articleId = articleId;

        this.articleService = articleService;
        this.discussionService = discussionService;
        this.postService = postService;

        this.articleModel = article;
        this.discussionModel = new Model<Discussion>();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ArticleForm("articleForm", articleService, new Model<Article>(new Article()), discussionService));

        if (articleId == -1){
            add(new ArticleContentListPanel("content",
                    new ArticleWicketModel(articleService), articleService, articleModel)
            );
        } else {
            Article article = articleService.getArticleById(articleId);
            articleModel.setObject(article);

            Discussion discussion = discussionService.getDiscussionById(article.getDiscussion().getId());

            add(new ArticleTextPanel("content", articleModel, new Model<Discussion>(discussion), postService));

        }
    }
}

