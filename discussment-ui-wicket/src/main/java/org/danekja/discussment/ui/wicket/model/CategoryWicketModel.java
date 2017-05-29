package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.service.CategoryService;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class CategoryWicketModel extends LoadableDetachableModel<List<Category>> {

    private CategoryService categoryService;

    public CategoryWicketModel(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected List<Category> load() {

        return categoryService.getCategories();
    }

}
