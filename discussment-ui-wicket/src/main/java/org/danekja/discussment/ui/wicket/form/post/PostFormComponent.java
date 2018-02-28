package org.danekja.discussment.ui.wicket.form.post;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.domain.Post;

/**
 * Created by Martin Bláha on 03.02.17.
 *
 * The class contains the input fields for getting a text
 */
public class PostFormComponent extends Panel {

    /**
     * Constructor for creating a instance of getting a text of the post.
     *
     * @param id id of the element into which the panel is inserted
     * @param postModel variable contains the post for setting the text
     */
    public PostFormComponent(String id, IModel<Post> postModel) {
        super(id, postModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        TextArea<String> text = new TextArea<String>("text", new PropertyModel<String>(getDefaultModel(), "text"));
        text.setRequired(true);
        add(text);
    }
    
}
