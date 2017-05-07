package cz.zcu.fav.kiv.discussion.example.discussion;

import cz.zcu.fav.kiv.discussion.example.base.BasePage;
import cz.zcu.fav.kiv.discussion.gui.panel.forum.ForumPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;


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

