package org.danekja.discussment.learning.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.learning.domain.Article;
import org.danekja.discussment.learning.service.ArticleService;
import org.danekja.discussment.learning.form.article.ArticleFormComponent;

/**
 * The class creates the form for creating a new article.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class ArticleForm extends Form {

    private ArticleService articleService;
    private DiscussionService discussionService;

    private IModel<Article> articleModel;

    /**
     * Constructor for creating a instance of the form for adding a new form
     *
     * @param id id of the element into which the panel is inserted
     * @param articleModel model contains the article for setting the form
     */
    public ArticleForm(String id, IModel<Article> articleModel, DiscussionService discussionService) { this (id, null, articleModel, discussionService);}

    /**
     * Constructor for creating a instance of the form for adding a new form
     *
     * @param id id of the element into which the panel is inserted
     * @param articleService instance of the article service
     * @param articleModel model contains the article for setting the form
     */
    public ArticleForm(String id, ArticleService articleService, IModel<Article> articleModel, DiscussionService discussionService){
        super(id);

        this.discussionService = discussionService;
        this.articleService = articleService;
        this.articleModel = articleModel;
    }

    @Override
    protected void onInitialize(){
        super.onInitialize();

        add(new ArticleFormComponent("articleFormComponent", articleModel));
    }

    public void setArticleService(ArticleService articleService) { this.articleService = articleService; }

    @Override
    protected void onSubmit(){
        if(articleService != null){

            articleService.createArticle(articleModel.getObject(), discussionService);
            articleModel.setObject(new Article());

        }
    }
}
