package org.danekja.discussment.core.dao.hibernate;

import org.danekja.discussment.core.dao.GenericDao;
import org.danekja.discussment.core.dao.transaction.StaticTransactionHelper;
import org.danekja.discussment.core.dao.transaction.TransactionHelper;
import org.danekja.discussment.core.domain.BaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;


/**
 * Created by Jakub Danek on 04.01.17.
 */
public class GenericDaoHibernate<PK extends Serializable, T extends BaseEntity<PK>> implements GenericDao<PK, T> {

    protected SessionFactory sessionFactory;

    private TransactionHelper transactionHelper;

    private final Class<T> clazz;

    public GenericDaoHibernate(Class<T> clazz, SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = new StaticTransactionHelper(true);
        this.clazz = clazz;
    }

    public void setTransactionHelper(TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    @Override
    public T save(T obj) {
        Session session = sessionFactory.getCurrentSession();
        boolean hasOuterTransaction = transactionHelper.isJoinedToTransaction();
        if (!hasOuterTransaction) {
            session.getTransaction().begin();
        }
        if (obj.isNew()) {
            session.persist(obj);
        } else {
            return (T) session.merge(obj);
        }
        if (!hasOuterTransaction) {
            session.getTransaction().commit();
        }

        return obj;
    }

    @Override
    public T getById(PK id) {
        return (T) sessionFactory.getCurrentSession().get(clazz, id);
    }

    @Override
    public void remove(T obj) {
        Session session = sessionFactory.getCurrentSession();
        boolean hasOuterTransaction = transactionHelper.isJoinedToTransaction();
        if (!hasOuterTransaction) {
            session.getTransaction().begin();
        }
        if (!obj.isNew()) {
            session.delete(session.contains(obj) ? obj : session.merge(obj));
        }
        if (!hasOuterTransaction) {
            session.getTransaction().commit();
        }
    }
}
