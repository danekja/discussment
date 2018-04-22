package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.domain.Category;

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
     * @throws AccessDeniedException when user is not allowed to commit the action
     */
    Category createCategory(Category entity) throws AccessDeniedException;

    /**
     * Get a category in the forum based on its id.
     *
     * @param categoryId category id
     * @return category by id
     * @throws AccessDeniedException when user is not allowed to commit the action
     */
    Category getCategoryById(long categoryId) throws AccessDeniedException;

    /**
     * Gets default category or creates one if there isn't any.
     * Its id can be found in Category.java variable DEFAULT_CATEGORY_ID.
     *
     * @return default category
     */
    Category getDefaultCategory();

    /**
     * Get all categories in the forum
     *
     * @return list of Category
     * @throws AccessDeniedException when user is not allowed to commit the action
     */
    List<Category> getCategories() throws AccessDeniedException;

    /**
     * Remove a category in the forum
     *
     * @param entity category to remove
     * @throws AccessDeniedException when user is not allowed to commit the action
     */
    void removeCategory(Category entity) throws AccessDeniedException;
}
