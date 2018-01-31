package org.danekja.discussment.learning.list.ArticleContent;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.learning.domain.Article;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.learning.service.ArticleService;
import org.danekja.discussment.learning.list.article.ArticleListPanel;

import java.util.List;

/**
 * The class creates the panel that contains list of articles and a link for creating new ones.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class ArticleContentListPanel extends Panel{

    private ArticleService articleService;
    private IModel<List<Article>> articleListModel;


    /**
     * Constructor for creating a instance of the panel contains the list of articles and a link for creating new ones.
     *
     * @param id id of the element into which the panel is inserted
     * @param articleListModel model for getting the articles
     * @param articleService instance of the article service
     */
    public ArticleContentListPanel (String id, IModel<List<Article>> articleListModel, ArticleService articleService){
        super(id);

        this.articleListModel = articleListModel;
        this.articleService = articleService;
    }
    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(createArticleAjaxLink());
        add(new ArticleListPanel("articlePanel", articleListModel, articleService));
    }

    private AjaxLink createArticleAjaxLink() {
        return new AjaxLink("createArticle") {
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {}

            @Override
            protected void onConfigure() {
                super.onConfigure();

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isCreateCategory());
            }
        };
    }
}
