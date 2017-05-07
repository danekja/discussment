package cz.zcu.fav.kiv.discussion.gui.panel.discussion;

import cz.zcu.fav.kiv.discussion.core.model.UserModel;
import cz.zcu.fav.kiv.discussion.gui.form.panel.post.PostFormPanel;
import cz.zcu.fav.kiv.discussion.gui.form.panel.reply.ReplyModalFormPanel;
import cz.zcu.fav.kiv.discussion.gui.list.panel.thread.ThreadListViewPanel;
import cz.zcu.fav.kiv.discussion.gui.model.ThreadWicketModel;
import org.apache.wicket.markup.html.panel.Panel;


/**
 * Created by Martin Bláha on 29.01.17.
 */
public class DiscussionPanel extends Panel {

    private PostFormPanel postFormPanel;

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        UserModel user = (UserModel) getSession().getAttribute("user");

        if (user != null && user.getPermission().isCreatePost()) {
            postFormPanel.setVisible(true);
        } else {
            postFormPanel.setVisible(false);
        }
    }

    public DiscussionPanel(String id, long discussionId) {
        super(id);

        this.postFormPanel = new PostFormPanel("postForm", discussionId);
        add(postFormPanel);

        ReplyModalFormPanel replyModalFormPanel = new ReplyModalFormPanel("replyForm");
        add(replyModalFormPanel);

        add(new ThreadListViewPanel("threadPanel", new ThreadWicketModel(discussionId), replyModalFormPanel));
    }




}
