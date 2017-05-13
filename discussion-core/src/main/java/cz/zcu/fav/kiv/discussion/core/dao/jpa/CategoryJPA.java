package cz.zcu.fav.kiv.discussion.core.dao.jpa;

import cz.zcu.fav.kiv.discussion.core.dao.ICategoryDao;
import cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class CategoryJPA extends GenericJPA<CategoryEntity> implements ICategoryDao {

    public CategoryJPA() {
        super(CategoryEntity.class);
    }

    public List<CategoryEntity> getCategories() {
        TypedQuery<CategoryEntity> q = em.createNamedQuery(CategoryEntity.GET_CATEGORIES, CategoryEntity.class);
        return q.getResultList();

    }

}

