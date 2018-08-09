package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.dao.GenericDao;
import org.danekja.discussment.core.dao.jpa.transaction.JPA21TransactionHelper;
import org.danekja.discussment.core.dao.transaction.TransactionHelper;
import org.danekja.discussment.core.domain.BaseEntity;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;


/**
 * Created by Martin Bláha on 04.01.17.
 */
public class GenericDaoJPA<PK extends Serializable, T extends BaseEntity<PK>> implements GenericDao<PK, T> {

    @PersistenceContext
    protected EntityManager em;

    private TransactionHelper transactionHelper;

    private Class<T> clazz;

    /**
     * Constructor used with container managed entity manager
     * @param clazz Entity class this dao instance manages
     */
    public GenericDaoJPA(Class<T> clazz){
        this.clazz = clazz;
    }

    public GenericDaoJPA(Class<T> clazz, EntityManager em) {
        this(clazz);
        this.em = em;
        this.transactionHelper = new JPA21TransactionHelper(em);
    }

    public void setTransactionHelper(TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    @PostConstruct
    protected void init() {
        if (this.transactionHelper == null) {
            this.transactionHelper = new JPA21TransactionHelper(em);
        }
    }

    public void setTransactionHelper(ITransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    public T save(T obj) {
        boolean hasOuterTransaction = transactionHelper.isJoinedToTransaction();
        if(!hasOuterTransaction) {
            em.getTransaction().begin();
        }
        if (obj.isNew()) {
            em.persist(obj);
        } else {
            return em.merge(obj);
        }
        if(!hasOuterTransaction) {
            em.getTransaction().commit();
        }

        return obj;
    }

    public T getById(PK id) {
        return em.find(clazz, id);
    }

    public void remove(T obj) {
        boolean hasOuterTransaction = transactionHelper.isJoinedToTransaction();
        if(!hasOuterTransaction) {
            em.getTransaction().begin();
        }
        if (!obj.isNew()) {
            em.remove(em.contains(obj) ? obj : em.merge(obj));
        }
        if(!hasOuterTransaction){
            em.getTransaction().commit();
        }
    }
}
