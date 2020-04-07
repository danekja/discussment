package org.danekja.discussment.ui.wicket.panel.discussion;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.configuration.service.ConfigurationService;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostReputationService;
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
    private AccessControlService accessControlService;
    private DiscussionUserService userService;
    private PostReputationService postReputationService;
    private ConfigurationService configurationService;

    /**
     * Constructor for creating the panel which contains the discussion.
     *
     * @param id id of the element into which the panel is inserted
     * @param discussion discussion in the panel
     * @param postService instance of the post service
     * @param selectedPost instance contains the selected post in the discussion
     * @param postReputationService instance of the post reputation service
     * @param accessControlService instance of the access control service
     * @param configurationService instance of the configuration service
     */
    public DiscussionPanel(String id,
                           IModel<Discussion> discussion,
                           IModel<Post> selectedPost,
                           PostService postService,
                           DiscussionUserService userService,
                           PostReputationService postReputationService,
                           AccessControlService accessControlService,
                           ConfigurationService configurationService) {
        super(id);

        this.selectedPost = selectedPost;
        this.postService = postService;
        this.discussionModel = discussion;

        this.accessControlService = accessControlService;
        this.userService = userService;
        this.postReputationService = postReputationService;
        this.configurationService = configurationService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ReplyForm("replyForm", discussionModel, selectedPost, new Model<>(new Post()), postService, accessControlService));
        add(new ThreadListPanel("threadPanel", new ThreadWicketModel(postService, discussionModel), selectedPost, postService, userService, postReputationService, accessControlService, configurationService));
        add(new PostForm("postForm", discussionModel, new Model<Post>(new Post()), postService, accessControlService));
    }
}
