package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.service.CategoryService;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class CategoryWicketModel extends LoadableDetachableModel {

    private CategoryService categoryService;

    public CategoryWicketModel(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected Object load() {

        return categoryService.getCategories();
    }

}
