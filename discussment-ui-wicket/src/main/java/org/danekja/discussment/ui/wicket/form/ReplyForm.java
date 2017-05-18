package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.IPostService;
import org.danekja.discussment.ui.wicket.form.reply.ReplyFormComponent;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class ReplyForm extends Form {

    private IPostService postService;

    private IModel<Post> postModel;
    private IModel<Post> replyModel;

    public ReplyForm(String id, IModel<Post> postModel) {
        this(id, null, postModel);
    }

    public ReplyForm(String id, IPostService postService, IModel<Post> postModel) {
        super(id);

        this.postService = postService;
        this.postModel = postModel;

        this.replyModel = new Model<Post>();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ReplyFormComponent("replyFormComponent", replyModel));
    }

    public void setPostService(IPostService postService) {
        this.postService = postService;
    }

    @Override
    protected void onSubmit() {


        if (postService != null) {

            Post reply = replyModel.getObject();
            reply.setUser((User) getSession().getAttribute("user"));

            postService.sendReply(reply, postModel.getObject());
        }

        setResponsePage(getPage());
    }
}
