package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
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

    public PostForm(String id, IModel<Discussion> discussionModel) {
        this(id, null, discussionModel);
    }

    public PostForm(String id, PostService postService, IModel<Discussion> discussionModel) {
        super(id);

        this.postService = postService;
        this.discussionModel = discussionModel;

        this.postModel = new Model<Post>();
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

            setResponsePage(getPage());
        }
    }
}
