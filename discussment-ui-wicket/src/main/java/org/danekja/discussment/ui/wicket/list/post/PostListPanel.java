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
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.PostService;

import java.util.List;

/**
 * Created by Martin Bláha on 04.02.17.
 */
public class PostListPanel extends Panel {

    private IModel<Post> postModel;
    private PostService postService;
    private IModel<List<Post>> postListModel;

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
            protected void populateItem(ListItem<Post> listItem) {
                final Post post = listItem.getModelObject();

                Label text = new Label("text", post.getText()) {
                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        this.setVisible(!post.isDisabled());
                    }
                };
                listItem.add(text);

                WebMarkupContainer dis = new WebMarkupContainer("disabled") {
                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        this.setVisible(post.isDisabled());
                    }
                };
                listItem.add(dis);

                listItem.add(new Label("username", post.getUser().getUsername()));
                listItem.add(new Label("created", post.getCreatedFormat()));


                listItem.add(createReplyAjaxLink(post));
                listItem.add(createRemoveLink(post));
                listItem.add(createDisableLink(post));

                listItem.add(new AttributeModifier("style", "padding-left: " + post.getLevel() * 30 + "px"));

            }
        });
    }

    private AjaxLink createReplyAjaxLink(final Post post) {
        return new AjaxLink("reply") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                postModel.setObject(post);
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isCreatePost() && !post.isDisabled());
            }
        };
    }

    private Link createRemoveLink(final Post post) {
        return new Link("remove") {
            @Override
            public void onClick() {
                postService.removePost(post);
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isRemovePost());
            }
        };
    }

    private Link createDisableLink(final Post post) {
        IModel<String> model = new Model<String>() {
            @Override
            public String getObject() {
                if (post.isDisabled()) {
                    return "Enable";
                } else {
                    return "Disable";
                }
            }
        };

        Link disableLink = new Link("disable") {
            @Override
            public void onClick() {
                if (post.isDisabled()) {
                    postService.enablePost(post);
                } else {
                    postService.disablePost(post);
                }
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isDisablePost());
            }
        };
        disableLink.setBody(model);

        return disableLink;
    }


}
