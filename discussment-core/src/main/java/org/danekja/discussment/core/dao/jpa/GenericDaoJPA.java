package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.dao.GenericDao;
import org.danekja.discussment.core.domain.BaseEntity;

import javax.persistence.EntityManager;
import java.io.Serializable;


/**
 * Created by Martin Bláha on 04.01.17.
 */
public class GenericDaoJPA<PK extends Serializable, T extends BaseEntity<PK>> implements GenericDao<PK, T> {

    protected static EntityManager em;

    private Class<T> clazz;

    public GenericDaoJPA(Class<T> clazz, EntityManager em) {
        this.em = em;
        this.clazz = clazz;
    }

    public T save(T obj) {
        if(!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        if (obj.isNew()) {
            em.persist(obj);
        } else {
            em.merge(obj);
        }
        em.getTransaction().commit();
        em.clear();
        return obj;
    }

    public T getById(PK id) {
        return em.find(clazz, id);
    }

    public void remove(T obj) {
        if(!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        if (!obj.isNew()) {
            em.remove(em.contains(obj) ? obj : em.merge(obj));
        }
        em.getTransaction().commit();
    }
}
