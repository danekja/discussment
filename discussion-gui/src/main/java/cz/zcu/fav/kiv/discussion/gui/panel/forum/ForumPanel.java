package cz.zcu.fav.kiv.discussion.gui.panel.forum;

import cz.zcu.fav.kiv.discussion.core.model.DiscussionModel;
import cz.zcu.fav.kiv.discussion.core.model.UserModel;
import cz.zcu.fav.kiv.discussion.core.service.DiscussionService;
import cz.zcu.fav.kiv.discussion.gui.list.panel.content.ContentListViewPanel;
import cz.zcu.fav.kiv.discussion.gui.list.panel.discussion.DiscussionListViewPanel;
import cz.zcu.fav.kiv.discussion.gui.model.CategoryWicketModel;
import cz.zcu.fav.kiv.discussion.gui.model.DiscussionWicketModel;
import cz.zcu.fav.kiv.discussion.gui.model.TopicWicketModel;
import cz.zcu.fav.kiv.discussion.gui.panel.discussion.DiscussionPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;


/**
 * Created by Martin Bláha on 29.01.17.
 */
public class ForumPanel extends Panel {

    public ForumPanel(String id, PageParameters parameters) {

        super(id);

        long topicId = -1;
        if (!parameters.get("topicId").isNull()) {
            topicId = Long.parseLong(parameters.get("topicId").toString());
        }

        long discussionId = -1;
        if (!parameters.get("discussionId").isNull()) {
            discussionId = Long.parseLong(parameters.get("discussionId").toString());
        }

        if (topicId == -1 && discussionId == -1) {
            add(new ContentListViewPanel("content", new CategoryWicketModel(), new TopicWicketModel()));
        } else if (topicId != -1) {
            add(new DiscussionListViewPanel("content", new DiscussionWicketModel(topicId)));
        } else {
            UserModel user = (UserModel) getSession().getAttribute("user");
            DiscussionModel discussion = DiscussionService.getDiscussionById(discussionId);


            add(new DiscussionPanel("content", discussionId));

            /*if (discussion.getPass() == null || user != null && user.isAdmin() || discussion.getAccessList().contains(user)) {
                //add(new DiscussionPanel("content", discussionId));
            } else {
                if (discussion.getTopic().getId() == 0) {
                    //getResponse()
                }

            }*/
        }
    }
}
