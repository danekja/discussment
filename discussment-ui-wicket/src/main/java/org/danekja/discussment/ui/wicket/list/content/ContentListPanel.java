package org.danekja.discussment.ui.wicket.list.content;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.ui.wicket.list.category.CategoryListPanel;
import org.danekja.discussment.ui.wicket.list.topic.TopicListPanel;

import java.util.List;


/**
 * Created by Martin Bláha on 29.01.17.
 *
 * The class creates the panel contains the categories with the topics and the topics without the category.
 */
public class ContentListPanel extends Panel {

    private CategoryService categoryService;
    private TopicService topicService;
    private DiscussionService discussionService;
    private PostService postService;
    private AccessControlService accessControlService;
    private IModel<Category> categoryModel;
    private IModel<List<Category>>  categoryListModel;
    private IModel<List<Topic>> topicListModel;

    /**
     * Constructor for creating a instance of the panel contains the categories with the topics and the topics without the category.
     *
     * @param id id of the element into which the panel is inserted
     * @param categoryListModel model for getting the categories
     * @param topicListModel model for getting the topics
     * @param categoryService instance of the category service
     * @param topicService instance of the topic service
     * @param categoryModel model for setting the selected category
     */
    public ContentListPanel(String id,
                            IModel<List<Category>> categoryListModel,
                            IModel<List<Topic>> topicListModel,
                            IModel<Category> categoryModel,
                            CategoryService categoryService,
                            TopicService topicService,
                            DiscussionService discussionService,
                            PostService postService,
                            AccessControlService accessControlService) {
        super(id);

        this.topicService = topicService;
        this.categoryListModel = categoryListModel;
        this.categoryService = categoryService;
        this.categoryModel = categoryModel;
        this.topicListModel = topicListModel;
        this.accessControlService = accessControlService;
        this.discussionService = discussionService;
        this.postService = postService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(createCategoryAjaxLink());
        add(createTopicAjaxLink());

        if (accessControlService.canViewCategories()) {
            add(new CategoryListPanel("categoryPanel", categoryListModel, categoryModel, categoryService, topicService, discussionService, postService, accessControlService));
        } else {
            add(new EmptyPanel("categoryPanel"));
        }

        if (accessControlService.canViewTopics(categoryService.getDefaultCategory())){
            add(new TopicListPanel("withoutTopicListPanel", topicListModel, topicService, discussionService, postService, accessControlService));
        } else {
            add(new EmptyPanel("withoutTopicListPanel"));
        }
    }

    private AjaxLink createCategoryAjaxLink() {
        return new AjaxLink("createCategory") {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                this.setVisible(accessControlService.canAddCategory());
            }

            @Override
            public void onClick(AjaxRequestTarget target) {
                target.appendJavaScript("$('#categoryModal').modal('show');");
            }
        };
    }

    private AjaxLink createTopicAjaxLink() {
        return new AjaxLink("createTopic") {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                this.setVisible(accessControlService.canAddTopic(categoryService.getDefaultCategory()));
            }

            @Override
            public void onClick(AjaxRequestTarget target) {
                categoryModel.setObject(categoryService.getDefaultCategory());

                target.appendJavaScript("$('#topicModal').modal('show');");
            }
        };
    }
}
