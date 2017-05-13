package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.IPostService;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class ReplyForm extends Form {

    private IPostService postService;

    private String replyText;

    private Post post;

    public ReplyForm(String id, IPostService postService) {
        super(id);

        this.postService = postService;

        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextArea("replyText"));
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    protected void onSubmit() {

        Post post = new Post();
        post.setUser(((User) getSession().getAttribute("user")));
        post.setText(replyText);

        postService.sendReply(post, this.post);

        setResponsePage(getPage());
    }
}
