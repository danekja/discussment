package org.danekja.discussment.ui.wicket.list.post;

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
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.PostService;

import java.util.List;

/**
 * Created by Martin Bláha on 04.02.17.
 *
 * The class creates the panel contains the posts
 */
public class PostListPanel extends Panel {

    private IModel<Post> postModel;
    private PostService postService;
    private IModel<List<Post>> postListModel;

    /**
     * Constructor for creating a instance of the panel contains the posts
     *
     * @param id id of the element into which the panel is inserted
     * @param postListModel model for getting the posts
     * @param postModel model for setting the selected post
     * @param postService instance of the post service
     */
    public PostListPanel(String id, IModel<List<Post>> postListModel, IModel<Post> postModel, PostService postService) {
        super(id);

        this.postModel = postModel;
        this.postService = postService;
        this.postListModel = postListModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

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

                listItem.add(new Label("username", new PropertyModel<String>(listItem.getModel(), "user.username")));
                listItem.add(new Label("created", new PropertyModel<String>(listItem.getModel(), "created")));


                listItem.add(createReplyAjaxLink(listItem.getModel()));
                listItem.add(createRemoveLink(listItem.getModel()));
                listItem.add(createDisableLink(listItem.getModel()));

                listItem.add(new AttributeModifier("style", "padding-left: " + listItem.getModelObject().getLevel() * 30 + "px"));

            }
        });
    }

    private AjaxLink createReplyAjaxLink(final IModel<Post> pm) {
        return new AjaxLink("reply") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                postModel.setObject(pm.getObject());
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isCreatePost() && !pm.getObject().isDisabled());
            }
        };
    }

    private Link createRemoveLink(final IModel<Post> pm) {
        return new Link("remove") {
            @Override
            public void onClick() {
                postService.removePost(pm.getObject());
                setResponsePage(getWebPage().getClass(), getWebPage().getPageParameters());
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isRemovePost());
            }
        };
    }

    private Link createDisableLink(final IModel<Post> pm) {

        Link disableLink = new Link("disable") {
            @Override
            public void onClick() {
                if (pm.getObject().isDisabled()) {
                    postService.enablePost(pm.getObject());
                } else {
                    postService.disablePost(pm.getObject());
                }
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isDisablePost());
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
