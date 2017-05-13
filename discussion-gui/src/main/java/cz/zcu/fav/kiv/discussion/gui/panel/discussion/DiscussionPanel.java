package cz.zcu.fav.kiv.discussion.gui.panel.discussion;

import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;
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

        UserEntity user = (UserEntity) getSession().getAttribute("user");

        if (user != null && user.getPermissions().isCreatePost()) {
            postFormPanel.setVisible(true);
        } else {
            postFormPanel.setVisible(false);
        }
    }

    public DiscussionPanel(String id, DiscussionEntity discussionEntity) {
        super(id);

        this.postFormPanel = new PostFormPanel("postForm", discussionEntity);
        add(postFormPanel);

        ReplyModalFormPanel replyModalFormPanel = new ReplyModalFormPanel("replyForm");
        add(replyModalFormPanel);

        add(new ThreadListViewPanel("threadPanel", new ThreadWicketModel(discussionEntity), replyModalFormPanel));
    }




}
