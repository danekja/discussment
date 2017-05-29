package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.ui.wicket.form.discussion.DiscussionFormComponent;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class DiscussionForm extends Form {

    private DiscussionService discussionService;
    private IModel<Topic> topicModel;

    private IModel<Discussion> discussionModel;

    public DiscussionForm(String id, IModel<Topic> topicModel, IModel<Discussion> discussionModel) {
        this(id, null, topicModel, discussionModel);
    }

    public DiscussionForm(String id, DiscussionService discussionService, IModel<Topic> topicModel, IModel<Discussion> discussionModel) {
        super(id);

        this.discussionService = discussionService;
        this.topicModel = topicModel;
        this.discussionModel = discussionModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new DiscussionFormComponent("discussionFormComponent", discussionModel));
    }

    public void setDiscussionService(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @Override
    protected void onSubmit() {

        Discussion discussion = discussionModel.getObject();
        discussion.setTopic(topicModel.getObject());

        if (discussionService != null) {
            discussionService.createDiscussion(discussion);

            discussionModel.setObject(new Discussion());

            PageParameters pageParameters = new PageParameters();
            pageParameters.add("topicId", topicModel.getObject().getId());
        }
    }
}
