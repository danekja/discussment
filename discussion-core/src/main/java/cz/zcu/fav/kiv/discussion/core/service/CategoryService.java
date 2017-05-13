package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.dao.ICategoryDao;
import cz.zcu.fav.kiv.discussion.core.dao.jpa.CategoryJPA;
import cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity;

import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class CategoryService {

    private static ICategoryDao categoryJPA = new CategoryJPA();

    public static CategoryEntity createCategory(CategoryEntity entity) {

        return categoryJPA.save(entity);

    }

    public static CategoryEntity getCategoryById(long categoryId) {

        return categoryJPA.getById(categoryId);
    }

    public static List<CategoryEntity> getCategories() {

        return categoryJPA.getCategories();
    }

    public static void removeCategory(CategoryEntity entity) {

        categoryJPA.remove(entity);
    }


}
