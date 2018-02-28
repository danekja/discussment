package org.danekja.discussment.article.ui.wicket.form.article;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.article.core.domain.Article;

/**
 * The class contains input fields for getting a name and text of the article.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class ArticleFormComponent extends Panel {

    /**
     * Constructor for creating a instance of getting a name and text of the article.
     *
     * @param id id of the element into which the panel is inserted
     * @param articleModel variable contains the article for setting the name and text
     */
    public ArticleFormComponent (String id, IModel<Article> articleModel) { super (id, articleModel);}
    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextField<String> name = new TextField<String>("name", new PropertyModel<String>(getDefaultModel(), "name"));
        name.setRequired(true);
        add(name);

        TextArea<String> articleText = new TextArea<String>("articleText", new PropertyModel<String>(getDefaultModel(), "articleText"));
        articleText.setRequired(true);
        add(articleText);
    }
}
