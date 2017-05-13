package org.danekja.discussment.ui.wicket.panel.forum;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.ui.wicket.list.panel.content.ContentListViewPanel;
import org.danekja.discussment.ui.wicket.list.panel.discussion.DiscussionListViewPanel;
import org.danekja.discussment.ui.wicket.model.CategoryWicketModel;
import org.danekja.discussment.ui.wicket.model.DiscussionWicketModel;
import org.danekja.discussment.ui.wicket.model.TopicWicketModel;
import org.danekja.discussment.ui.wicket.panel.discussion.DiscussionPanel;
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
            add(new DiscussionListViewPanel("content", new DiscussionWicketModel(TopicService.getTopicById(topicId))));
        } else {
            User user = (User) getSession().getAttribute("user");
            Discussion discussion = DiscussionService.getDiscussionById(discussionId);


            add(new DiscussionPanel("content", discussion));

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
