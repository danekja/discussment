package org.danekja.discussment.core.dao.hibernate;

import org.danekja.discussment.core.dao.CategoryDao;
import org.danekja.discussment.core.domain.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Jakub Danek on 28.01.17.
 */
public class CategoryDaoHibernate extends GenericDaoHibernate<Long, Category> implements CategoryDao {

    public CategoryDaoHibernate(SessionFactory sessionFactory) {
        super(Category.class, sessionFactory);
    }

    public List<Category> getCategories() {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Category> q = session.createNamedQuery(Category.GET_CATEGORIES, Category.class);
        return q.getResultList();

    }

}

