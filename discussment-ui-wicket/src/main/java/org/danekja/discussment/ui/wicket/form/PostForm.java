package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.form.post.PostFormComponent;

/**
 * Created by Martin Bláha on 21.01.17.
 *
 * The class creates the form for creating a new post
 */
public class PostForm extends Form {

    private PostService postService;

    private IModel<Discussion> discussionModel;
    private IModel<Post> postModel;

    /**
     * Constructor for creating a instance of the form for adding the post form
     *
     * @param id id of the element into which the panel is inserted
     * @param discussionModel model contains the discussion for adding a new post
     * @param postModel model contains the post for setting a form
     */
    public PostForm(String id, IModel<Discussion> discussionModel, IModel<Post> postModel) {
        this(id, null, discussionModel, postModel);
    }

    /**
     * Constructor for creating a instance of a form for adding a post form
     *
     * @param id id of the element into which the panel is inserted
     * @param postService instance of the post service
     * @param discussionModel model contains the discussion for adding a new post
     * @param postModel model contains the post for setting the form
     */
    public PostForm(String id, PostService postService, IModel<Discussion> discussionModel, IModel<Post> postModel) {
        super(id);

        this.postService = postService;
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

        if (postService != null) {
            Post post = postModel.getObject();
            post.setUser((User) getSession().getAttribute("user"));

            postService.sendPost(
                    discussionModel.getObject(),
                    post
            );

            postModel.setObject(new Post());

            setResponsePage(getPage());
        }
    }
}
