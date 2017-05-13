package cz.zcu.fav.kiv.discussion.gui.form;

import cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity;
import cz.zcu.fav.kiv.discussion.core.entity.TopicEntity;
import cz.zcu.fav.kiv.discussion.core.service.CategoryService;
import cz.zcu.fav.kiv.discussion.core.service.TopicService;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class TopicForm extends Form {

    private String name;
    private String description;
    private CategoryEntity categoryEntity;


    public TopicForm(String id) {
        super(id);

        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextField("name"));
        add(new TextField("description"));

    }

    public CategoryEntity getCategory() {
        return categoryEntity;
    }

    public void setCategory(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    @Override
    protected void onSubmit() {

        TopicEntity topic = new TopicEntity();
        topic.setName(name);
        topic.setDescription(description);

        if (categoryEntity == null) {
            categoryEntity = CategoryService.getCategoryById(CategoryEntity.WITHOUT_CATEGORY);
        }

        TopicService.createTopic(topic, categoryEntity);

    }
}
