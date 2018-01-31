package org.danekja.discussment.learning.panel.article;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.learning.domain.Article;
import org.danekja.discussment.learning.service.ArticleService;
import org.danekja.discussment.learning.form.ArticleForm;
import org.danekja.discussment.learning.list.ArticleContent.ArticleContentListPanel;
import org.danekja.discussment.learning.model.ArticleWicketModel;

/**
 * The class creates the panel which contains the article list. Can be added to a separate page.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class ArticlePanel extends Panel{

    private ArticleService articleService;

    /**
     * Constructor for creating the panel which contains the article page.
     *
     * @param id id of the element into which the panel is inserted
     * @param articleService instance of the article service
     */
    public ArticlePanel (String id,  ArticleService articleService){
        super(id);

        this.articleService = articleService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ArticleForm("articleForm", new Model<Article>(new Article()), articleService));
        add(new ArticleContentListPanel("content",
                new ArticleWicketModel(articleService), articleService));
    }
}


