package org.danekja.discussment.article.core.dao;

import org.danekja.discussment.article.core.domain.User;
import org.danekja.discussment.core.dao.GenericDao;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface extends GenericDao on methods for working with users in a database
 */
public interface UserDao extends GenericDao<Long, User> {

    /**
     * Get an user in a database based on his username.
     *
     * @param username username of a user
     * @return User
     */
    User getUserByUsername(String username);

    /**
     * Get all users in a database.
     *
     * @return list of User
     */
    List<User> getUsers();
}
