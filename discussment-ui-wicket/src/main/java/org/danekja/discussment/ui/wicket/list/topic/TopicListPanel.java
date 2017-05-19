package org.danekja.discussment.ui.wicket.list.topic;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.ui.wicket.model.TopicWicketModel;

/**
 * Created by Martin Bláha on 04.02.17.
 */
public class TopicListPanel extends Panel {

    private TopicService topicService;
    private TopicWicketModel topicWicketModel;

    public TopicListPanel(String id, TopicWicketModel topicWicketModel, TopicService topicService) {
        super(id);

        this.topicService = topicService;
        this.topicWicketModel = topicWicketModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ListView<Topic>("topicList", topicWicketModel) {
            protected void populateItem(ListItem<Topic> listItem) {
                final Topic topic = listItem.getModelObject();

                listItem.add(createOpenTopicLink(topic));
                listItem.add(createRemoveLink(topic));

                listItem.add(new Label("description", topic.getDescription()));
                listItem.add(new Label("numberOfDiscussions", topic.getNumberOfDiscussions()));
                listItem.add(new Label("numberOfPosts", topic.getNumberOfPosts()));
            }
        });
    }

    private Link createOpenTopicLink(final Topic topic) {
        return new Link("openTopic") {
            @Override
            public void onClick() {
                PageParameters pageParameters = new PageParameters();
                pageParameters.add("topicId", topic.getId());

                setResponsePage(getWebPage().getClass(), pageParameters);
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                setBody(Model.of(topic.getName()));
            }
        };
    }

    private Link createRemoveLink(final Topic topic) {
        return new Link("remove") {
            @Override
            public void onClick() {
                topicService.removeTopic(topic);
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isRemoveTopic());
            }
        };
    }

}
