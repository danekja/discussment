package org.danekja.discussment.ui.wicket.form.panel.discussion;

import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.ui.wicket.form.DiscussionForm;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class DiscussionModalFormPanel extends Panel {


    private DiscussionForm discussionForm;

    public DiscussionModalFormPanel(String id,  Topic topic) {
        super(id);

        discussionForm = new DiscussionForm("discussionForm", topic);
        add(discussionForm);

    }


}
