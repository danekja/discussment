package org.danekja.discussment.ui.wicket.list.panel.topic;

import org.danekja.discussment.ui.wicket.list.TopicListView;
import org.danekja.discussment.ui.wicket.model.TopicWicketModel;
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
