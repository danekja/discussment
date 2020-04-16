package org.danekja.discussment.ui.wicket.form.postReputation;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.ui.wicket.panel.postReputation.PostReputationPanel;

/**
 * The class contains input fields for making a new vote on post.
 *
 * Date: 18.2.18
 *
 * @author Jiri Kryda
 */
public class PostReputationFormComponent extends Panel implements IEventSource {

    private IModel<Post> postModel;

    private PostReputationPanel parent;

    private DiscussionUserService userService;

    /**
     * Constructor for creating a instance of getting a name and text of the article.
     *  @param id id of the element into which the panel is inserted
     * @param parent Parent component used to call handlers of reputation actions (like/dislike/change vote).
     * @param postModel model contains the post to add a vote
     * @param userService instance of the user service
     */
    public PostReputationFormComponent(String id, PostReputationPanel parent, IModel<Post> postModel, DiscussionUserService userService) {
        super(id);
        this.postModel = postModel;
        this.parent = parent;
        this.userService = userService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        AjaxButton addLike = new AjaxButton("addLike") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form prform)
            {
                parent.likePost(postModel);
            }

            @Override
            protected void onConfigure(){
                this.setVisible(currentUserVoteStatus(false));
            }
        };

        AjaxButton addDislike = new AjaxButton("addDislike") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form prform)
            {
                parent.dislikePost(postModel);
            }

            @Override
            protected void onConfigure(){
                this.setVisible(currentUserVoteStatus(false));
            }
        };

        AjaxButton changeVote = new AjaxButton("changeVote") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form prform)
            {
                parent.changePostVote(postModel);
            }

            @Override
            protected void onConfigure(){
                this.setVisible(currentUserVoteStatus(true));
            }
        };

        add(addLike);
        add(addDislike);
        add(changeVote);
    }


    private boolean currentUserVoteStatus(boolean voted) {
        if (!userService.isGuest()) {
            IDiscussionUser currentUser = userService.getCurrentlyLoggedUser();
            if (voted) {
                return postModel.getObject().getUserPostReputations().stream().anyMatch(pr -> pr.getUserId().equals(currentUser.getDiscussionUserId()));
            } else {
                return postModel.getObject().getUserPostReputations().stream().noneMatch(pr -> pr.getUserId().equals(currentUser.getDiscussionUserId()));
            }
        } else {
            return false;
        }
    }
}
