package org.danekja.discussment.ui.wicket.panel.postReputation;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.PostReputation;
import org.danekja.discussment.core.service.PostReputationService;
import org.danekja.discussment.ui.wicket.form.PostReputationForm;

/**
 * The class creates the panel which contains likes and dislike for a post and calls a form for making a new vote.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class PostReputationPanel extends Panel  {

    private IModel<Post> postModel;
    private IModel<PostReputation> postReputationModel;
    private DiscussionUserService userService;
    private PostReputationService postReputationService;
    private AccessControlService accessControlService;

    /**
     * Constructor for creating the panel which contains the specific article and its discussion.
     *
     * @param id id of the element into which the panel is inserted
     * @param postModel model contains the post to get votes for
     * @param userService instance of the user service
     * @param postReputationService instance of the post reputation service
     * @param accessControlService instance of the access control service
     */
    public PostReputationPanel(String id,
                               IModel<Post> postModel,
                               DiscussionUserService userService,
                               AccessControlService accessControlService,
                               PostReputationService postReputationService){
        super(id);
        this.postModel = postModel;
        this.postReputationModel = new Model<PostReputation>();

        this.userService = userService;
        this.postReputationService = postReputationService;
        this.accessControlService = accessControlService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        postReputationModel.setObject(postReputationService.getPostReputationByPost(postModel.getObject()));

        add(new Label("liketext", ("Likes: ")));
        add(new Label("likes", new PropertyModel(postReputationModel, "likes")));

        add(new Label("disliketext", ("Dislikes: ")));
        add(new Label("dislikes", new PropertyModel(postReputationModel, "dislikes")));

        Form prform = new PostReputationForm("prform", postReputationModel, postReputationService);

        Label liked = new Label("liked");
        liked.setVisible(false);

        if(accessControlService.canViewPosts(postModel.getObject().getDiscussion()) == false){
            prform.setVisible(false);
        } else if (userService.getCurrentlyLoggedUser().getDiscussionUserId().equals(postModel.getObject().getUserId())){
            prform.setVisible(false);
        } else if (postReputationService.userVotedOn(postReputationModel.getObject())){
            prform.setVisible(false);
            if (postReputationService.userLiked(postReputationModel.getObject())){
                liked = new Label("liked", "You LIKED this post.");
                liked.setVisible(true);
            } else {
                liked = new Label("liked", "You DISLIKED this post.");
                liked.setVisible(true);
            }
        }
        add(liked);
        add(prform);
    }

}
