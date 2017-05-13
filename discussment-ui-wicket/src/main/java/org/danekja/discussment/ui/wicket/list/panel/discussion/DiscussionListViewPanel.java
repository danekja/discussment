package org.danekja.discussment.ui.wicket.list.panel.discussion;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.IDiscussionService;
import org.danekja.discussment.core.service.ITopicService;
import org.danekja.discussment.core.service.IUserService;
import org.danekja.discussment.ui.wicket.form.panel.discussion.DiscussionModalFormPanel;
import org.danekja.discussment.ui.wicket.form.panel.password.PasswordModalFormPanel;
import org.danekja.discussment.ui.wicket.list.DiscussionListView;
import org.danekja.discussment.ui.wicket.model.DiscussionWicketModel;


/**
 * Created by Martin Bláha on 29.01.17.
 */
public class DiscussionListViewPanel extends Panel {

    private AjaxLink createDiscussion;

    protected void onBeforeRender() {
        super.onBeforeRender();

        User user = (User) getSession().getAttribute("user");

        if (user != null && user.getPermissions().isCreateDiscussion()) {
            createDiscussion.setVisible(true);
        } else {
            createDiscussion.setVisible(false);
        }
    }

    public DiscussionListViewPanel(String id, DiscussionWicketModel discussion, ITopicService topicService, IDiscussionService discussionService, IUserService userService) {
        super(id);


        add(new Label("topicName", topicService.getTopicById(discussion.getTopic().getId()).getName()));

        createDiscussion = new AjaxLink("createDiscussion") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {}
        };
        add(createDiscussion);

        add(new DiscussionModalFormPanel("discussionForm", discussion.getTopic(), discussionService));

        PasswordModalFormPanel passwordModalFormPanel = new PasswordModalFormPanel("passwordForm", userService, discussionService);
        add(passwordModalFormPanel);

        add(new DiscussionListView("discussionList", discussion, passwordModalFormPanel.getPasswordForm(), discussionService));

    }
}
