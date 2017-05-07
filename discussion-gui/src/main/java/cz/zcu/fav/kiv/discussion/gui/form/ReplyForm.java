package cz.zcu.fav.kiv.discussion.gui.form;

import cz.zcu.fav.kiv.discussion.core.model.UserModel;
import cz.zcu.fav.kiv.discussion.core.service.PostService;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class ReplyForm extends Form {

    private String replyText;

    private long postId;

    public ReplyForm(String id) {
        super(id);

        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextArea("replyText"));
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    @Override
    protected void onSubmit() {

        PostService.sendReply(
                ((UserModel) getSession().getAttribute("user")).getId(),
                replyText,
                postId
        );

        setResponsePage(getPage());
    }
}
