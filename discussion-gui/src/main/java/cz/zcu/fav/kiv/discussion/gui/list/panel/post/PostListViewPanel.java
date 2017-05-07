package cz.zcu.fav.kiv.discussion.gui.list.panel.post;

import cz.zcu.fav.kiv.discussion.gui.form.panel.reply.ReplyModalFormPanel;
import cz.zcu.fav.kiv.discussion.gui.list.PostListView;
import cz.zcu.fav.kiv.discussion.gui.model.PostWicketModel;
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
