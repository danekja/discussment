package cz.zcu.fav.kiv.discussion.gui.form;

import cz.zcu.fav.kiv.discussion.core.service.DiscussionService;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class DiscussionForm extends Form {

    private String name;
    private boolean priv;
    private String pass;

    private long topicId;

    public DiscussionForm(String id, long topicId) {
        super(id);

        this.topicId = topicId;

        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextField("name"));
        add(new CheckBox("priv"));
        add(new TextField("pass"));
    }

    @Override
    protected void onSubmit() {
        if (priv == false) {
            if (topicId == -1) {
                DiscussionService.createDiscussion(name);
            } else {
                DiscussionService.createDiscussion(name, topicId);
            }
        } else {
            DiscussionService.createDiscussion(name, topicId, pass);
        }

        PageParameters pageParameters = new PageParameters();
        pageParameters.add("topicId", topicId);

    }
}
