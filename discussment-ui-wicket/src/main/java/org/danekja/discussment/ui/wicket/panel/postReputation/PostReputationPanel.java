package org.danekja.discussment.ui.wicket.panel.postReputation;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.UserPostReputation;
import org.danekja.discussment.core.service.PostReputationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * The class creates the panel which contains likes and dislike for a post and calls a form for making a new vote.
 *
 * Date: 19.2.18
 *
 * @author Jiri Kryda
 */
public class PostReputationPanel extends Panel  {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final IModel<Post> postModel;
    private final PostReputationService postReputationService;
    private final DiscussionUserService userService;

    /**
     * Constructor for creating the panel which contains the specific article and its discussion.
     *
     * @param id id of the element into which the panel is inserted
     * @param postModel model contains the post to get votes for
     * @param postReputationService instance of the post reputation service
     * @param userService instance of the user service
     */
    public PostReputationPanel(String id,
                               IModel<Post> postModel,
                               PostReputationService postReputationService,
                               DiscussionUserService userService){
        super(id);
        this.postModel = postModel;

        this.postReputationService = postReputationService;
        this.userService = userService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        setOutputMarkupId(true);

        AbstractLink likes = new AjaxLink<Post>("likes", postModel) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                PostReputationPanel.this.likePost(getModel());
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                Optional<UserPostReputation> upr = getUserPostReputation(getModel());
                if (upr.isPresent() && upr.get().getLiked()) {
                    this.add(currentUserPostReputationBehavior());
                }
            }
        };
        likes.setOutputMarkupId(true);
        likes.setEnabled(!userService.isGuest() && !userService.getCurrentlyLoggedUser().getDiscussionUserId().equals(postModel.getObject().getUserId()));
        likes.add(new Label("numberOfLikes", new PropertyModel<>(postModel, "postReputation.likes")));
        add(likes);

        AbstractLink dislikes = new AjaxLink<Post>("dislikes", postModel) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                PostReputationPanel.this.dislikePost(getModel());
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                Optional<UserPostReputation> upr = getUserPostReputation(getModel());
                if (upr.isPresent() && !upr.get().getLiked()) {
                    this.add(currentUserPostReputationBehavior());
                }
            }
        };
        dislikes.setOutputMarkupId(true);
        dislikes.setEnabled(!userService.isGuest() && !userService.getCurrentlyLoggedUser().getDiscussionUserId().equals(postModel.getObject().getUserId()));
        dislikes.add(new Label("numberOfDislikes", new PropertyModel<>(postModel, "postReputation.dislikes")));
        add(dislikes);
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        this.setVisible(!postModel.getObject().isDisabled());
    }

    /**
     * Handler for like button.
     *
     * @param post
     */
    public void likePost(IModel<Post> post) {
        try {
            Optional<UserPostReputation> upr = getUserPostReputation(post);
            if (upr.isPresent()) {
                if (upr.get().getLiked()) {
                    postReputationService.removeVote(userService.getCurrentlyLoggedUser(), post.getObject());
                } else {
                    postReputationService.changeVote(userService.getCurrentlyLoggedUser(), post.getObject());
                }
            } else {
                postReputationService.addLike(post.getObject());
            }
        } catch (Exception ex) {
            logger.error("Exception occurred while liking post {}:  {}", post.getObject().getId(), ex);
        }
    }

    /**
     * Handler for dislike button.
     *
     * @param post
     */
    public void dislikePost(IModel<Post> post) {
        try {
            Optional<UserPostReputation> upr = getUserPostReputation(post);
            if (upr.isPresent()) {
                if (!upr.get().getLiked()) {
                    postReputationService.removeVote(userService.getCurrentlyLoggedUser(), post.getObject());
                } else {
                    postReputationService.changeVote(userService.getCurrentlyLoggedUser(), post.getObject());
                }
            } else {
                postReputationService.addDislike(post.getObject());
            }
        } catch (Exception ex) {
            logger.error("Exception occurred while disliking post {}:  {}", post.getObject().getId(), ex);
        }
    }

    /**
     * Find current user post reputation for given post.
     *
     * @param postModel Post.
     * @return Current user post reputation.
     */
    private Optional<UserPostReputation> getUserPostReputation(IModel<Post> postModel) {
        if (userService.isGuest()) {
            return Optional.empty();

        } else {
            final String currentUserId = userService.getCurrentlyLoggedUser().getDiscussionUserId();

            return postModel.getObject().getUserPostReputations().stream()
                    .filter(upr -> currentUserId.equals(upr.getUserId()))
                    .findFirst();
        }
    }

    /**
     * @return Behavior highlighting authenticated user's current post reputation.
     */
    protected static Behavior currentUserPostReputationBehavior() {
        return new AttributeModifier("class", "text-default");
    }
}
