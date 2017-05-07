package cz.zcu.fav.kiv.discussion.gui.form.panel.topic;

import cz.zcu.fav.kiv.discussion.gui.form.TopicForm;
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
