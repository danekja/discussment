package org.danekja.discussment.ui.wicket.list.panel.post;

import org.danekja.discussment.ui.wicket.form.panel.reply.ReplyModalFormPanel;
import org.danekja.discussment.ui.wicket.list.PostListView;
import org.danekja.discussment.ui.wicket.model.PostWicketModel;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by Martin Bláha on 04.02.17.
 */
public class PostListViewPanel extends Panel {

    private PostWicketModel postWicketModel;

    public PostListViewPanel(String id, PostWicketModel postWicketModel, ReplyModalFormPanel replyModalFormPanel) {
        super(id);

        this.postWicketModel = postWicketModel;

        add(new PostListView("postListView", postWicketModel, replyModalFormPanel.getReplyForm()));
    }

}
