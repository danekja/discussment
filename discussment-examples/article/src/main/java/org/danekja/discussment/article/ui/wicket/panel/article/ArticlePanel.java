package org.danekja.discussment.article.ui.wicket.panel.article;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.article.ui.wicket.list.article.ArticleListPanel;
import org.danekja.discussment.article.ui.wicket.panel.modal.ModalPanel;
import org.danekja.discussment.article.core.service.ArticleService;
import org.danekja.discussment.article.ui.wicket.model.ArticleWicketModel;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

/**
 * The class creates the panel which contains the article list. Can be added to a separate page.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class ArticlePanel extends Panel{

    private ArticleService articleService;
    private ModalWindow modalWindow;

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

        modalWindow = new ModalWindow("articleModal");
        modalWindow.setContent(new ModalPanel(modalWindow.getContentId(), modalWindow, articleService));
        modalWindow.setMinimalWidth(600);
        modalWindow.setAutoSize(true);
        modalWindow.setCssClassName("style.css");
        add(modalWindow);

        add(createArticleAjaxLink());
        add(new ArticleListPanel("content",
                new ArticleWicketModel(articleService), articleService));

    }

    private AjaxLink createArticleAjaxLink() {
        return new AjaxLink("createArticle") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                modalWindow.show(target);
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isCreateCategory());
            }
        };
    }
}



