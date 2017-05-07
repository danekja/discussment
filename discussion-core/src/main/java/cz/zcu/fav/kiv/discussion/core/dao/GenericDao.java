package cz.zcu.fav.kiv.discussion.core.dao;

import cz.zcu.fav.kiv.discussion.core.entity.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;


/**
 * Created by Martin Bláha on 04.01.17.
 */
public class GenericDao <T extends BaseEntity> {

    protected static EntityManager em;

    private Class<T> clazz;

    public GenericDao(Class<T> clazz) {

        if (em == null) {
            em = Persistence.createEntityManagerFactory("discussion-core").createEntityManager();
        }

        this.clazz = clazz;
    }

    public T save(T obj) {
        em.getTransaction().begin();
        if (obj.isNew()) {
            em.persist(obj);
        } else {
            em.merge(obj);
        }
        em.getTransaction().commit();
        return obj;
    }

    public T getById(Long id) {
        return em.find(clazz, id);
    }

    public void remove(T obj) {
        em.getTransaction().begin();
        if (!obj.isNew()) {
            em.remove(em.contains(obj) ? obj : em.merge(obj));
        }
        em.getTransaction().commit();
    }
}
