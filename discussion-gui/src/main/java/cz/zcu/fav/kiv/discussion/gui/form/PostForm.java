package cz.zcu.fav.kiv.discussion.gui.form;

import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.PostEntity;
import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;
import cz.zcu.fav.kiv.discussion.core.service.PostService;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class PostForm extends Form {

    private String text;

    private DiscussionEntity discussionEntity;

    private TextArea<String> ta;

    public PostForm(String id, DiscussionEntity discussionEntity) {
        super(id);

        this.discussionEntity = discussionEntity;

        setDefaultModel(new CompoundPropertyModel(this));

        ta = new TextArea<String>("text");

        add(ta);
    }

    @Override
    protected void onSubmit() {

        PostEntity post = new PostEntity();
        post.setText(text);
        post.setUser((UserEntity) getSession().getAttribute("user"));

        PostService.sendPost(discussionEntity, post);

        text = "";

        setResponsePage(getPage());
    }
}
