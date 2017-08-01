package org.danekja.discussment.example.core;

import org.danekja.discussment.core.dao.PermissionDao;
import org.danekja.discussment.core.domain.Permission;

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
        permission.setUser(entity);

        return userDao.save(entity);
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User getUserById(long userId) {

        return userDao.getById(userId);
    }

    public User getUserByUsername(String username) {

        return userDao.getUserByUsername(username);
    }


}
