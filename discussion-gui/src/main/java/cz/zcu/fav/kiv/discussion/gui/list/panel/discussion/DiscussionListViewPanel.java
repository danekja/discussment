package cz.zcu.fav.kiv.discussion.gui.list.panel.discussion;

import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;
import cz.zcu.fav.kiv.discussion.core.service.TopicService;
import cz.zcu.fav.kiv.discussion.gui.form.panel.discussion.DiscussionModalFormPanel;
import cz.zcu.fav.kiv.discussion.gui.form.panel.password.PasswordModalFormPanel;
import cz.zcu.fav.kiv.discussion.gui.list.DiscussionListView;
import cz.zcu.fav.kiv.discussion.gui.model.DiscussionWicketModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;


/**
 * Created by Martin Bláha on 29.01.17.
 */
public class DiscussionListViewPanel extends Panel {

    private AjaxLink createDiscussion;

    protected void onBeforeRender() {
        super.onBeforeRender();

        UserEntity user = (UserEntity) getSession().getAttribute("user");

        if (user != null && user.getPermissions().isCreateDiscussion()) {
            createDiscussion.setVisible(true);
        } else {
            createDiscussion.setVisible(false);
        }
    }

    public DiscussionListViewPanel(String id, DiscussionWicketModel discussion) {
        super(id);


        add(new Label("topicName", TopicService.getTopicById(discussion.getTopic().getId()).getName()));

        createDiscussion = new AjaxLink("createDiscussion") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {}
        };
        add(createDiscussion);

        add(new DiscussionModalFormPanel("discussionForm", discussion.getTopic()));

        PasswordModalFormPanel passwordModalFormPanel = new PasswordModalFormPanel("passwordForm");
        add(passwordModalFormPanel);

        add(new DiscussionListView("discussionList", discussion, passwordModalFormPanel.getPasswordForm()));

    }
}
