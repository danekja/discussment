package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.dao.CategoryDao;
import org.danekja.discussment.core.domain.Category;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class CategoryDaoJPA extends GenericDaoJPA<Category> implements CategoryDao {

    public CategoryDaoJPA(EntityManager em) {
        super(Category.class, em);
    }

    public List<Category> getCategories() {
        TypedQuery<Category> q = em.createNamedQuery(Category.GET_CATEGORIES, Category.class);
        return q.getResultList();

    }

}

