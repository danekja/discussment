package org.danekja.discussment.ui.wicket.form;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.PostService;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class PostForm extends Form {

    private String text;

    private Discussion discussion;

    private TextArea<String> ta;

    public PostForm(String id, Discussion discussion) {
        super(id);

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

        PostService.sendPost(discussion, post);

        text = "";

        setResponsePage(getPage());
    }
}
