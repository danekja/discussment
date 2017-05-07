package cz.zcu.fav.kiv.discussion.gui.list.panel.topic;

import cz.zcu.fav.kiv.discussion.gui.list.TopicListView;
import cz.zcu.fav.kiv.discussion.gui.model.TopicWicketModel;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by Martin Bláha on 04.02.17.
 */
public class TopicListViewPanel extends Panel {

    public TopicListViewPanel(String id, TopicWicketModel topicWicketModel) {
        super(id);

        add(new TopicListView("topicList", topicWicketModel));
    }
}
