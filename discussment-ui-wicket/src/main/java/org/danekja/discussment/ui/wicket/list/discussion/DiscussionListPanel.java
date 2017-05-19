package org.danekja.discussment.ui.wicket.list.discussion;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.ui.wicket.model.DiscussionWicketModel;


/**
 * Created by Martin Bláha on 29.01.17.
 */
public class DiscussionListPanel extends Panel {

    private DiscussionService discussionService;
    private IModel<Discussion> discussionModel;
    private IModel<Topic> topicListModel;


    public DiscussionListPanel(String id, IModel<Topic> topicListModel, DiscussionService discussionService, IModel<Discussion> discussionModel) {
        super(id);

        this.discussionService = discussionService;
        this.discussionModel = discussionModel;
        this.topicListModel = topicListModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Label("topicName", topicListModel.getObject().getName()));
        add(createDiscussionAjaxLink());


        add(new ListView<Discussion>("discussionList", new DiscussionWicketModel(topicListModel, discussionService)) {
            protected void populateItem(ListItem<Discussion> listItem) {
                final Discussion discussion = listItem.getModelObject();

                listItem.add(createPasswordDivWebMarkupContainer(discussion));

                Post lastPost = discussion.getLastPost();

                listItem.add(new Label("numberOfPosts", discussion.getNumberOfPosts()));
                listItem.add(new Label("lastUsername", lastPost == null ? "" : lastPost.getUser().getUsername()));
                listItem.add(new Label("lastCreated", lastPost == null ? "" : lastPost.getCreatedFormat()));

                listItem.add(createRemoveDiscussionLink(discussion));
            }
        });
    }

    private WebMarkupContainer createPasswordDivWebMarkupContainer(Discussion discussion) {
        WebMarkupContainer div = new WebMarkupContainer("passwordDiv");

        User user = (User) getSession().getAttribute("user");

        if (user.isAccessToDiscussion(discussion)) {
            div.add(new AttributeModifier("href", "#"));
            div.add(new AttributeModifier("data-target", "#"));

            div.add(createOpenDiscussionAjaxLink(discussion, true));
        } else {
            div.add(createOpenDiscussionAjaxLink(discussion, false));
        }

        return div;
    }

    private AjaxLink createOpenDiscussionAjaxLink(final Discussion discussion, final boolean access) {
        AjaxLink discussionNameLink = new AjaxLink("openDiscussion") {

            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                discussionModel.setObject(discussion);

                if (access) {
                    PageParameters pageParameters = new PageParameters();
                    pageParameters.add("discussionId", discussion.getId());

                    setResponsePage(getWebPage().getClass(), pageParameters);
                }
            }
        };

        discussionNameLink.setBody(Model.of(discussion.getName()));

        return discussionNameLink;
    }

    private AjaxLink createDiscussionAjaxLink() {
        return new AjaxLink("createDiscussion") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {}

            @Override
            protected void onConfigure() {
                super.onConfigure();

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isCreateDiscussion());
            }
        };
    }

    private Link createRemoveDiscussionLink(final Discussion discussion) {
        return new Link("remove") {
            @Override
            public void onClick() {
                discussionService.removeDiscussion(discussion);
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isRemoveDiscussion());
            }
        };
    }


}
