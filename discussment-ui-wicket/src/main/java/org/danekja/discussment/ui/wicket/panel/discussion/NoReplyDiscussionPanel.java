package org.danekja.discussment.ui.wicket.panel.discussion;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.configuration.service.ConfigurationService;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostReputationService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.form.PostForm;
import org.danekja.discussment.ui.wicket.list.thread.ThreadListPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Same as {@link DiscussionPanel} but without the modal reply form. This component can thus be safely used inside
 * another modal window.
 *
 * Note: the reply form is still needed to submit replies. Consumers of this library is expected to initialize it
 * and place it into the page themselves.
 */
public class NoReplyDiscussionPanel extends Panel {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private IModel<Discussion> discussionModel;
    private IModel<Post> selectedPost;

    private final PostService postService;
    private final DiscussionUserService userService;
    private final PostReputationService postReputationService;
    private final ConfigurationService configurationService;

    /**
     * Constructor for creating the panel which contains the discussion.
     *  @param id id of the element into which the panel is inserted
     * @param discussionModel Model of the discussion to be displayed in this panel. It is assumed that the model object
     *                   is complete, that is, it contains all posts and reply chains to be rendered.
     * @param selectedPost instance contains the selected post in the discussion
     * @param postService instance of the post service
     * @param postReputationService instance of the post reputation service
     * @param configurationService instance of the configuration service
     */
    public NoReplyDiscussionPanel(String id,
                                  IModel<Discussion> discussionModel,
                                  IModel<Post> selectedPost,
                                  PostService postService,
                                  DiscussionUserService userService,
                                  PostReputationService postReputationService,
                                  ConfigurationService configurationService) {
        super(id);
        this.discussionModel = discussionModel;
        this.selectedPost = selectedPost;
        this.postService = postService;
        this.userService = userService;
        this.postReputationService = postReputationService;
        this.configurationService = configurationService;
    }

    public String getReplyModalContainerId() {
        return "replyModal";
    }

    /**
     * Sends posts to new discussion using postService and refreshes the page.
     * Override to provide custom implementation.
     *  @param discussion Discussion to send new post to.
     * @param newPost New post content.
     * @param target
     */
    public void sendNewPost(Discussion discussion, Post newPost, AjaxRequestTarget target) {
        try {
            postService.sendPost(discussion, newPost);
        } catch (AccessDeniedException e) {
            this.error(getString("error.accessDenied"));
        } catch (Exception ex) {
            logger.error("Exception occurred when sending new post: ", ex);
        }

        setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ThreadListPanel("threadPanel", new PropertyModel<>(discussionModel, "posts"), selectedPost, postService, userService, postReputationService, configurationService, getReplyModalContainerId()));

        add(new PostForm("postForm", discussionModel, new Model<>(new Post())) {
            @Override
            public void sendNewPost(Discussion discussion, Post post, AjaxRequestTarget target) {
                NoReplyDiscussionPanel.this.sendNewPost(discussion, post, target);
            }
        });
    }
}
