package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.service.CategoryService;

import java.util.Collections;
import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 *
 * The class for getting the categories via the category service.
 */
public class CategoryWicketModel extends LoadableDetachableModel<List<Category>> {

    private final CategoryService categoryService;

    /**
     * Constructor for creating a instance of getting the categories.
     *
     * @param categoryService instance of the category service
     */
    public CategoryWicketModel(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected List<Category> load() {

        try {
            return categoryService.getCategories();
        } catch (AccessDeniedException e) {
            return Collections.emptyList();
        }
    }

}
