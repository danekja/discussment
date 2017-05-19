package org.danekja.discussment.ui.wicket.panel.forum;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.*;
import org.danekja.discussment.core.service.*;
import org.danekja.discussment.ui.wicket.form.*;
import org.danekja.discussment.ui.wicket.list.content.ContentListPanel;
import org.danekja.discussment.ui.wicket.list.discussion.DiscussionListPanel;
import org.danekja.discussment.ui.wicket.model.CategoryWicketModel;
import org.danekja.discussment.ui.wicket.model.TopicWicketModel;
import org.danekja.discussment.ui.wicket.panel.discussion.DiscussionPanel;

import java.util.HashMap;


/**
 * Created by Martin Bláha on 29.01.17.
 */
public class ForumPanel extends Panel {

    private IModel<HashMap<String, Integer>> parametersModel;

    private CategoryService categoryService;
    private PostService postService;
    private TopicService topicService;
    private DiscussionService discussionService;
    private UserService userService;

    private IModel<Category> categoryModel;
    private IModel<Discussion> discussionModel;
    private IModel<Topic> topicModel;
    private IModel<Post> postModel;

    public ForumPanel(String id, IModel<HashMap<String, Integer>> parametersModel, DiscussionService discussionService, TopicService topicService, CategoryService categoryService, PostService postService, UserService userService) {
        super(id);

        this.parametersModel = parametersModel;

        this.categoryService = categoryService;
        this.postService = postService;
        this.topicService = topicService;
        this.discussionService = discussionService;
        this.userService = userService;

        this.categoryModel = new Model<Category>();
        this.discussionModel = new Model<Discussion>();
        this.topicModel = new Model<Topic>();
        this.postModel = new Model<Post>();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new CategoryForm("categoryForm", categoryService));
        add(new ReplyForm("replyForm", postService, postModel, new Model<Post>(new Post())));
        add(new TopicForm("topicForm", topicService, categoryModel));
        add(new DiscussionForm("discussionForm", discussionService, topicModel));
        add(new PasswordForm("passwordForm", userService, discussionModel));


        if (parametersModel.getObject().get("topicId") == -1 && parametersModel.getObject().get("discussionId") == -1) {
            add(new ContentListPanel("content",
                new CategoryWicketModel(categoryService),
                new TopicWicketModel(topicService), categoryService, topicService, categoryModel)
            );

        } else if (parametersModel.getObject().get("topicId") != -1) {
            Topic topic = topicService.getTopicById(parametersModel.getObject().get("topicId"));
            topicModel.setObject(topic);

            add(new DiscussionListPanel("content", topic, discussionService,discussionModel));
        } else {
            Discussion discussion = discussionService.getDiscussionById(parametersModel.getObject().get("discussionId"));

            User user = (User) getSession().getAttribute("user");

            if (user.isAccessToDiscussion(discussion)) {
                add(new DiscussionPanel("content", new Model<Discussion>(discussion), postService, postModel));
            } else {
                setResponsePage(getPage().getClass());
            }
        }

    }


}
