package cz.zcu.fav.kiv.discussion.core.dao;

import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Martin Bláha on 04.01.17.
 */
public class UserDao extends GenericDao<UserEntity> {

    public UserDao() {
        super(UserEntity.class);
    }

    public UserEntity getUserByUsername(String username) {
        TypedQuery<UserEntity> q = em.createNamedQuery(UserEntity.GET_BY_USERNAME, UserEntity.class);
        q.setParameter("username", username);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<UserEntity> getUsers() {
        TypedQuery<UserEntity> q = em.createNamedQuery(UserEntity.GET_USERS, UserEntity.class);
        return q.getResultList();
    }

}
