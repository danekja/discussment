package org.danekja.discussment.ui.wicket.form;

import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.service.CategoryService;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class CategoryForm extends Form {

    private String name;

    public CategoryForm(String id) {
        super(id);

        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextField("name"));
    }

    @Override
    protected void onSubmit() {

        CategoryService.createCategory(new Category(name));

    }
}
