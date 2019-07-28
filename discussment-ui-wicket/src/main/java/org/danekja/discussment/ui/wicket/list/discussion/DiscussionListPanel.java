package org.danekja.discussment.ui.wicket.list.discussion;

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
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.accesscontrol.service.PermissionManagementService;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.form.PasswordForm;
import org.danekja.discussment.ui.wicket.model.DiscussionWicketModel;

/**
 * Created by Martin Bláha on 29.01.17.
 *
 * The class creates the panel contains the discussions
 */
public class DiscussionListPanel extends Panel {

    private DiscussionService discussionService;
    private PostService postService;
    private IModel<Discussion> discussionModel;
    private IModel<Topic> topicListModel;
    private DiscussionUserService userService;
    private AccessControlService accessControlService;
    private PermissionManagementService permissionService;


    /**
     * Constructor for creating a instance of the panel contains the discussions
     *
     * @param id id of the element into which the panel is inserted
     * @param topicListModel model for getting the topics
     * @param discussionService instance of the discussion service
     * @param discussionModel model for setting the selected discussion
     */
    public DiscussionListPanel(String id,
                               IModel<Topic> topicListModel,
                               IModel<Discussion> discussionModel,
                               DiscussionService discussionService,
                               PostService postService,
                               DiscussionUserService userService,
                               AccessControlService accessControlService,
                               PermissionManagementService permissionService) {
        super(id);

        this.discussionService = discussionService;
        this.postService = postService;
        this.discussionModel = discussionModel;
        this.topicListModel = topicListModel;
        this.userService = userService;
        this.accessControlService = accessControlService;
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
                listItem.add(createOpenDiscussionAjaxLink(listItem.getModel()));

                Long numberOfPosts = getNumberOfPosts(listItem.getModelObject());
                listItem.add(new Label("numberOfPosts", new Model<>(numberOfPosts)));

                Post lastPost = getLastPost(listItem.getModelObject());
                listItem.add(new Label("lastCreated", new PropertyModel<>(lastPost, "created")));

                IDiscussionUser lastPostAuthor = getPostAuthor(lastPost);
                listItem.add(new Label("lastUsername", new PropertyModel<>(lastPostAuthor, "displayName")));

                listItem.add(createRemoveDiscussionLink(listItem.getModel()));
            }
        });

        add(new PasswordForm("passwordForm", discussionModel, new Model<>(new Discussion()), userService, accessControlService, permissionService, discussionService));
    }

    private Long getNumberOfPosts(Discussion discussion) {
        try {
            return postService.getNumberOfPosts(discussion);
        } catch (AccessDeniedException e) {
            return null;
        }
    }

    private Post getLastPost(Discussion discussion) {
        try {
            return postService.getLastPost(discussion);
        } catch (AccessDeniedException e) {
            return null;
        }
    }

    private IDiscussionUser getPostAuthor(Post post) {
        if (post == null) {
            return null;
        }

        try {
            return postService.getPostAuthor(post);
        } catch (AccessDeniedException | DiscussionUserNotFoundException e) {
            return null;
        }
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

    private AjaxLink createOpenDiscussionAjaxLink(final IModel<Discussion> dm) {
        AjaxLink discussionNameLink = new AjaxLink("openDiscussion") {

            public void onClick(AjaxRequestTarget target) {

                discussionModel.setObject(dm.getObject());

                if (accessControlService.canViewPosts(dm.getObject())) {
                    PageParameters pageParameters = getPage().getPageParameters();
                    pageParameters.add("discussionId", dm.getObject().getId());

                    setResponsePage(getPage().getPageClass(), pageParameters);
                } else {
                    target.appendJavaScript("$('#passwordModal').modal('show');");
                }
            }
        };

        discussionNameLink.setBody(Model.of(dm.getObject().getName()));

        return discussionNameLink;
    }

    private AjaxLink createDiscussionAjaxLink() {
        return new AjaxLink("createDiscussion") {
            @Override
            protected void onConfigure() {
                super.onConfigure();

                this.setVisible(accessControlService.canAddDiscussion(topicListModel.getObject()));
            }

            @Override
            public void onClick(AjaxRequestTarget target) {
                target.appendJavaScript("$('#discussionModal').modal('show');");
            }
        };
    }

    private Link createRemoveDiscussionLink(final IModel<Discussion> dm) {
        return new Link("remove") {
            @Override
            public void onClick() {
                try {
                    discussionService.removeDiscussion(dm.getObject());
                } catch (AccessDeniedException e) {
                    // todo: not yet implemented
                }

                setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                this.setVisible(accessControlService.canRemoveTopic(topicListModel.getObject()));
            }
        };
    }


}
