package cz.zcu.fav.kiv.discussion.gui.list.panel.category;

import cz.zcu.fav.kiv.discussion.core.model.CategoryModel;
import cz.zcu.fav.kiv.discussion.gui.form.panel.topic.TopicModalFormPanel;
import cz.zcu.fav.kiv.discussion.gui.list.CategoryListView;
import cz.zcu.fav.kiv.discussion.gui.list.panel.topic.TopicListViewPanel;
import cz.zcu.fav.kiv.discussion.gui.model.CategoryWicketModel;
import cz.zcu.fav.kiv.discussion.gui.model.TopicWicketModel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Created by Martin Bláha on 04.02.17.
 */
public class CategoryListViewPanel extends Panel {


    public CategoryListViewPanel(String id, CategoryWicketModel categoryWicketModel, TopicModalFormPanel topicModalFormPanel ) {
        super(id);

        CategoryListView categoryListView = new CategoryListView("categoryList", categoryWicketModel, topicModalFormPanel.getTopicForm()) {
            @Override
            protected void populateItem(ListItem<CategoryModel> listItem) {
                super.populateItem(listItem);

                CategoryModel category = listItem.getModelObject();

                TopicListViewPanel topicListViewPanel = new TopicListViewPanel("topicListPanel", new TopicWicketModel(category.getId()));
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
