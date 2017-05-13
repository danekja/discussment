package org.danekja.discussment.example.article;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.dao.jpa.DiscussionJPA;
import org.danekja.discussment.core.dao.jpa.PostJPA;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.service.IDiscussionService;
import org.danekja.discussment.core.service.IPostService;
import org.danekja.discussment.core.service.imp.DiscussionService;
import org.danekja.discussment.core.service.imp.PostService;
import org.danekja.discussment.example.base.BasePage;
import org.danekja.discussment.ui.wicket.panel.discussion.DiscussionPanel;


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

        IDiscussionService discussionService = new DiscussionService(new DiscussionJPA());
        IPostService postService = new PostService(new PostJPA());

        Discussion discussion = discussionService.getDiscussionById(DISCUSSION_ID);


        if (discussion == null) {
            discussion = discussionService.createDiscussion(new Discussion("article name"));
        }

        add(new DiscussionPanel("content", discussion, postService));


    }

    @Override
    public String getTitle() {
        return "Article page";
    }
}
