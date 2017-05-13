package org.danekja.discussment.ui.wicket.form;

import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.core.service.TopicService;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class TopicForm extends Form {

    private String name;
    private String description;
    private Category category;


    public TopicForm(String id) {
        super(id);

        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextField("name"));
        add(new TextField("description"));

    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    protected void onSubmit() {

        Topic topic = new Topic();
        topic.setName(name);
        topic.setDescription(description);

        if (category == null) {
            category = CategoryService.getCategoryById(Category.WITHOUT_CATEGORY);
        }

        TopicService.createTopic(topic, category);

    }
}
