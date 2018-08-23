package org.danekja.discussment.article.ui.wicket.panel.article;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.Panel;
import org.danekja.discussment.article.core.service.ArticleService;
import org.danekja.discussment.article.core.service.UserService;
import org.danekja.discussment.article.ui.wicket.list.article.ArticleListPanel;
import org.danekja.discussment.article.ui.wicket.model.ArticleWicketModel;
import org.danekja.discussment.article.ui.wicket.panel.modal.ModalPanel;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.service.CategoryService;

/**
 * The class creates the panel which contains the article list. Can be added to a separate page.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class ArticlePanel extends Panel{

    private AccessControlService accessControlService;
    private ArticleService articleService;
    private CategoryService categoryService;
    private UserService userService;

    private ModalWindow modalWindow;

    /**
     * Constructor for creating the panel which contains the article page.
     *
     * @param id id of the element into which the panel is inserted
     * @param articleService instance of the article service
     */
    public ArticlePanel (String id, AccessControlService accessControlService, ArticleService articleService, CategoryService categoryService, UserService userService){
        super(id);

        this.accessControlService = accessControlService;
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.userService = userService;
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
                new ArticleWicketModel(articleService), articleService, categoryService, accessControlService));

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

                IDiscussionUser user = userService.getCurrentlyLoggedUser();
                this.setVisible(user != null && accessControlService.canAddCategory());
            }
        };
    }
}



