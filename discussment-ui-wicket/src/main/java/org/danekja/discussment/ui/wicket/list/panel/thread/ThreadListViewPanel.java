package org.danekja.discussment.ui.wicket.list.panel.thread;

import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.ui.wicket.form.panel.reply.ReplyModalFormPanel;
import org.danekja.discussment.ui.wicket.list.ThreadListView;
import org.danekja.discussment.ui.wicket.list.panel.post.PostListViewPanel;
import org.danekja.discussment.ui.wicket.model.PostWicketModel;
import org.danekja.discussment.ui.wicket.model.ThreadWicketModel;
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
            protected void populateItem(ListItem<Post> listItem) {
                super.populateItem(listItem);

                Post posts = listItem.getModelObject();

                listItem.add(new PostListViewPanel("postPanel", new PostWicketModel(posts), replyModalFormPanel));

            }
        };
        add(threadListView);

    }
}
