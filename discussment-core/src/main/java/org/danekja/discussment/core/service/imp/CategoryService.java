package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.dao.CategoryDao;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.service.ICategoryService;

import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class CategoryService implements ICategoryService {

    private CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public Category createCategory(Category entity) {

        return categoryDao.save(entity);

    }

    public Category getCategoryById(long categoryId) {

        return categoryDao.getById(categoryId);
    }

    public List<Category> getCategories() {

        return categoryDao.getCategories();
    }

    public void removeCategory(Category entity) {

        categoryDao.remove(entity);
    }


}
