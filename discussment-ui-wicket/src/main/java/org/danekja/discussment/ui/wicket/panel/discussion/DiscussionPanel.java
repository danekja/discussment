package org.danekja.discussment.ui.wicket.panel.discussion;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.configuration.service.ConfigurationService;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.exception.MaxReplyLevelExceeded;
import org.danekja.discussment.core.service.PostReputationService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.form.PostForm;
import org.danekja.discussment.ui.wicket.form.ReplyForm;
import org.danekja.discussment.ui.wicket.list.thread.ThreadListPanel;


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
     * @param discussion Model of the discussion to be displayed in this panel. It is assumed that the model object
     *                   is complete, that is, it contains all posts and reply chains to be rendered.
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

    /**
     * Sends reply to post using postService and refreshes the page.
     * Override to provide custom implementation.
     *
     * @param parentPost Parent post of the reply.
     * @param reply Reply content.
     */
    public void replyToPost(Post parentPost, Post reply) {
        try {
            postService.sendReply(reply, parentPost);
        } catch (MaxReplyLevelExceeded e) {
            this.error(getString("error.maxReplyLevelExceeded"));
        } catch (AccessDeniedException e) {
            this.error(getString("error.accessDenied"));
        }

        setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
    }

    /**
     * Sends posts to new discussion using postService and refreshes the page.
     * Override to provide custom implementation.
     *
     * @param discussion Discussion to send new post to.
     * @param newPost New post content.
     */
    public void sendNewPost(Discussion discussion, Post newPost) {
        try {
            postService.sendPost(discussion, newPost);
        } catch (AccessDeniedException e) {
            this.error(getString("error.accessDenied"));
        }

        setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ReplyForm("replyForm", discussionModel, selectedPost, new Model<>(new Post()), postService, accessControlService) {
            @Override
            public void replyToPost(Post parentPost, Post reply) {
                DiscussionPanel.this.replyToPost(parentPost, reply);
            }
        });
        add(new ThreadListPanel("threadPanel", new PropertyModel<>(discussionModel, "posts"), selectedPost, postService, userService, postReputationService, accessControlService, configurationService));

        add(new PostForm("postForm", discussionModel, new Model<>(new Post())) {
            @Override
            public void sendNewPost(Discussion discussion, Post post) {
                DiscussionPanel.this.sendNewPost(discussion, post);
            }
        });
    }
}
