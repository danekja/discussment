package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.PostReputation;
import org.danekja.discussment.core.service.PostReputationService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.form.post.PostFormComponent;
import org.danekja.discussment.ui.wicket.session.SessionUtil;

/**
 * Created by Martin Bláha on 21.01.17.
 *
 * The class creates the form for creating a new post
 */
public class PostForm extends Form {

    private DiscussionUserService userService;
    private PostService postService;
    private PostReputationService postReputationService;

    private IModel<Discussion> discussionModel;
    private IModel<Post> postModel;

    /**
     * Constructor for creating a instance of a form for adding a post form
     *
     * @param id id of the element into which the panel is inserted
     * @param discussionModel model contains the discussion for adding a new post
     * @param postModel model contains the post for setting the form
     * @param userService instance of the user service
     * @param postReputationService instance of the post reputation service
     * @param postService instance of the post service
     */
    public PostForm(String id,
                    IModel<Discussion> discussionModel,
                    IModel<Post> postModel,
                    DiscussionUserService userService,
                    PostReputationService postReputationService,
                    PostService postService) {
        super(id);

        this.userService = userService;
        this.postService = postService;
        this.postReputationService = postReputationService;

        this.discussionModel = discussionModel;
        this.postModel = postModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new PostFormComponent("postFormComponent", postModel));
    }

    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Override
    protected void onSubmit() {
        postModel.getObject().setUserId(userService.getCurrentlyLoggedUser().getDiscussionUserId());
        try {
            postService.sendPost(discussionModel.getObject(), postModel.getObject());
        } catch (AccessDeniedException e) {
            // todo: not yet implemented
        }
        postModel.setObject(new Post());

        setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
    }
}
