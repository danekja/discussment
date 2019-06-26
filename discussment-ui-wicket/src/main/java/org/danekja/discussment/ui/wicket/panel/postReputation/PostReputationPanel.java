package org.danekja.discussment.ui.wicket.panel.postReputation;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.PostReputation;
import org.danekja.discussment.core.service.PostReputationService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.form.PostReputationForm;

/**
 * The class creates the panel which contains likes and dislike for a post and calls a form for making a new vote.
 *
 * Date: 19.2.18
 *
 * @author Jiri Kryda
 */
public class PostReputationPanel extends Panel  {

    private IModel<Post> postModel;
    private IModel<PostReputation> postReputationModel;
    private PostService postService;
    private PostReputationService postReputationService;
    private DiscussionUserService userService;
    private AccessControlService accessControlService;

    /**
     * Constructor for creating the panel which contains the specific article and its discussion.
     *
     * @param id id of the element into which the panel is inserted
     * @param postModel model contains the post to get votes for
     * @param postService instance of the post service
     * @param postReputationService instance of the post reputation service
     * @param userService instance of the user service
     * @param accessControlService instance of the access control service
     */
    public PostReputationPanel(String id,
                               IModel<Post> postModel,
                               PostService postService,
                               PostReputationService postReputationService,
                               DiscussionUserService userService,
                               AccessControlService accessControlService){
        super(id);
        this.postModel = postModel;
        this.postReputationModel = new Model<PostReputation>();

        this.postService = postService;
        this.postReputationService = postReputationService;
        this.userService = userService;
        this.accessControlService = accessControlService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        postReputationModel.setObject(postModel.getObject().getPostReputation());

        add(new Label("likes", new PropertyModel(postReputationModel, "likes")));

        add(new Label("dislikes", new PropertyModel(postReputationModel, "dislikes")));

        add(new PostReputationForm("prform", postModel, postService, postReputationService, userService, accessControlService));

        Label liked = new Label("liked");
        add(liked);

        if (!userService.isGuest() && postReputationService.userVotedOn(userService.getCurrentlyLoggedUser(), postModel.getObject())) {
            if (postReputationService.getVote(userService.getCurrentlyLoggedUser(), postModel.getObject()).getLiked()) {
                liked.setDefaultModel(new ResourceModel("postReputation.liked"));
            } else {
                liked.setDefaultModel(new ResourceModel("postReputation.disliked"));
            }
            liked.setVisible(true);
        } else {
            liked.setVisible(false);
        }
    }

    @Override
    protected void onConfigure(){
        super.onConfigure();

        if(postModel.getObject().isDisabled()) {
            this.setVisible(false);
        }

    }

}
