package cz.zcu.fav.kiv.discussion.core.dao;

import cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class CategoryDao extends GenericDao<CategoryEntity> {

    public CategoryDao() {
        super(CategoryEntity.class);
    }

    public List<CategoryEntity> getCategories() {
        TypedQuery<CategoryEntity> q = em.createNamedQuery(CategoryEntity.GET_CATEGORIES, CategoryEntity.class);
        return q.getResultList();

    }

}

