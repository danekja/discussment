package org.danekja.discussment.example.article;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.example.base.BasePage;
import org.danekja.discussment.ui.wicket.panel.discussion.DiscussionPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;


/**
 * Homepage
 */
public class ArticlePage extends BasePage {

	private static final long serialVersionUID = 1L;

	private static final long DISCUSSION_ID = 1;

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public ArticlePage(final PageParameters parameters) {

        Discussion discussion = DiscussionService.getDiscussionById(DISCUSSION_ID);


        if (discussion== null) {
            discussion = DiscussionService.createDiscussion(new Discussion("article name"));
        }

        add(new DiscussionPanel("content", discussion));


    }

    @Override
    public String getTitle() {
        return "Article page";
    }
}
