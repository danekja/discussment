package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.ui.wicket.form.postReputation.PostReputationFormComponent;
import org.danekja.discussment.ui.wicket.panel.postReputation.PostReputationPanel;

/**
 * The class creates the form for making a new vote on post.
 *
 * Date: 18.2.18
 *
 * @author Jiri Kryda
 */
public class PostReputationForm extends Form {

    private DiscussionUserService userService;

    private PostReputationPanel parent;

    private IModel<Post> postModel;

    /**
     * Constructor for creating a instance of the form for adding the post form
     *  @param id id of the element into which the panel is inserted
     * @param parent Parent component used for calling handlers of like actions.
     * @param postModel model contains the post to add a vote
     * @param userService instance of the user service
     */
    public PostReputationForm(String id,
                              PostReputationPanel parent, IModel<Post> postModel,
                              DiscussionUserService userService) {
        super(id);

        this.postModel = postModel;
        this.parent = parent;
        this.userService = userService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new PostReputationFormComponent("postReputationFormComponent", parent, postModel, userService));
    }

    @Override
    protected void onConfigure() {
        IDiscussionUser currentUser = userService.getCurrentlyLoggedUser();
        setVisible(currentUser != null && postModel.getObject().getUserId().equals(currentUser.getDiscussionUserId()));
    }
}
