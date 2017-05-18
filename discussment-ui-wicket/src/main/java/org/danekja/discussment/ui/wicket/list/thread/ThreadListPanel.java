package org.danekja.discussment.ui.wicket.list.thread;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.IPostService;
import org.danekja.discussment.ui.wicket.list.post.PostListPanel;
import org.danekja.discussment.ui.wicket.model.PostWicketModel;
import org.danekja.discussment.ui.wicket.model.ThreadWicketModel;

/**
 * Created by Martin Bláha on 04.02.17.
 */
public class ThreadListPanel extends Panel {

    private ThreadWicketModel threadWicketModel;
    private IModel<Post> selectedPost;
    private IPostService postService;

    public ThreadListPanel(String id, ThreadWicketModel threadWicketModel, IModel<Post> selectedPost, final IPostService postService) {
        super(id);

        this.threadWicketModel = threadWicketModel;
        this.selectedPost = selectedPost;
        this.postService = postService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ListView<Post>("threadListView", threadWicketModel) {
            protected void populateItem(ListItem<Post> listItem) {
                final Post posts = listItem.getModelObject();

                listItem.add(new PostListPanel("postPanel", new PostWicketModel(posts), selectedPost, postService));
            }
        });
    }
}
