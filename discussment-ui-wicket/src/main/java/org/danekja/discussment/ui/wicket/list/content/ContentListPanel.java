package org.danekja.discussment.ui.wicket.list.content;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.CategoryService;
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
    private AccessControlService accessControlService;

    public ContentListPanel(String id, IModel<List<Category>> categoryListModel, IModel<List<Topic>> topicWicketModel, IModel<Category> categoryModel, CategoryService categoryService, TopicService topicService, AccessControlService accessControlService) {
        super(id);
        this.topicService = topicService;
        this.categoryService = categoryService;
        this.categoryModel = categoryModel;
        this.categoryListModel = categoryListModel;
        this.topicWicketModel = topicWicketModel;
        this.accessControlService = accessControlService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(createCategoryAjaxLink());
        add(createTopicAjaxLink());

        add(new CategoryListPanel("categoryPanel", categoryListModel, categoryModel, categoryService, topicService, accessControlService));
        add(new TopicListPanel("withoutTopicListPanel", topicWicketModel, topicService, accessControlService));
    }

    private AjaxLink createCategoryAjaxLink() {
        return new AjaxLink("createCategory") {
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {}

            @Override
            protected void onConfigure() {
                super.onConfigure();

                this.setVisible(accessControlService.canAddCategory());
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

                this.setVisible(accessControlService.canAddTopic(categoryModel.getObject()));
            }
        };
    }
}
