package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.domain.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface contains the service methods for working with the categories.
 */
public interface CategoryService {

    /**
     * Create a new category in the forum
     *
     * @param entity new category
     * @return new category
     */
    @Transactional
    Category createCategory(Category entity) throws AccessDeniedException;

    /**
     * Get a category in the forum based on its id.
     *
     * @param categoryId category id
     * @return category by id
     */
    @Transactional
    Category getCategoryById(long categoryId) throws AccessDeniedException;

    /**
     * Get all categories in the forum
     *
     * @return list of Category
     */
    @Transactional
    List<Category> getCategories() throws AccessDeniedException;

    /**
     * Remove a category in the forum
     *
     * @param entity category to remove
     */
    @Transactional
    void removeCategory(Category entity) throws AccessDeniedException;
}
