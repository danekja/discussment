package org.danekja.discussment.core.service.mock;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.Permission;
import org.danekja.discussment.core.mock.User;
import org.danekja.discussment.core.mock.UserDao;
import org.danekja.discussment.core.mock.UserService;

import java.util.List;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class DefaultUserService implements UserService {

    private UserDao userDao;
    private PermissionDao permissionDao;

    public DefaultUserService(UserDao userDao, PermissionDao permissionDao) {
        this.userDao = userDao;
        this.permissionDao = permissionDao;
    }

    public User addUser(User entity, Permission permission) {

        permission = permissionDao.save(permission);
        permission.setUserId(entity.getDiscussionUserId());

        return userDao.save(entity);
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User getUserById(long userId) {

        return userDao.getById(userId);
    }

    public IDiscussionUser getUserById(String userId) {
        return getUserById(Long.parseLong(userId));
    }

    public IDiscussionUser getCurrentlyLoggedUser() {
        return null;
    }

    public User getUserByUsername(String username) {

        return userDao.getUserByUsername(username);
    }


}
