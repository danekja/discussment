package org.danekja.discussment.ui.wicket.list.topic;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.TopicService;

import java.util.List;

/**
 * Created by Martin Bláha on 04.02.17.
 *
 * The class creates the panel contains the topics
 */
public class TopicListPanel extends Panel {

    private TopicService topicService;
    private AccessControlService accessControlService;
    private IModel<List<Topic>>  topicListModel;

    /**
     * Constructor for creating a instance of the panel contains the topics
     *
     * @param id id of the element into which the panel is inserted
     * @param topicListModel model for getting the topics
     * @param topicService instance of the topic service
     */
    public TopicListPanel(String id, IModel<List<Topic>> topicListModel, TopicService topicService, AccessControlService accessControlService) {
        super(id);

        this.topicService = topicService;
        this.accessControlService = accessControlService;
        this.topicListModel = topicListModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        if (topicListModel.getObject().size() == 0) {
            setVisible(false);
        } else {
            setVisible(true);
        }

        add(new ListView<Topic>("topicList", topicListModel) {
            protected void populateItem(ListItem<Topic> listItem) {

                listItem.add(createOpenTopicLink(listItem.getModel()));
                listItem.add(createRemoveLink(listItem.getModel()));

                listItem.add(new Label("description", new PropertyModel<String>(listItem.getModel(), "description")));
                listItem.add(new Label("numberOfDiscussions", new PropertyModel<String>(listItem.getModel(), "numberOfDiscussions")));
                listItem.add(new Label("numberOfPosts", new PropertyModel<String>(listItem.getModel(), "numberOfPosts")));

            }
        });
    }

    private Link createOpenTopicLink(final IModel<Topic> tm) {
        return new Link("openTopic") {
            @Override
            public void onClick() {
                PageParameters pageParameters = new PageParameters();
                pageParameters.add("topicId", tm.getObject().getId());

                setResponsePage(getWebPage().getClass(), pageParameters);
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                setBody(Model.of(tm.getObject().getName()));
            }
        };
    }

    private Link createRemoveLink(final IModel<Topic> tm) {
        return new Link("remove") {
            @Override
            public void onClick() {

                topicService.removeTopic(tm.getObject());
                setResponsePage(getWebPage().getClass());
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                setVisible(accessControlService.canRemoveTopic(tm.getObject()));
            }
        };
    }

}
