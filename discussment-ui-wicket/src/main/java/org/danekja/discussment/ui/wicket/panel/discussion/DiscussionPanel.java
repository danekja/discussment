package org.danekja.discussment.ui.wicket.panel.discussion;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.ui.wicket.form.panel.post.PostFormPanel;
import org.danekja.discussment.ui.wicket.form.panel.reply.ReplyModalFormPanel;
import org.danekja.discussment.ui.wicket.list.panel.thread.ThreadListViewPanel;
import org.danekja.discussment.ui.wicket.model.ThreadWicketModel;
import org.apache.wicket.markup.html.panel.Panel;


/**
 * Created by Martin Bláha on 29.01.17.
 */
public class DiscussionPanel extends Panel {

    private PostFormPanel postFormPanel;

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        User user = (User) getSession().getAttribute("user");

        if (user != null && user.getPermissions().isCreatePost()) {
            postFormPanel.setVisible(true);
        } else {
            postFormPanel.setVisible(false);
        }
    }

    public DiscussionPanel(String id, Discussion discussion) {
        super(id);

        this.postFormPanel = new PostFormPanel("postForm", discussion);
        add(postFormPanel);

        ReplyModalFormPanel replyModalFormPanel = new ReplyModalFormPanel("replyForm");
        add(replyModalFormPanel);

        add(new ThreadListViewPanel("threadPanel", new ThreadWicketModel(discussion), replyModalFormPanel));
    }




}
