package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.ICategoryService;
import org.danekja.discussment.core.service.ITopicService;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class TopicForm extends Form {

    private ICategoryService categoryService;
    private ITopicService topicService;

    private String name;
    private String description;
    private Category category;


    public TopicForm(String id, ICategoryService categoryService, ITopicService topicService) {
        super(id);

        this.categoryService = categoryService;
        this.topicService = topicService;

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
            category = categoryService.getCategoryById(Category.WITHOUT_CATEGORY);
        }

        topicService.createTopic(topic, category);

    }
}
