package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostReputationService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.form.post.PostFormComponent;

/**
 * Created by Martin Bláha on 21.01.17.
 *
 * The class creates the form for creating a new post
 */
public class PostForm extends Form {

    private PostService postService;
    private AccessControlService accessControlService;

    private IModel<Discussion> discussionModel;
    private IModel<Post> postModel;

    /**
     * Constructor for creating a instance of a form for adding a post form
     *
     * @param id id of the element into which the panel is inserted
     * @param discussionModel model contains the discussion for adding a new post
     * @param postModel model contains the post for setting the form
     * @param postService instance of the post service
     * @param accessControlService instance of the access control service
     */
    public PostForm(String id,
                    IModel<Discussion> discussionModel,
                    IModel<Post> postModel,
                    PostService postService,
                    AccessControlService accessControlService) {
        super(id);

        this.postService = postService;
        this.accessControlService = accessControlService;

        this.discussionModel = discussionModel;
        this.postModel = postModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new PostFormComponent("postFormComponent", postModel));
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        this.setVisible(accessControlService.canAddPost(discussionModel.getObject()));
    }

    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Override
    protected void onSubmit() {
        try {
            postService.sendPost(discussionModel.getObject(), postModel.getObject());
        } catch (AccessDeniedException e) {
            // todo: not yet implemented
        }

        postModel.setObject(new Post());

        setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
    }
}
