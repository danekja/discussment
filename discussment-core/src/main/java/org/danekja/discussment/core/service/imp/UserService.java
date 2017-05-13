package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.dao.IPermissionDao;
import org.danekja.discussment.core.dao.IUserDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Permission;
import org.danekja.discussment.core.domain.User;
import org.danekja.discussment.core.service.IUserService;

import java.util.List;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class UserService implements IUserService {

    private IUserDao userDao;
    private IPermissionDao permissionDao;

    public UserService(IUserDao userDao, IPermissionDao permissionDao) {
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

    public void removeUser(User user) {
        userDao.remove(user);
    }

    public User getUserById(long userId) {

        return userDao.getById(userId);
    }

    public void addAccessToDiscussion(User entity, Discussion en) {

        entity.getAccessListToDiscussion().add(en);
        en.getUserAccessList().add(entity);

        userDao.save(entity);
    }

    public User getUserByUsername(String username) {

        return userDao.getUserByUsername(username);
    }


}
