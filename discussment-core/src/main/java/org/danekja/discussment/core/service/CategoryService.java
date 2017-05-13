package org.danekja.discussment.core.service;

import org.danekja.discussment.core.dao.ICategoryDao;
import org.danekja.discussment.core.dao.jpa.CategoryJPA;
import org.danekja.discussment.core.domain.Category;

import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class CategoryService {

    private static ICategoryDao categoryJPA = new CategoryJPA();

    public static Category createCategory(Category entity) {

        return categoryJPA.save(entity);

    }

    public static Category getCategoryById(long categoryId) {

        return categoryJPA.getById(categoryId);
    }

    public static List<Category> getCategories() {

        return categoryJPA.getCategories();
    }

    public static void removeCategory(Category entity) {

        categoryJPA.remove(entity);
    }


}
