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
    private IModel<Post> postModel;

    /**
     * Constructor for creating a instance of the form for adding the post form
     *
     * @param id id of the element into which the panel is inserted
     * @param postModel model contains the post to add a vote
     * @param postReputationService instance of the post reputation service
     */
    public PostReputationForm (String id, IModel<Post> postModel, PostReputationService postReputationService) {
        super(id);

        this.postModel = postModel;
        this.postReputationService = postReputationService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new PostReputationFormComponent("postReputationFormComponent", postModel, postReputationService));
    }
}
