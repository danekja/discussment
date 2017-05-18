package org.danekja.discussment.ui.wicket.list.content;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.ICategoryService;
import org.danekja.discussment.core.service.ITopicService;
import org.danekja.discussment.ui.wicket.list.category.CategoryListPanel;
import org.danekja.discussment.ui.wicket.list.topic.TopicListPanel;
import org.danekja.discussment.ui.wicket.model.CategoryWicketModel;
import org.danekja.discussment.ui.wicket.model.TopicWicketModel;


/**
 * Created by Martin Bláha on 29.01.17.
 */
public class ContentListPanel extends Panel {

    private ITopicService topicService;
    private ICategoryService categoryService;
    private IModel<Category> categoryModel;
    private CategoryWicketModel categoryWicketModel;
    private TopicWicketModel topicWicketModel;

    public ContentListPanel(String id, CategoryWicketModel categoryWicketModel, TopicWicketModel topicWicketModel, final ICategoryService categoryService, ITopicService topicService, IModel<Category> categoryModel) {
        super(id);

        this.topicService = topicService;
        this.categoryWicketModel = categoryWicketModel;
        this.categoryService = categoryService;
        this.categoryModel = categoryModel;
        this.topicWicketModel = topicWicketModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(createCategoryAjaxLink());
        add(createTopicAjaxLink());

        add(new CategoryListPanel("categoryPanel", categoryWicketModel, categoryModel, categoryService, topicService));
        add(new TopicListPanel("withoutTopicListPanel", topicWicketModel, topicService));
    }

    private AjaxLink createCategoryAjaxLink() {
        return new AjaxLink("createCategory") {
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {}

            @Override
            protected void onConfigure() {
                super.onConfigure();

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isCreateCategory());
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

                User user = (User) getSession().getAttribute("user");
                this.setVisible(user != null && user.getPermissions().isCreateTopic());
            }
        };
    }
}
