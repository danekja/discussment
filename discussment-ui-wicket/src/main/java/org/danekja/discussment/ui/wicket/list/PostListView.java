package org.danekja.discussment.ui.wicket.list;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.IPostService;
import org.danekja.discussment.ui.wicket.form.ReplyForm;

import java.util.List;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class PostListView extends ListView<Post> {

    private IPostService postService;

    private ReplyForm replyForm;

    public PostListView(String id, IModel<? extends List<Post>> model, ReplyForm replyForm, IPostService postService) {
        super(id, model);

        this.postService = postService;

        this.replyForm = replyForm;
    }

    protected void populateItem(ListItem<Post> listItem) {
        final Post post = listItem.getModelObject();


        Label text = new Label("text", post.getText());
        listItem.add(text);

        WebMarkupContainer dis = new WebMarkupContainer("disabled");
        listItem.add(dis);

        if (post.isDisabled() == true) {
            text.setVisible(false);
        } else {
            dis.setVisible(false);
        }

        listItem.add(new Label("username", post.getUser().getUsername()));
        listItem.add(new Label("created", post.getCreatedFormat()));


        AjaxLink replyLink = new AjaxLink("reply") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                replyForm.setPost(post);
            }
        };
        listItem.add(replyLink);

        Link removeLink = new Link("remove") {
            @Override
            public void onClick() {
                postService.removePost(post);
            }
        };
        listItem.add(removeLink);


        Link disableLink = new Link("disable") {
            @Override
            public void onClick() {
                if (post.isDisabled()) {
                    postService.enablePost(post);
                } else {
                    postService.disablePost(post);
                }
            }
        };

        if (post.isDisabled()) {
            disableLink.setBody(Model.of("Enable"));
        } else {
            disableLink.setBody(Model.of("Disable"));
        }

        listItem.add(disableLink);

        listItem.add(new AttributeModifier("style", "padding-left: " + post.getLevel() * 30 + "px"));


        User user = (User)getSession().getAttribute("user");

        if (user != null && user.getPermissions().isCreatePost()) {
            replyLink.setVisible(true);
        } else {
            replyLink.setVisible(false);
        }

        if (user != null && user.getPermissions().isRemovePost()) {
            removeLink.setVisible(true);
        } else {
            removeLink.setVisible(false);
        }

        if (user != null && user.getPermissions().isDisablePost()) {
            disableLink.setVisible(true);
        } else {
            disableLink.setVisible(false);
        }

    }
}
