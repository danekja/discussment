package org.danekja.discussment.ui.wicket.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.service.ICategoryService;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class CategoryForm extends Form {

    private ICategoryService categoryService;

    private String name;

    public CategoryForm(String id, ICategoryService categoryService) {
        super(id);

        this.categoryService = categoryService;

        setDefaultModel(new CompoundPropertyModel(this));

        add(new TextField("name"));
    }

    @Override
    protected void onSubmit() {

        categoryService.createCategory(new Category(name));

    }
}
