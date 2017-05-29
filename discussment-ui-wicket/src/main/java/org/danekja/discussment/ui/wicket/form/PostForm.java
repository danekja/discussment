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
 */
public class PostForm extends Form {

    private PostService postService;

    private IModel<Discussion> discussionModel;
    private IModel<Post> postModel;

    PostFormComponent postFormComponent;

    public PostForm(String id, IModel<Discussion> discussionModel, IModel<Post> postModel) {
        this(id, null, discussionModel, postModel);
    }

    public PostForm(String id, PostService postService, IModel<Discussion> discussionModel, IModel<Post> postModel) {
        super(id);

        this.postService = postService;
        this.discussionModel = discussionModel;
        this.postModel = postModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        postFormComponent = new PostFormComponent("postFormComponent", postModel);
        add(postFormComponent);
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
