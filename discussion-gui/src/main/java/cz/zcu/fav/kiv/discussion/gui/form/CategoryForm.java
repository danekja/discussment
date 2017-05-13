package cz.zcu.fav.kiv.discussion.gui.form;

import cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity;
import cz.zcu.fav.kiv.discussion.core.service.CategoryService;
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

        CategoryService.createCategory(new CategoryEntity(name));

    }
}
