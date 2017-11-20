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
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.accesscontrol.domain.Permission;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.PermissionService;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.ui.wicket.model.DiscussionWicketModel;


/**
 * Created by Martin Bláha on 29.01.17.
 *
 * The class creates the panel contains the discussions
 */
public class DiscussionListPanel extends Panel {

    private DiscussionService discussionService;
    private IModel<Discussion> discussionModel;
    private IModel<Topic> topicListModel;
    private PermissionService permissionService;


    /**
     * Constructor for creating a instance of the panel contains the discussions
     *
     * @param id id of the element into which the panel is inserted
     * @param topicListModel model for getting the topics
     * @param discussionService instance of the discussion service
     * @param discussionModel model for setting the selected discussion
     */
    public DiscussionListPanel(String id, IModel<Topic> topicListModel, DiscussionService discussionService, IModel<Discussion> discussionModel, PermissionService permissionService) {
        super(id);

        this.discussionService = discussionService;
        this.discussionModel = discussionModel;
        this.topicListModel = topicListModel;
        this.permissionService = permissionService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(createPasswordAlert());

        add(new Label("topicName", topicListModel.getObject().getName()));
        add(createDiscussionAjaxLink());


        add(new ListView<Discussion>("discussionList", new DiscussionWicketModel(topicListModel, discussionService)) {
            protected void populateItem(final ListItem<Discussion> listItem) {

                listItem.add(createPasswordDivWebMarkupContainer(listItem.getModel()));

                listItem.add(new Label("numberOfPosts", new PropertyModel<String>(listItem.getModel(), "numberOfPosts")));
                listItem.add(new Label("lastUsername", new LoadableDetachableModel<String>() {
                    protected String load() {
                        try {
                            return discussionService.getLastPostAuthor(listItem.getModelObject());
                        } catch (DiscussionUserNotFoundException e) {
                            return "Error: author of last post not found";
                        }
                    }
                }));
                listItem.add(new Label("lastCreated", new PropertyModel<String>(listItem.getModel(), "lastPost.getCreatedFormat")));

                listItem.add(createRemoveDiscussionLink(listItem.getModel()));
            }
        });
    }

    private WebMarkupContainer createPasswordAlert() {
        return new WebMarkupContainer("alertPassword") {
            @Override
            protected void onConfigure() {
                super.onConfigure();

                if (getSession().getAttribute("error") != null && getSession().getAttribute("error").equals("password")) {
                    setVisible(true);
                    getSession().setAttribute("error", null);
                } else {
                    setVisible(false);
                }


            }
        };
    }

    private WebMarkupContainer createPasswordDivWebMarkupContainer(final IModel<Discussion> dm) {

        WebMarkupContainer div = new WebMarkupContainer("passwordDiv") {
            @Override
            protected void onConfigure() {
                super.onConfigure();

                if (discussionService.hasCurrentUserAccessToDiscussion(dm.getObject())) {
                    add(new AttributeModifier("href", "#"));
                    add(new AttributeModifier("data-target", "#"));

                    add(createOpenDiscussionAjaxLink(dm, true));
                } else {
                    add(createOpenDiscussionAjaxLink(dm, false));
                }
            }
        };

        return div;
    }

    private AjaxLink createOpenDiscussionAjaxLink(final IModel<Discussion> dm, final boolean access) {
        AjaxLink discussionNameLink = new AjaxLink("openDiscussion") {

            public void onClick(AjaxRequestTarget ajaxRequestTarget) {

                discussionModel.setObject(dm.getObject());

                if (access) {
                    PageParameters pageParameters = new PageParameters();
                    pageParameters.add("discussionId", dm.getObject().getId());

                    setResponsePage(getWebPage().getClass(), pageParameters);
                }
            }
        };

        discussionNameLink.setBody(Model.of(dm.getObject().getName()));

        return discussionNameLink;
    }

    private AjaxLink createDiscussionAjaxLink() {
        return new AjaxLink("createDiscussion") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {}

            @Override
            protected void onConfigure() {
                super.onConfigure();

                Permission p = permissionService.getCurrentlyLoggedUsersPermission();
                this.setVisible(p != null && p.isCreateDiscussion());
            }
        };
    }

    private Link createRemoveDiscussionLink(final IModel<Discussion> dm) {
        return new Link("remove") {
            @Override
            public void onClick() {
                discussionService.removeDiscussion(dm.getObject());
                setResponsePage(getWebPage().getClass(), getWebPage().getPageParameters());
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                Permission p = permissionService.getCurrentlyLoggedUsersPermission();
                this.setVisible(p != null && p.isRemoveDiscussion());
            }
        };
    }


}
