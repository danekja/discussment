package org.danekja.discussment.article.ui.wicket.panel.article;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.article.core.domain.Article;
import org.danekja.discussment.article.core.service.UserService;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostReputationService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.ui.wicket.panel.discussion.DiscussionPanel;

/**
 * The class creates the panel which contains the specific article with its discussion. Can be added to a separate page.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class ArticleTextPanel extends Panel {

    private final IModel<Article> articleModel;
    private final PostService postService;
    private final UserService userService;
    private final AccessControlService accessControlService;
    private final PostReputationService postReputationService;


    /**
     * Constructor for creating the panel which contains the specific article and its discussion.
     *
     * @param id id of the element into which the panel is inserted
     * @param article article in the panel
     * @param postService instance of the post service
     * @param userService instance of the user service
     * @param postReputationService instance of the post reputation service
     * @param accessControlService instance of the access control service
     */
    public ArticleTextPanel(String id,
                            IModel<Article> article,
                            PostService postService,
                            UserService userService,
                            PostReputationService postReputationService,
                            AccessControlService accessControlService){
        super(id);

        this.articleModel = article;
        this.postService = postService;
        this.userService = userService;
        this.postReputationService = postReputationService;
        this.accessControlService = accessControlService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Label articleName = new Label ("articleName", new PropertyModel<String>(articleModel, "name"));
        add(articleName);

        Label articleText = new Label ("articleText", new PropertyModel<String>(articleModel, "articleText"));
        add(articleText);

        add(new DiscussionPanel("discussionPanel",
                new PropertyModel(articleModel, "discussion"),
                new Model<Post>(),
                postService, userService, postReputationService, accessControlService));
    }
}
