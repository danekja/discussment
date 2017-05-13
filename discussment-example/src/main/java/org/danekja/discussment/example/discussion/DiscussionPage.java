package org.danekja.discussment.example.discussion;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.example.base.BasePage;
import org.danekja.discussment.ui.wicket.panel.forum.ForumPanel;


/**
 * Homepage
 */
public class DiscussionPage extends BasePage {

	private static final long serialVersionUID = 1L;

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public DiscussionPage(final PageParameters parameters) {

        add(new ForumPanel("content", parameters));

    }

    @Override
    public String getTitle() {
        return "Post page";
    }
}

