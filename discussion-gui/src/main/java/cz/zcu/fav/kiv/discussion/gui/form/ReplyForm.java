package cz.zcu.fav.kiv.discussion.gui.form;

import cz.zcu.fav.kiv.discussion.core.entity.PostEntity;
import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;
import cz.zcu.fav.kiv.discussion.core.service.PostService;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class ReplyForm extends Form {

    private String replyText;

    private PostEntity postEntity;

    public ReplyForm(String id) {
        super(id);

        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextArea("replyText"));
    }

    public PostEntity getPost() {
        return postEntity;
    }

    public void setPost(PostEntity postEntity) {
        this.postEntity = postEntity;
    }

    @Override
    protected void onSubmit() {

        PostEntity post = new PostEntity();
        post.setUser(((UserEntity) getSession().getAttribute("user")));
        post.setText(replyText);

        PostService.sendReply(post, postEntity);

        setResponsePage(getPage());
    }
}
