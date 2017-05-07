package cz.zcu.fav.kiv.discussion.gui.list.panel.thread;

import cz.zcu.fav.kiv.discussion.core.model.PostModel;
import cz.zcu.fav.kiv.discussion.gui.form.panel.reply.ReplyModalFormPanel;
import cz.zcu.fav.kiv.discussion.gui.list.ThreadListView;
import cz.zcu.fav.kiv.discussion.gui.list.panel.post.PostListViewPanel;
import cz.zcu.fav.kiv.discussion.gui.model.PostWicketModel;
import cz.zcu.fav.kiv.discussion.gui.model.ThreadWicketModel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by Martin Bláha on 04.02.17.
 */
public class ThreadListViewPanel extends Panel {

    public ThreadListViewPanel(String id, ThreadWicketModel threadWicketModel, final ReplyModalFormPanel replyModalFormPanel) {
        super(id);


        ThreadListView threadListView = new ThreadListView("threadListView", threadWicketModel){
            @Override
            protected void populateItem(ListItem<PostModel> listItem) {
                super.populateItem(listItem);

                PostModel posts = listItem.getModelObject();

                listItem.add(new PostListViewPanel("postPanel", new PostWicketModel(posts), replyModalFormPanel));

            }
        };
        add(threadListView);

    }
}
