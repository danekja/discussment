package org.danekja.discussment.ui.wicket.form.panel.topic;

import org.danekja.discussment.ui.wicket.form.TopicForm;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class TopicModalFormPanel extends Panel {


    private TopicForm topicForm;

    public TopicModalFormPanel(String id) {
        super(id);

        topicForm = new TopicForm("topicForm");
        add(topicForm);

    }

    public TopicForm getTopicForm() {
        return topicForm;
    }
}
