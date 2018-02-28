package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.PostReputation;
import org.danekja.discussment.core.domain.UserPostReputation;
import org.danekja.discussment.core.service.PostReputationService;
import org.danekja.discussment.ui.wicket.form.postReputation.PostReputationFormComponent;

/**
 * The class creates the form for making a new vote on post.
 *
 * Date: 18.2.18
 *
 * @author Jiri Kryda
 */
public class PostReputationForm extends Form {

    private PostReputationService postReputationService;
    private DiscussionUserService userService;
    private AccessControlService accessControlService;

    private IModel<Post> postModel;

    /**
     * Constructor for creating a instance of the form for adding the post form
     *
     * @param id id of the element into which the panel is inserted
     * @param postModel model contains the post to add a vote
     * @param userService instance of the user service
     * @param postReputationService instance of the post reputation service
     * @param accessControlService instance of the access control service
     */
    public PostReputationForm (String id,
                               IModel<Post> postModel,
                               DiscussionUserService userService,
                               PostReputationService postReputationService,
                               AccessControlService accessControlService) {
        super(id);

        this.postModel = postModel;

        this.postReputationService = postReputationService;
        this.userService = userService;
        this.accessControlService = accessControlService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new PostReputationFormComponent("postReputationFormComponent", postModel, userService, postReputationService));
    }

    @Override
    protected void onConfigure() {
        if(!accessControlService.canViewPosts(postModel.getObject().getDiscussion())){
            setVisible(false);
        } else if (userService.getCurrentlyLoggedUser().getDiscussionUserId().equals(postModel.getObject().getUserId())){
            setVisible(false);
        }
    }
}
