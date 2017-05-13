package cz.zcu.fav.kiv.discussion.gui.list;

import cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity;
import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;
import cz.zcu.fav.kiv.discussion.core.service.CategoryService;
import cz.zcu.fav.kiv.discussion.gui.form.TopicForm;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by Martin Bláha on 03.02.17.
 */
public class CategoryListView extends ListView<CategoryEntity> {

    private TopicForm topicForm;

    private int generateId = 0;

    public CategoryListView(String id, IModel<? extends List<CategoryEntity>> model, TopicForm topicForm) {
        super(id, model);

        this.topicForm = topicForm;
    }

    protected void populateItem(ListItem<CategoryEntity> listItem) {
        final CategoryEntity category = listItem.getModelObject();

        WebMarkupContainer categoryHeader = new WebMarkupContainer("categoryHeader");
        listItem.add(categoryHeader);

        WebMarkupContainer categoryIcon = new WebMarkupContainer("categoryIcon");
        categoryIcon.add(new AttributeAppender("data-target", "id" + generateId));
        categoryHeader.add(categoryIcon);

        categoryHeader.add(new Label("categoryName", category.getName()));
        AjaxLink newTopic = new AjaxLink("newTopic") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                topicForm.setCategory(category);
            }
        };
        categoryHeader.add(newTopic);


        Link removeCategory = new Link("remove") {
            @Override
            public void onClick() {
                CategoryService.removeCategory(category);
            }
        };
        categoryHeader.add(removeCategory);


        UserEntity user = (UserEntity) getSession().getAttribute("user");

        if (user != null && user.getPermissions().isRemoveCategory()) {
            removeCategory.setVisible(true);
        } else {
            removeCategory.setVisible(false);
        }

        if (user != null && user.getPermissions().isCreateTopic()) {
            newTopic.setVisible(true);
        } else {
            newTopic.setVisible(false);
        }
    }

    public int getGenerateId() {
        return generateId;
    }

    public void setGenerateId(int generateId) {
        this.generateId = generateId;
    }
}
