package org.danekja.discussment.ui.wicket.list.category;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.ui.wicket.list.topic.TopicListPanel;
import org.danekja.discussment.ui.wicket.model.TopicWicketModel;

import java.util.List;

/**
 * Created by Martin Bláha on 04.02.17.
 */
public class CategoryListPanel extends Panel {


    private static int generateId = 0;

    private CategoryService categoryService;
    private TopicService topicService;
    private IModel<Category> categoryModel;
    private IModel<List<Category>> categoryListModel;

    public CategoryListPanel(String id, IModel<List<Category>> categoryListModel, IModel<Category> categoryModel, CategoryService categoryService, TopicService topicService) {
        super(id);

        this.topicService = topicService;
        this.categoryListModel = categoryListModel;
        this.categoryService = categoryService;
        this.categoryModel = categoryModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ListView<Category>("categoryList", categoryListModel) {
            protected void populateItem(ListItem<Category> listItem) {
                Category category = listItem.getModelObject();

                listItem.add(createCategoryHeader(category));
                listItem.add(createTopicListViewPanel(category));

                generateId++;
            }
        });
    }

    private TopicListPanel createTopicListViewPanel(Category category) {
        TopicListPanel topicListViewPanel = new TopicListPanel("topicListPanel", new TopicWicketModel(new Model<Category>(category), topicService), topicService);
        topicListViewPanel.setOutputMarkupId(true);
        topicListViewPanel.setMarkupId("id" + generateId);

        return topicListViewPanel;
    }

    private WebMarkupContainer createCategoryHeader(Category category) {
        WebMarkupContainer categoryHeader = new WebMarkupContainer("categoryHeader");

        WebMarkupContainer categoryIcon = new WebMarkupContainer("categoryIcon");
        categoryIcon.add(new AttributeAppender("data-target", "id" + generateId));

        categoryHeader.add(categoryIcon);
        categoryHeader.add(new Label("categoryName", category.getName()));
        categoryHeader.add(createNewTopicAjaxLink(category));
        categoryHeader.add(createRemoveCategoryLink(category));

        return categoryHeader;
    }

    private AjaxLink createNewTopicAjaxLink(final Category category) {
        return new AjaxLink("newTopic") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                categoryModel.setObject(category);
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isCreateTopic());
            }
        };
    }

    private Link createRemoveCategoryLink(final Category category) {
        return new Link("remove") {
            @Override
            public void onClick() {
                categoryService.removeCategory(category);
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isRemoveCategory());
            }
        };
    }

}
