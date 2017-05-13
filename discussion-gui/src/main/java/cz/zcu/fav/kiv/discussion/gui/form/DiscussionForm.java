package cz.zcu.fav.kiv.discussion.gui.form;

import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.TopicEntity;
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

    private TopicEntity topicEntity;

    public DiscussionForm(String id, TopicEntity topicEntity) {
        super(id);

        this.topicEntity = topicEntity;

        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextField("name"));
        add(new CheckBox("priv"));
        add(new TextField("pass"));
    }

    @Override
    protected void onSubmit() {

        DiscussionEntity discussionEntity = new DiscussionEntity();
        discussionEntity.setName(name);
        discussionEntity.setTopic(topicEntity);
        discussionEntity.setPass(pass);

        DiscussionService.createDiscussion(discussionEntity);

        PageParameters pageParameters = new PageParameters();
        pageParameters.add("topicId", topicEntity.getId());

    }
}
