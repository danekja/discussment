package org.danekja.discussment.ui.wicket.form.panel.reply;

import org.danekja.discussment.ui.wicket.form.ReplyForm;
import org.apache.wicket.markup.html.panel.Panel;


/**
 * Created by Martin Bláha on 29.01.17.
 */
public class ReplyModalFormPanel extends Panel {

    private ReplyForm replyForm;

    public ReplyModalFormPanel(String id) {
        super(id);

        replyForm = new ReplyForm("replyForm");
        add(replyForm);

    }

    public ReplyForm getReplyForm() {
        return replyForm;
    }
}

