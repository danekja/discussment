package org.danekja.discussment.ui.wicket.list.panel.category;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.service.ICategoryService;
import org.danekja.discussment.core.service.ITopicService;
import org.danekja.discussment.ui.wicket.form.panel.topic.TopicModalFormPanel;
import org.danekja.discussment.ui.wicket.list.CategoryListView;
import org.danekja.discussment.ui.wicket.list.panel.topic.TopicListViewPanel;
import org.danekja.discussment.ui.wicket.model.CategoryWicketModel;
import org.danekja.discussment.ui.wicket.model.TopicWicketModel;

/**
 * Created by Martin Bláha on 04.02.17.
 */
public class CategoryListViewPanel extends Panel {


    public CategoryListViewPanel(String id, CategoryWicketModel categoryWicketModel, TopicModalFormPanel topicModalFormPanel, ICategoryService categoryService, final ITopicService topicService) {
        super(id);

        CategoryListView categoryListView = new CategoryListView("categoryList", categoryWicketModel, topicModalFormPanel.getTopicForm(), categoryService) {
            @Override
            protected void populateItem(ListItem<Category> listItem) {
                super.populateItem(listItem);

                Category category = listItem.getModelObject();

                TopicListViewPanel topicListViewPanel = new TopicListViewPanel("topicListPanel", new TopicWicketModel(category, topicService), topicService);
                topicListViewPanel.setOutputMarkupId(true);

                int generateId = getGenerateId();
                topicListViewPanel.setMarkupId("id" + generateId);

                setGenerateId(++generateId);

                listItem.add(topicListViewPanel);
            }
        };

        add(categoryListView);
    }
}
