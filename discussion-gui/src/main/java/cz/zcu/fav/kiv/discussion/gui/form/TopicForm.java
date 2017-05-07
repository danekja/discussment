package cz.zcu.fav.kiv.discussion.gui.form;

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
    private long categoryId;


    public TopicForm(String id) {
        super(id);

        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextField("name"));
        add(new TextField("description"));

    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    protected void onSubmit() {

        if (categoryId == -1) {
            TopicService.createTopic(name, description);
        } else {
            TopicService.createTopic(name, description, categoryId);
        }

        categoryId = -1;

    }
}
