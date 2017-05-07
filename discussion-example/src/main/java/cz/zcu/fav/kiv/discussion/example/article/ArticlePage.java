package cz.zcu.fav.kiv.discussion.example.article;

import cz.zcu.fav.kiv.discussion.core.model.DiscussionModel;
import cz.zcu.fav.kiv.discussion.core.service.DiscussionService;
import cz.zcu.fav.kiv.discussion.example.base.BasePage;
import cz.zcu.fav.kiv.discussion.gui.panel.discussion.DiscussionPanel;
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

        DiscussionModel discussion = DiscussionService.getDiscussionById(DISCUSSION_ID);


        if (discussion== null) {
            discussion = DiscussionService.createDiscussion("article name");
        }

        add(new DiscussionPanel("content", discussion.getId()));


    }

    @Override
    public String getTitle() {
        return "Article page";
    }
}
