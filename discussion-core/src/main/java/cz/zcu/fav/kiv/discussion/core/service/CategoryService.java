package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.dao.CategoryDao;
import cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity;

import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class CategoryService {

    private static CategoryDao categoryDao = new CategoryDao();

    public static CategoryEntity createCategory(CategoryEntity entity) {

        return categoryDao.save(entity);

    }

    public static CategoryEntity getCategoryById(long categoryId) {

        return categoryDao.getById(categoryId);
    }

    public static List<CategoryEntity> getCategories() {

        return categoryDao.getCategories();
    }

    public static void removeCategory(CategoryEntity entity) {

        categoryDao.remove(entity);
    }


}
