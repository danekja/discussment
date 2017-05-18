package org.danekja.discussment.ui.wicket.form.category;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Category;

/**
 * Created by Martin Bláha on 15.05.17.
 */
public class CategoryFormComponent extends FormComponentPanel {

    private TextField<String> name;

    private IModel<Category> categoryModel;

    public CategoryFormComponent(String id, IModel<Category> categoryModel) {
        super(id, categoryModel);

        this.categoryModel = categoryModel;

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        name = new TextField<String>("name", new Model<String>(""));
        add(name);

    }

    @Override
    public void updateModel() {
        super.updateModel();

        categoryModel.setObject(new Category(name.getModelObject()));

    }
}
