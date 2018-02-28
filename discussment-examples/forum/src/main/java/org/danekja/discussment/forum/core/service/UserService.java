package org.danekja.discussment.forum.core.service;

import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.forum.core.domain.User;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface contains service methods for working with the users.
 */
public interface  UserService extends DiscussionUserService{

    /**
     * Add a new user to the discussion
     *
     * @param entity new user
     * @return new user
     */
    User addUser(User entity);

    List<User> getUsers();

    /**
     * Get an user in the discussion based on his username.
     *
     * @param username username of a user
     * @return user by username
     */
    User getUserByUsername(String username);
}
