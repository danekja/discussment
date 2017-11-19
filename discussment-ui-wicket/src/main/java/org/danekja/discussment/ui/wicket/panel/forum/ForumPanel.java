package org.danekja.discussment.ui.wicket.panel.forum;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.*;
import org.danekja.discussment.ui.wicket.form.*;
import org.danekja.discussment.ui.wicket.list.content.ContentListPanel;
import org.danekja.discussment.ui.wicket.list.discussion.DiscussionListPanel;
import org.danekja.discussment.ui.wicket.model.CategoryWicketModel;
import org.danekja.discussment.ui.wicket.model.TopicWicketModel;
import org.danekja.discussment.ui.wicket.panel.discussion.DiscussionPanel;
import org.danekja.discussment.ui.wicket.session.SessionUtil;

import java.util.HashMap;


/**
 * Created by Martin Bláha on 29.01.17.
 *
 * The class creates the panel which contains the forum. The forum can be added to a separate page.
 */
public class ForumPanel extends Panel {

    private IModel<HashMap<String, Integer>> parametersModel;

    private CategoryService categoryService;
    private PostService postService;
    private TopicService topicService;
    private DiscussionService discussionService;
    private PermissionService permissionService;

    private IModel<Category> categoryModel;
    private IModel<Discussion> discussionModel;
    private IModel<Topic> topicModel;
    private IModel<Post> postModel;

    /**
     * Constructor for creating the panel which contains the forum.
     *
     * @param id id of the element into which the panel is inserted
     * @param parametersModel variable contains a map of the page parameters
     * @param discussionService instance of the discussion service
     * @param topicService instance of the topic service
     * @param categoryService instance of the category service
     * @param postService instance of the post service
     */
    public ForumPanel(String id, IModel<HashMap<String, Integer>> parametersModel, DiscussionService discussionService, TopicService topicService, CategoryService categoryService, PostService postService, PermissionService permissionService) {
        super(id);

        this.parametersModel = parametersModel;

        this.categoryService = categoryService;
        this.postService = postService;
        this.topicService = topicService;
        this.discussionService = discussionService;
        this.permissionService = permissionService;

        this.categoryModel = new Model<Category>();
        this.discussionModel = new Model<Discussion>();
        this.topicModel = new Model<Topic>();
        this.postModel = new Model<Post>();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new CategoryForm("categoryForm", categoryService, new Model<Category>(new Category())));
        add(new ReplyForm("replyForm", postService, postModel, new Model<Post>(new Post())));
        add(new TopicForm("topicForm", topicService, categoryModel, new Model<Topic>(new Topic())));
        add(new DiscussionForm("discussionForm", discussionService, topicModel, new Model<Discussion>(new Discussion())));
        add(new PasswordForm("passwordForm", discussionService, discussionModel, new Model<Discussion>(new Discussion())));


        if (parametersModel.getObject().get("topicId") == -1 && parametersModel.getObject().get("discussionId") == -1) {
            add(new ContentListPanel("content",
                new CategoryWicketModel(categoryService),
                new TopicWicketModel(topicService), categoryService, topicService, categoryModel, permissionService)
            );

        } else if (parametersModel.getObject().get("topicId") != -1) {
            Topic topic = topicService.getTopicById(parametersModel.getObject().get("topicId"));
            topicModel.setObject(topic);

            add(new DiscussionListPanel("content", topicModel, discussionService,discussionModel, permissionService));
        } else {

            Discussion discussion = discussionService.getDiscussionById(parametersModel.getObject().get("discussionId"));

            Long dId = SessionUtil.getDiscussionId();
            Boolean access = SessionUtil.getAccess();
            if(discussionService.hasCurrentUserAccessToDiscussion(discussion) ||
                    access != null && access.booleanValue() && dId != null && dId.equals(discussion.getId())) {
                add(new DiscussionPanel("content", new Model<Discussion>(discussion), postService, postModel, permissionService));
            } else {
                setResponsePage(getPage().getClass());
            }
        }

    }


}
