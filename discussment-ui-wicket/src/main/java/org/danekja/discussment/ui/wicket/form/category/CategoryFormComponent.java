package org.danekja.discussment.ui.wicket.form.category;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.danekja.discussment.core.domain.Category;

/**
 * Created by Martin Bláha on 15.05.17.
 *
 * The class contains the input field for getting a name
 */
public class CategoryFormComponent extends Panel {

    /**
     * Constructor for creating a instance of getting a name of the category.
     *
     * @param id id of the element into which the panel is inserted
     * @param categoryModel variable contains the category for setting the name
     */
    public CategoryFormComponent(String id, IModel<Category> categoryModel) {
        super(id, categoryModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextField<String> name = new TextField<>("name", new PropertyModel<String>(getDefaultModel(), "name"));
        name.setRequired(true);
        add(name);

    }
}
