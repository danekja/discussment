package cz.zcu.fav.kiv.discussion.gui.form;

import cz.zcu.fav.kiv.discussion.core.model.UserModel;
import cz.zcu.fav.kiv.discussion.core.service.PostService;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class PostForm extends Form {

    private String text;

    private long discussionId;

    private TextArea<String> ta;

    public PostForm(String id, long discussionId) {
        super(id);

        this.discussionId = discussionId;

        setDefaultModel(new CompoundPropertyModel(this));

        ta = new TextArea<String>("text");

        add(ta);
    }

    @Override
    protected void onSubmit() {

        PostService.sendPost(
                discussionId,
                ((UserModel) getSession().getAttribute("user")).getId(),
                text
        );

        text = "";

        setResponsePage(getPage());
    }
}
