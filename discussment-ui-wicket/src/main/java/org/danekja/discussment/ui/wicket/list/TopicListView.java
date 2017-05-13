package org.danekja.discussment.ui.wicket.list;

import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.TopicService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class TopicListView extends ListView<Topic> {

    public TopicListView(String id, IModel<? extends List<Topic>> model) {
        super(id, model);
    }

    protected void populateItem(ListItem<Topic> listItem) {
        final Topic topic = listItem.getModelObject();

        Link nameLink = new Link("nameLink") {
            @Override
            public void onClick() {
                PageParameters pageParameters = new PageParameters();
                pageParameters.add("topicId", topic.getId());

                setResponsePage(getWebPage().getClass(), pageParameters);
            }
        };

        nameLink.setBody(Model.of(topic.getName()));

        listItem.add(nameLink);

        Link removeLink = new Link("remove") {
            @Override
            public void onClick() {
                TopicService.removeTopic(topic);
            }
        };
        listItem.add(removeLink);

        listItem.add(new Label("description", topic.getDescription()));
        listItem.add(new Label("numberOfThreads", topic.getNumberOfThreads()));
        listItem.add(new Label("numberOfPosts", topic.getNumberOfPosts()));


        User user = (User) getSession().getAttribute("user");

        if (user != null && user.getPermissions().isRemoveTopic()) {
            removeLink.setVisible(true);
        } else {
            removeLink.setVisible(false);
        }
    }

}
