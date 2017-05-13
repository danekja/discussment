package org.danekja.discussment.core.service;

import org.danekja.discussment.core.domain.Category;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 */
public interface ICategoryService {
    Category createCategory(Category entity);

    Category getCategoryById(long categoryId);

    List<Category> getCategories();

    void removeCategory(Category entity);
}
