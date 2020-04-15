package org.danekja.discussment.ui.wicket.list.post;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.configuration.service.ConfigurationService;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostReputationService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.panel.postReputation.PostReputationPanel;

import java.util.List;
import java.util.Map;

/**
 * Created by Martin Bláha on 04.02.17.
 *
 * The class creates the panel contains the posts
 */
public class PostListPanel extends Panel {

    private IModel<Post> postModel;
    private PostService postService;
    private IModel<List<Post>> postListModel;

    private DiscussionUserService userService;
    private PostReputationService postReputationService;
    private ConfigurationService configurationService;

    /**
     * Constructor for creating a instance of the panel contains the posts
     *
     * @param id id of the element into which the panel is inserted
     * @param postListModel model for getting the posts
     * @param postModel model for setting the selected post
     * @param postService instance of the post service
     * @param userService instance of the user service
     * @param postReputationService instance of the post reputation service
     * @param configurationService instance of the configuration service
     */
    public PostListPanel(String id,
                         IModel<List<Post>> postListModel,
                         IModel<Post> postModel,
                         PostService postService,
                         DiscussionUserService userService,
                         PostReputationService postReputationService,
                         ConfigurationService configurationService) {
        super(id);

        this.postModel = postModel;
        this.postService = postService;
        this.postListModel = postListModel;

        this.userService = userService;
        this.postReputationService = postReputationService;
        this.configurationService = configurationService;
    }


    @Override
    protected void onInitialize() {
        super.onInitialize();

        Map<Long, IDiscussionUser> postsAuthors = postService.getPostsAuthors(postListModel.getObject());

        add(new ListView<Post>("postListView", postListModel) {
            protected void populateItem(final ListItem<Post> listItem) {

                Label text = new Label("text", new PropertyModel<String>(listItem.getModel(), "text")) {
                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        this.setVisible(!listItem.getModelObject().isDisabled());
                    }
                };
                listItem.add(text);

                WebMarkupContainer dis = new WebMarkupContainer("disabled") {
                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        this.setVisible(listItem.getModelObject().isDisabled());
                    }
                };
                listItem.add(dis);

                listItem.add(new Label("username", new LoadableDetachableModel<String>() {
                    protected String load() {

                        if (postsAuthors.containsKey(listItem.getModelObject().getId())) {
                            return postsAuthors.get(listItem.getModelObject().getId()).getDisplayName();
                        } else {
                            return getString("error.userNotFound");
                        }
                    }
                }));

                listItem.add(DateLabel.forDateStyle("created", new PropertyModel<>(listItem.getModel(), "created"), "MS"));


                listItem.add(createReplyAjaxLink(listItem.getModel()));
                listItem.add(createRemoveLink(listItem.getModel()));
                listItem.add(createDisableLink(listItem.getModel()));

                listItem.add(new AttributeModifier("style", "padding-left: " + listItem.getModelObject().getLevel() * 30 + "px"));

                listItem.add(new PostReputationPanel("postreputation", listItem.getModel(), postService, postReputationService, userService));
            }
        });

    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        this.setVisible(!postListModel.getObject().isEmpty());
    }

    private AjaxLink createReplyAjaxLink(final IModel<Post> pm) {
        return new AjaxLink("reply") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                postModel.setObject(pm.getObject());

                target.appendJavaScript("$('#replyModal').modal('show');");
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();
                this.setVisible(pm.getObject().getLevel() < configurationService.maxReplyLevel());
            }
        };
    }

    private Link createRemoveLink(final IModel<Post> pm) {
        return new Link("remove") {
            @Override
            public void onClick() {
                try {
                    postService.removePost(pm.getObject());
                } catch (AccessDeniedException e) {
                    this.error(getString("error.accessDenied"));
                }
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();
                this.setVisible(!userService.isGuest() && userService.getCurrentlyLoggedUser().getDiscussionUserId().equals(pm.getObject().getUserId()));
            }
        };
    }

    private Link createDisableLink(final IModel<Post> pm) {

        Link disableLink = new AjaxFallbackLink("disable") {
            @Override
            public void onClick(AjaxRequestTarget target) {

                if (pm.getObject().isDisabled()) {
                    try{
                        postService.enablePost(pm.getObject());
                    } catch (AccessDeniedException e) {
                        this.error(getString("error.accessDenied"));
                    }
                } else {
                    try {
                        postService.disablePost(pm.getObject());
                    } catch (AccessDeniedException e) {
                        this.error(getString("error.accessDenied"));
                    }
                }
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                this.setVisible(!userService.isGuest() && userService.getCurrentlyLoggedUser().getDiscussionUserId().equals(pm.getObject().getUserId()));
            }
        };

        WebMarkupContainer span = new WebMarkupContainer("disable_icon") {
            @Override
            protected void onConfigure() {
                super.onConfigure();

                if (pm.getObject().isDisabled()) {
                    add(new AttributeModifier("class", "glyphicon glyphicon-ok-circle"));
                } else {
                    add(new AttributeModifier("class", "glyphicon glyphicon-ban-circle"));
                }
            }
        };
        disableLink.add(span);

        return disableLink;
    }


}
