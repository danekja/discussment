package org.danekja.discussment.ui.wicket.list.content;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.ui.wicket.list.category.CategoryListPanel;
import org.danekja.discussment.ui.wicket.list.topic.TopicListPanel;
import org.danekja.discussment.ui.wicket.panel.accessDenied.AccessDeniedPanel;
import org.danekja.discussment.ui.wicket.panel.notLoggedIn.NotLoggedInPanel;

import java.util.List;


/**
 * Created by Martin Bláha on 29.01.17.
 *
 * The class creates the panel contains the categories with the topics and the topics without the category.
 */
public class ContentListPanel extends Panel {

    private final long CATEGORY_ID = 1;

    private CategoryService categoryService;
    private TopicService topicService;
    private DiscussionService discussionService;
    private PostService postService;
    private AccessControlService accessControlService;
    private DiscussionUserService userService;
    private IModel<Category> categoryModel;
    private IModel<List<Category>>  categoryListModel;
    private IModel<List<Topic>>  topicWicketModel;

    /**
     * Constructor for creating a instance of the panel contains the categories with the topics and the topics without the category.
     *
     * @param id id of the element into which the panel is inserted
     * @param categoryListModel model for getting the categories
     * @param topicWicketModel model for getting the topics
     * @param categoryService instance of the category service
     * @param topicService instance of the topic service
     * @param categoryModel model for setting the selected category
     */
    public ContentListPanel(String id,
                            IModel<List<Category>> categoryListModel,
                            IModel<List<Topic>> topicWicketModel,
                            IModel<Category> categoryModel,
                            CategoryService categoryService,
                            TopicService topicService,
                            DiscussionService discussionService,
                            PostService postService,
                            DiscussionUserService userService,
                            AccessControlService accessControlService) {
        super(id);

        this.topicService = topicService;
        this.categoryListModel = categoryListModel;
        this.categoryService = categoryService;
        this.categoryModel = categoryModel;
        this.topicWicketModel = topicWicketModel;
        this.userService = userService;
        this.accessControlService = accessControlService;
        this.discussionService = discussionService;
        this.postService = postService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(createCategoryAjaxLink());
        add(createTopicAjaxLink());

        try {
            add(new CategoryListPanel("categoryPanel", categoryListModel, categoryModel, categoryService, topicService, discussionService, postService, accessControlService));
        } catch (NullPointerException e) {
            add(new NotLoggedInPanel("categoryPanel"));
        }
        try {
            if(accessControlService.canViewTopics(categoryService.getDefaultCategory())){
                add(new TopicListPanel("withoutTopicListPanel", topicWicketModel, topicService, discussionService, postService, accessControlService));
            } else {
                Panel adp = new AccessDeniedPanel("withoutTopicListPanel");
                adp.setVisible(false);
                add(adp);
            }
        } catch (NullPointerException e) {
            add(new NotLoggedInPanel("withoutTopicListPanel"));
        }
    }

    private AjaxLink createCategoryAjaxLink() {
        return new AjaxLink("createCategory") {
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {}

            @Override
            protected void onConfigure() {
                super.onConfigure();

                IDiscussionUser user = userService.getCurrentlyLoggedUser();
                this.setVisible(user != null && accessControlService.canAddCategory());
            }
        };
    }

    private AjaxLink createTopicAjaxLink() {
        return new AjaxLink("createTopic") {
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                categoryModel.setObject(categoryService.getDefaultCategory());
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                IDiscussionUser user = userService.getCurrentlyLoggedUser();
                try {
                    this.setVisible(user != null && accessControlService.canAddTopic(categoryService.getCategoryById(CATEGORY_ID)));
                } catch (AccessDeniedException e) {
                    e.printStackTrace();
                    this.setVisible(false);
                }
            }
        };
    }
}
