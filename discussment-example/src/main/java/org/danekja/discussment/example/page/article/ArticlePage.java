package org.danekja.discussment.example.page.article;

import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.core.dao.jpa.PostDaoJPA;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.core.service.imp.DefaultDiscussionService;
import org.danekja.discussment.core.service.imp.DefaultPostService;
import org.danekja.discussment.example.WicketApplication;
import org.danekja.discussment.example.page.base.BasePage;
import org.danekja.discussment.ui.wicket.panel.discussion.DiscussionPanel;

import javax.persistence.EntityManager;


/**
 * Homepage
 */
public class ArticlePage extends BasePage {

	private static final long serialVersionUID = 1L;

    private EntityManager em;

	private static final long DISCUSSION_ID = 0;

	private DiscussionService discussionService;
	private PostService postService;

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public ArticlePage(final PageParameters parameters) {
        this.em = WicketApplication.factory.createEntityManager();

        this.discussionService = new DefaultDiscussionService(new DiscussionDaoJPA(em));
        this.postService = new DefaultPostService(new PostDaoJPA(em));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Discussion discussion = discussionService.getDiscussionById(DISCUSSION_ID);

        if (discussion == null) {
            discussion = discussionService.createDiscussion(new Discussion("article name"));
        }

        add(new DiscussionPanel("content", new Model<Discussion>(discussion), postService, new Model<Post>()));
    }

    @Override
    public String getTitle() {
        return "Article page";
    }
}
