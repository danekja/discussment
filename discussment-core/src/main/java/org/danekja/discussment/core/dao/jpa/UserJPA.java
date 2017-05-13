package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.dao.IUserDao;
import org.danekja.discussment.core.domain.User;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Martin Bláha on 04.01.17.
 */
public class UserJPA extends GenericJPA<User> implements IUserDao {

    public UserJPA() {
        super(User.class);
    }

    public User getUserByUsername(String username) {
        TypedQuery<User> q = em.createNamedQuery(User.GET_BY_USERNAME, User.class);
        q.setParameter("username", username);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<User> getUsers() {
        TypedQuery<User> q = em.createNamedQuery(User.GET_USERS, User.class);
        return q.getResultList();
    }

}
