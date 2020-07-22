package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.ui.wicket.form.post.PostFormComponent;

/**
 * Created by Martin Bláha on 21.01.17.
 *
 * The class creates the form for creating a new post
 */
public abstract class PostForm extends Form {

    private IModel<Discussion> discussionModel;
    private IModel<Post> postModel;
    private PostFormComponent postFormComponent;

    /**
     * Constructor for creating a instance of a form for adding a post form
     *
     * @param id id of the element into which the panel is inserted
     * @param discussionModel model contains the discussion for adding a new post
     * @param postModel model contains the post for setting the form
     */
    public PostForm(String id,
                    IModel<Discussion> discussionModel,
                    IModel<Post> postModel) {
        super(id);


        this.discussionModel = discussionModel;
        this.postModel = postModel;
    }

    /**
     * Handler called when this form gets submitted.
     *
     * @param discussion Discussion to send new post to.
     * @param post New post content.
     */
    protected abstract void sendNewPost(Discussion discussion, Post post);

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.postFormComponent = new PostFormComponent("postFormComponent", postModel);
        postFormComponent.setOutputMarkupId(true);

        add(postFormComponent);
        add(new AjaxFormSubmitBehavior(this, "onclick") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);
                sendNewPost(discussionModel.getObject(), postModel.getObject());
                postModel.setObject(new Post());
                target.add(postFormComponent);
            }
        });
    }
}
