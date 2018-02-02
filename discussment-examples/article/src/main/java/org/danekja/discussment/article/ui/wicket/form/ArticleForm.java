package org.danekja.discussment.article.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.article.core.domain.Article;
import org.danekja.discussment.article.core.service.ArticleService;
import org.danekja.discussment.article.ui.wicket.form.article.ArticleFormComponent;

/**
 * The class creates the form for creating a new article.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class ArticleForm extends Form {

    private ArticleService articleService;
    private IModel<Article> articleModel;

    /**
     * Constructor for creating a instance of the form for adding a new form
     *
     * @param id id of the element into which the panel is inserted
     * @param articleModel model contains the article for setting the form
     */
    public ArticleForm(String id, IModel<Article> articleModel) { this (id, articleModel,null);}

    /**
     * Constructor for creating a instance of the form for adding a new form
     *
     * @param id id of the element into which the panel is inserted
     * @param articleService instance of the article service
     * @param articleModel model contains the article for setting the form
     */
    public ArticleForm(String id, IModel<Article> articleModel, ArticleService articleService){
        super(id);

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
            articleModel.setObject(articleService.createArticle(articleModel.getObject()));
        }
    }
}
