package org.danekja.discussment.ui.wicket.list.content;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Permission;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.core.service.PermissionService;
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

    private TopicService topicService;
    private CategoryService categoryService;
    private IModel<Category> categoryModel;
    private IModel<List<Category>>  categoryListModel;
    private IModel<List<Topic>>  topicWicketModel;
    private PermissionService permissionService;

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
    public ContentListPanel(String id, IModel<List<Category>> categoryListModel, IModel<List<Topic>> topicWicketModel, CategoryService categoryService, TopicService topicService, IModel<Category> categoryModel, PermissionService permissionService) {
        super(id);

        this.topicService = topicService;
        this.categoryListModel = categoryListModel;
        this.categoryService = categoryService;
        this.categoryModel = categoryModel;
        this.topicWicketModel = topicWicketModel;
        this.permissionService = permissionService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(createCategoryAjaxLink());
        add(createTopicAjaxLink());

        add(new CategoryListPanel("categoryPanel", categoryListModel, categoryModel, categoryService, topicService, permissionService));
        add(new TopicListPanel("withoutTopicListPanel", topicWicketModel, topicService, permissionService));
    }

    private AjaxLink createCategoryAjaxLink() {
        return new AjaxLink("createCategory") {
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {}

            @Override
            protected void onConfigure() {
                super.onConfigure();

                Permission p = permissionService.getCurrentlyLoggedUsersPermission();
                this.setVisible(p != null && p.isCreateCategory());
            }
        };
    }

    private AjaxLink createTopicAjaxLink() {
        return new AjaxLink("createTopic") {
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                categoryModel.setObject(categoryService.getCategoryById(Category.WITHOUT_CATEGORY));
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                Permission p = permissionService.getCurrentlyLoggedUsersPermission();
                this.setVisible(p != null && p.isCreateTopic());
            }
        };
    }
}
