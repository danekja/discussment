package cz.zcu.fav.kiv.discussion.gui.form.panel.post;

import cz.zcu.fav.kiv.discussion.gui.form.PostForm;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class PostFormPanel extends Panel {

    private PostForm postForm;

    public PostFormPanel(String id, long discussionId) {
        super(id);

        postForm = new PostForm("postForm", discussionId);
        add(postForm);

    }

}
