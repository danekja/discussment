package org.danekja.discussment.learning.panel.article;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.learning.domain.Article;
import org.danekja.discussment.ui.wicket.panel.discussion.DiscussionPanel;

/**
 * The class creates the panel which contains the specific article with its discussion. Can be added to a separate page.
 *
 * Date: 30.1.18
 *
 * @author Jiri Kryda
 */
public class ArticleTextPanel extends Panel {

    private IModel<Article> articleModel;
    private IModel<Discussion> discussionModel;

    private PostService postService;


    /**
     * Constructor for creating the panel which contains the specific article and its discussion.
     *
     * @param id id of the element into which the panel is inserted
     * @param article article in the panel
     * @param postService instance of the post service
     */
    public ArticleTextPanel(String id, IModel<Article> article, PostService postService){
        super(id);

        this.articleModel = article;
        this.discussionModel = new Model<Discussion>();

        this.postService = postService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Label articleText = new Label ("articleText", new PropertyModel<String>(articleModel, "articleText"));
        add(articleText);

        discussionModel = new PropertyModel(articleModel, "discussion");
        add(new DiscussionPanel("discussionPanel", discussionModel, postService, new Model<Post>()));
    }
}
