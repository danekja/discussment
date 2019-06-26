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
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.core.service.TopicService;

import java.util.List;

/**
 * Created by Martin Bláha on 04.02.17.
 *
 * The class creates the panel contains the topics
 */
public class TopicListPanel extends Panel {

    private TopicService topicService;
    private DiscussionService discussionService;
    private PostService postService;
    private AccessControlService accessControlService;
    private IModel<List<Topic>>  topicListModel;

    /**
     * Constructor for creating a instance of the panel contains the topics
     *
     * @param id id of the element into which the panel is inserted
     * @param topicListModel model for getting the topics
     * @param topicService instance of the topic service
     */
    public TopicListPanel(String id,
                          IModel<List<Topic>> topicListModel,
                          TopicService topicService,
                          DiscussionService discussionService,
                          PostService postService,
                          AccessControlService accessControlService) {
        super(id);

        this.topicService = topicService;
        this.discussionService = discussionService;
        this.postService = postService;
        this.accessControlService = accessControlService;
        this.topicListModel = topicListModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ListView<Topic>("topicList", topicListModel) {
            protected void populateItem(ListItem<Topic> listItem) {

                listItem.add(createOpenTopicLink(listItem.getModel()));
                listItem.add(createRemoveLink(listItem.getModel()));

                listItem.add(new Label("description", new PropertyModel<String>(listItem.getModel(), "description")));
                try {
                    List<Discussion> discussions = discussionService.getDiscussionsByTopic(listItem.getModelObject());
                    listItem.add(new Label("numberOfDiscussions", discussions.size()));
                    listItem.add(new Label("numberOfPosts", getNumberOfPosts(discussions)));
                } catch (AccessDeniedException e) {
                    listItem.add(new Label("numberOfDiscussions"));
                    listItem.add(new Label("numberOfPosts"));
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        this.setVisible(!topicListModel.getObject().isEmpty());
    }

    private Link createOpenTopicLink(final IModel<Topic> tm) {
        return new Link("openTopic") {
            @Override
            public void onClick() {
                PageParameters pageParameters = getPage().getPageParameters();
                pageParameters.add("topicId", tm.getObject().getId());

                setResponsePage(getPage().getPageClass(), pageParameters);
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
                try{
                    topicService.removeTopic(tm.getObject());
                } catch (AccessDeniedException e) {
                    //not yet implemented
                }
                setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                setVisible(accessControlService.canRemoveTopic(tm.getObject()));
            }
        };
    }

    private long getNumberOfPosts(List<Discussion> discussions) {
        long numberOfPosts = 0;
        try {
            for (Discussion discussion: discussions) {
                numberOfPosts += postService.getNumberOfPosts(discussion);
            }
        } catch (AccessDeniedException e){
            return numberOfPosts;
        }
        return numberOfPosts;
    }
}