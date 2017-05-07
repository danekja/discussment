package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.dao.CategoryDao;
import cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity;
import cz.zcu.fav.kiv.discussion.core.model.CategoryModel;
import cz.zcu.fav.kiv.discussion.core.utils.MapUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class CategoryService {

    private static CategoryDao categoryDao = new CategoryDao();

    public static CategoryModel createCategory(String name) {

        CategoryEntity entity = new CategoryEntity(name);

        return MapUtil.mapCategoryEntityToModel(categoryDao.save(entity));

    }

    public static CategoryModel getCategoryById(long categoryId) {

        return MapUtil.mapCategoryEntityToModel(categoryDao.getById(categoryId));

    }

    public static List<CategoryModel> getCategories() {

        List<CategoryModel> models = new ArrayList<CategoryModel>();

        List<CategoryEntity> entities = categoryDao.getCategories();

        for (CategoryEntity entity: entities) {
            models.add(MapUtil.mapCategoryEntityToModel(entity));
        }

        return models;
    }

    public static void removeCategoryById(long categoryId) {
        CategoryEntity categoryEntity = categoryDao.getById(categoryId);
        categoryDao.remove(categoryEntity);
    }


}
