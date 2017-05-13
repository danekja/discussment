package org.danekja.discussment.ui.wicket.form.panel.post;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.ui.wicket.form.PostForm;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class PostFormPanel extends Panel {

    private PostForm postForm;

    public PostFormPanel(String id, Discussion discussion) {
        super(id);

        postForm = new PostForm("postForm", discussion);
        add(postForm);

    }

}
