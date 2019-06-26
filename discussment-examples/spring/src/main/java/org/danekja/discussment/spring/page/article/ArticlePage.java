package org.danekja.discussment.spring.page.article;

import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PostReputationService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.spring.core.service.UserService;
import org.danekja.discussment.spring.page.base.BasePage;
import org.danekja.discussment.ui.wicket.panel.discussion.DiscussionPanel;


/**
 * Homepage
 */
public class ArticlePage extends BasePage {

	private static final long serialVersionUID = 1L;

	private static final long DISCUSSION_ID = 1;
    private static final long TOPIC_ID = 1;

    @SpringBean
	private DiscussionService discussionService;

    @SpringBean
	private TopicService topicService;

    @SpringBean
	private PostService postService;

    @SpringBean
	private AccessControlService accessControlService;

    @SpringBean
	private UserService userService;

    @SpringBean
	private PostReputationService postReputationService;

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public ArticlePage(final PageParameters parameters) {

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        try {
            Discussion discussion = discussionService.getDiscussionById(DISCUSSION_ID);
            if (discussion == null) {
                Topic topic = topicService.getTopicById(TOPIC_ID);
                discussion = discussionService.createDiscussion(topic, new Discussion("article name"));
            }
            add(new DiscussionPanel("content", new Model<Discussion>(discussion), new Model<Post>(), postService, userService, postReputationService, accessControlService));
        } catch (AccessDeniedException e) {
            add(new EmptyPanel("content"));
        }
    }

    @Override
    public String getTitle() {
        return "Article page";
    }
}
