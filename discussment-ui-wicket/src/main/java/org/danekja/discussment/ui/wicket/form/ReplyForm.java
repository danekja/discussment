package org.danekja.discussment.ui.wicket.form;

import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.PostService;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class ReplyForm extends Form {

    private String replyText;

    private Post post;

    public ReplyForm(String id) {
        super(id);

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

        PostService.sendReply(post, this.post);

        setResponsePage(getPage());
    }
}
