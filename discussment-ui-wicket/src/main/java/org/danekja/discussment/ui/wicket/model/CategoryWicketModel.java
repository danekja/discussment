package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.service.ICategoryService;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class CategoryWicketModel implements IModel<List<Category>> {

    private ICategoryService categoryService;

    public CategoryWicketModel(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void detach() {
    }

    public List<Category> getObject() {
        return categoryService.getCategories();
    }

    public void setObject(List<Category> object) {
    }
}
