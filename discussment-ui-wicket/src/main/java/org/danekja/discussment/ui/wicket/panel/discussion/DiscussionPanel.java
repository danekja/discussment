package org.danekja.discussment.ui.wicket.panel.discussion;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Permission;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PermissionService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.form.PostForm;
import org.danekja.discussment.ui.wicket.form.ReplyForm;
import org.danekja.discussment.ui.wicket.list.thread.ThreadListPanel;
import org.danekja.discussment.ui.wicket.model.ThreadWicketModel;


/**
 * Created by Martin Bláha on 29.01.17.
 *
 * The class creates the panel which contains the discussion. This panel can be used below a article like
 * a discussion about the article.
 */
public class DiscussionPanel extends Panel {

    private IModel<Discussion> discussionModel;
    private IModel<Post> selectedPost;

    private PostService postService;
    private PermissionService permissionService;

    /**
     * Constructor for creating the panel which contains the discussion.
     *
     * @param id id of the element into which the panel is inserted
     * @param discussion discussion in the panel
     * @param postService instance of the post service
     * @param selectedPost instance contains the selected post in the discussion
     */
    public DiscussionPanel(String id, IModel<Discussion> discussion, PostService postService, IModel<Post> selectedPost, PermissionService permissionService) {
        super(id);

        this.selectedPost = selectedPost;
        this.postService = postService;
        this.discussionModel = discussion;
        this.permissionService = permissionService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ReplyForm("replyForm", postService, selectedPost, new Model<Post>(new Post())));
        add(new ThreadListPanel("threadPanel", new ThreadWicketModel(postService, discussionModel), selectedPost, postService, permissionService));

        add(createPostForm());
    }

    private PostForm createPostForm() {
        return new PostForm("postForm", postService, discussionModel, new Model<Post>(new Post())) {

            @Override
            protected void onConfigure() {
                super.onConfigure();

                Permission p = permissionService.getCurrentlyLoggedUsersPermission();
                this.setVisible(p != null && p.isCreatePost());
            }
        };
    }
}
