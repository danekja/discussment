package cz.zcu.fav.kiv.discussion.core.dao;

import cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 */
public interface ICategoryDao extends IGenericDao<CategoryEntity> {
    List<CategoryEntity> getCategories();
}
