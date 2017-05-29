package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.dao.PermissionDao;
import org.danekja.discussment.core.dao.UserDao;
import org.danekja.discussment.core.domain.Permission;
import org.danekja.discussment.core.domain.User;

import java.util.List;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class DefaultUserService implements org.danekja.discussment.core.service.UserService {

    private UserDao userDao;
    private PermissionDao permissionDao;

    public DefaultUserService(UserDao userDao, PermissionDao permissionDao) {
        this.userDao = userDao;
        this.permissionDao = permissionDao;
    }

    public User addUser(User entity, Permission permission) {

        permission = permissionDao.save(permission);
        permission.setUser(entity);

        entity.setPermissions(permission);

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
