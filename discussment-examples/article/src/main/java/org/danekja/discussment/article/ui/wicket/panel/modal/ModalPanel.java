package org.danekja.discussment.article.ui.wicket.panel.modal;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.article.core.domain.Article;
import org.danekja.discussment.article.core.service.ArticleService;
import org.danekja.discussment.article.ui.wicket.form.ArticleForm;

/**
 * The class fills modal window with form for creating a new article.
 *
 * Date: 1.2.18
 *
 * @author Jiri Kryda
 */
public class ModalPanel extends Panel {

    private ArticleService articleService;
    private ModalWindow modalWindow;

    /**
     * Constructor for creating a instance of the modal window with form for creating a new article.
     *
     * @param id id of the element into which the panel is inserted
     * @param modalWindow instance of the modal window to contain article form
     * @param articleService instance of the article service
     */
    public ModalPanel(String id, ModalWindow modalWindow, ArticleService articleService){
        super(id);

        this.articleService = articleService;
        this.modalWindow = modalWindow;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form articleForm = new ArticleForm("articleForm", new Model<Article>(new Article()), articleService);

        AjaxButton createArticle = new AjaxButton("createArticle") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form articleForm) {
                modalWindow.close(target);
            }
        };

        modalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback(){
            @Override
            public void onClose(AjaxRequestTarget target)
            {
                setResponsePage(getPage());
            }
        });

        articleForm.add(createArticle);
        add(articleForm);
    }
}
