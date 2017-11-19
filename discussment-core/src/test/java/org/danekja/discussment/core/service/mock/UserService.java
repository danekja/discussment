package org.danekja.discussment.core.service.mock;

import org.danekja.discussment.core.accesscontrol.domain.Permission;
import org.danekja.discussment.core.service.DiscussionUserService;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface contains service methods for working with the users.
 */
public interface UserService extends DiscussionUserService{

    /**
     * Add a new user to the discussion
     *
     * @param entity new user
     * @param permission permission of a new user
     * @return new user
     */
    User addUser(User entity, Permission permission);

    /**
     * Get an user in the discussion based on his id.
     *
     * @param userId user id
     * @return user by id
     */
    User getUserById(long userId);

    List<User> getUsers();

    /**
     * Get an user in the discussion based on his username.
     *
     * @param username username of a user
     * @return user by username
     */
    User getUserByUsername(String username);
}
