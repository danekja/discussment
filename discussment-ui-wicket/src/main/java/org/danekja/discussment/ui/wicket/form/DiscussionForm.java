package org.danekja.discussment.ui.wicket.form;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;
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

    private Topic topic;

    public DiscussionForm(String id, Topic topic) {
        super(id);

        this.topic = topic;

        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextField("name"));
        add(new CheckBox("priv"));
        add(new TextField("pass"));
    }

    @Override
    protected void onSubmit() {

        Discussion discussion = new Discussion();
        discussion.setName(name);
        discussion.setTopic(topic);
        discussion.setPass(pass);

        DiscussionService.createDiscussion(discussion);

        PageParameters pageParameters = new PageParameters();
        pageParameters.add("topicId", topic.getId());

    }
}
