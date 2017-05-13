package org.danekja.discussment.ui.wicket.model;

import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.service.CategoryService;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class CategoryWicketModel implements IModel<List<Category>> {

    public void detach() {
    }

    public List<Category> getObject() {
        return CategoryService.getCategories();
    }

    public void setObject(List<Category> object) {
    }
}
