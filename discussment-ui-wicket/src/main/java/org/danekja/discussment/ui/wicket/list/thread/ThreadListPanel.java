package org.danekja.discussment.ui.wicket.list.thread;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostReputationService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.list.post.PostListPanel;
import org.danekja.discussment.ui.wicket.model.PostWicketModel;

import java.util.List;

/**
 * Created by Martin Bláha on 04.02.17.
 *
 * The class creates the panel contains threads with the posts
 */
public class ThreadListPanel extends Panel {

    private IModel<List<Post>> threadListModel;
    private IModel<Post> postModel;
    private PostService postService;
    private AccessControlService accessControlService;
    private DiscussionUserService userService;
    private PostReputationService postReputationService;

    /**
     * Constructor for creating a instance of the panel contains the threads with the posts
     *
     * @param id id of the element into which the panel is inserted
     * @param threadListModel model for getting the threads
     * @param postModel model for setting the selected post
     * @param postService instance of the post service
     * @param postReputationService instance of the post reputation service
     * @param accessControlService instance of the access control service
     */
    public ThreadListPanel(String id,
                           IModel<List<Post>> threadListModel,
                           IModel<Post> postModel,
                           PostService postService,
                           DiscussionUserService userService,
                           PostReputationService postReputationService,
                           AccessControlService accessControlService) {
        super(id);

        this.threadListModel = threadListModel;
        this.postModel = postModel;

        this.postService = postService;
        this.accessControlService = accessControlService;
        this.userService = userService;
        this.postReputationService = postReputationService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ListView<Post>("threadListView", threadListModel) {
            protected void populateItem(ListItem<Post> listItem) {
                listItem.add(new PostListPanel("postPanel", new PostWicketModel(listItem.getModel(), postService), postModel, postService, userService,  postReputationService, accessControlService));
            }
        });
    }
}
