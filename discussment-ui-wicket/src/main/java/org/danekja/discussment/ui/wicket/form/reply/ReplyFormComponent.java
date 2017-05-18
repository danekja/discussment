package org.danekja.discussment.ui.wicket.form.reply;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Post;


/**
 * Created by Martin Bláha on 29.01.17.
 */
public class ReplyFormComponent extends FormComponentPanel {


    private TextArea<String> replyText;

    private IModel<Post> postModel;

    public ReplyFormComponent(String id, IModel<Post> postModel) {
        super(id, postModel);

        this.postModel = postModel;

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        replyText = new TextArea<String>("replyText", new Model<String>(""));
        add(replyText);

    }

    @Override
    public void updateModel() {
        super.updateModel();

        postModel.setObject(new Post(replyText.getModelObject()));

    }

}

