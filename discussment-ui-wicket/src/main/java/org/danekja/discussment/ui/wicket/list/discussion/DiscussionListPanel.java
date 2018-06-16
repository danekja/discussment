package org.danekja.discussment.ui.wicket.list.discussion;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
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
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.model.DiscussionWicketModel;

import java.util.List;


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
    private AccessControlService accessControlService;


    /**
     * Constructor for creating a instance of the panel contains the discussions
     *
     * @param id id of the element into which the panel is inserted
     * @param topicListModel model for getting the topics
     * @param discussionService instance of the discussion service
     * @param discussionModel model for setting the selected discussion
     */
    public DiscussionListPanel(String id, IModel<Topic> topicListModel, IModel<Discussion> discussionModel, DiscussionService discussionService, PostService postService, AccessControlService accessControlService) {
        super(id);

        this.discussionService = discussionService;
        this.postService = postService;
        this.discussionModel = discussionModel;
        this.topicListModel = topicListModel;
        this.accessControlService = accessControlService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(createPasswordAlert());

        try {
            add(new Label("topicName", topicListModel.getObject().getName()));
        } catch (NullPointerException e){
            PageParameters pageParameters = getPage().getPageParameters();
            add(new Label("topicName", "Topic with id " + pageParameters.get("topicId")+ " not found"));
        }
        add(createDiscussionAjaxLink());


        add(new ListView<Discussion>("discussionList", new DiscussionWicketModel(topicListModel, discussionService)) {
            protected void populateItem(final ListItem<Discussion> listItem) {

                listItem.add(createOpenDiscussionAjaxLink(listItem.getModel()));

                try {
                    listItem.add(new Label("numberOfPosts", postService.getNumberOfPosts(listItem.getModelObject())));
                } catch (AccessDeniedException e){
                    listItem.add(new Label("numberOfPosts"));
                }

                listItem.add(new Label("lastUsername", new LoadableDetachableModel<String>() {
                    protected String load() {
                        try {
                            return discussionService.getLastPostAuthor(listItem.getModelObject()).getDisplayName();
                        } catch (DiscussionUserNotFoundException e) {
                            return getString("error.userNotFound");
                        } catch (AccessDeniedException e){
                            return getString("error.accessDenied");
                        } catch (NullPointerException e){
                            return "";
                        }
                    }
                }));
                try {
                    listItem.add(new Label("lastCreated", postService.getLastPost(listItem.getModelObject()).getCreated()));
                } catch (NullPointerException e){
                    listItem.add(new Label("lastCreated"));
                } catch (AccessDeniedException e){
                    listItem.add(new Label("lastCreated"));
                }

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

                try {
                    this.setVisible(accessControlService.canAddDiscussion(topicListModel.getObject()));
                } catch (NullPointerException e) {
                    this.setVisible(false);
                }
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
