package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.IPostService;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class PostForm extends Form {

    private IPostService postService;

    private String text;

    private Discussion discussion;

    private TextArea<String> ta;

    public PostForm(String id, Discussion discussion, IPostService postService) {
        super(id);

        this.postService = postService;

        this.discussion = discussion;

        setDefaultModel(new CompoundPropertyModel(this));

        ta = new TextArea<String>("text");

        add(ta);
    }

    @Override
    protected void onSubmit() {

        Post post = new Post();
        post.setText(text);
        post.setUser((User) getSession().getAttribute("user"));

        postService.sendPost(discussion, post);

        text = "";

        setResponsePage(getPage());
    }
}
