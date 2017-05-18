package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.service.ICategoryService;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class CategoryWicketModel extends LoadableDetachableModel {

    private ICategoryService categoryService;

    public CategoryWicketModel(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected Object load() {

        return categoryService.getCategories();
    }

}
