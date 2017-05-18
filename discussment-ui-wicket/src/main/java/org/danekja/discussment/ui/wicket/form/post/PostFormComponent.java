package org.danekja.discussment.ui.wicket.form.post;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Post;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class PostFormComponent extends FormComponentPanel {

    private TextArea<String> text;

    private IModel<Post> postModel;

    public PostFormComponent(String id, IModel<Post> postModel) {
        super(id, postModel);

        this.postModel = postModel;

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        text = new TextArea<String>("text", new Model<String>(""));
        add(text);

    }

    @Override
    public void updateModel() {
        super.updateModel();

        postModel.setObject(new Post(text.getModelObject()));

    }

}
