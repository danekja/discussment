package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.ui.wicket.form.post.PostFormComponent;

/**
 * Created by Martin Bláha on 21.01.17.
 *
 * The class creates the form for creating a new reply of the post
 */
public abstract class ReplyForm extends Form {

    private IModel<Post> postModel;
    private IModel<Post> replyModel;

    /**
     * Constructor for creating a instance of the form for creating a new reply of the post
     *
     * @param id id of the element into which the panel is inserted
     * @param postModel model contains the post for adding a new post
     * @param replyModel model contains the reply for setting the form
     */
    public ReplyForm(String id,
                     IModel<Post> postModel,
                     IModel<Post> replyModel) {
        super(id);
        this.postModel = postModel;
        this.replyModel = replyModel;
    }

    /**
     * Handler called when this form is submitted.
     *
     * @param parentPost Parent of the new reply.
     * @param reply Content of the new reply.
     */
    public abstract void replyToPost(Post parentPost, Post reply);

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new PostFormComponent("replyFormComponent", replyModel));
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
    }

    @Override
    protected void onSubmit() {
        replyToPost(postModel.getObject(), replyModel.getObject());
        replyModel.setObject(new Post());
    }
}
