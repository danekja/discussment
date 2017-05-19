package org.danekja.discussment.ui.wicket.panel.discussion;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.form.PostForm;
import org.danekja.discussment.ui.wicket.form.ReplyForm;
import org.danekja.discussment.ui.wicket.list.thread.ThreadListPanel;
import org.danekja.discussment.ui.wicket.model.ThreadWicketModel;


/**
 * Created by Martin Bláha on 29.01.17.
 */
public class DiscussionPanel extends Panel {

    private IModel<Discussion> discussionModel;
    private IModel<Post> selectedPost;

    private PostService postService;

    public DiscussionPanel(String id, IModel<Discussion> discussion, PostService postService, IModel<Post> selectedPost) {
        super(id);

        this.selectedPost = selectedPost;
        this.postService = postService;
        this.discussionModel = discussion;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ReplyForm("replyForm", postService, selectedPost, new Model<Post>(new Post())));
        add(new ThreadListPanel("threadPanel", new ThreadWicketModel(postService, discussionModel), selectedPost, postService));

        add(createPostForm());
    }

    private PostForm createPostForm() {
        return new PostForm("postForm", postService, discussionModel) {

            @Override
            protected void onConfigure() {
                super.onConfigure();

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isCreatePost());
            }
        };
    }
}
