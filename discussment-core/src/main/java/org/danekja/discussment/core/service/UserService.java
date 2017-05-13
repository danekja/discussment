package org.danekja.discussment.core.service;

import org.danekja.discussment.core.dao.IPermissionDao;
import org.danekja.discussment.core.dao.IUserDao;
import org.danekja.discussment.core.dao.jpa.PermissionJPA;
import org.danekja.discussment.core.dao.jpa.UserJPA;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Permission;
import org.danekja.discussment.core.domain.User;

import java.util.List;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class UserService {

    private static IUserDao userDao = new UserJPA();
    private static IPermissionDao permissionDao = new PermissionJPA();


    public static User addUser(User entity, Permission permission) {

        permission = permissionDao.save(permission);
        permission.setUser(entity);

        entity.setPermissions(permission);

        return userDao.save(entity);
    }

    public static List<User> getUsers() {

        return userDao.getUsers();
    }

    public static void removeUser(User user) {
        userDao.remove(user);
    }

    public static User getUserById(long userId) {

        return userDao.getById(userId);
    }

    public static void addAccessToDiscussion(User entity, Discussion en) {

        entity.getAccessListToDiscussion().add(en);
        en.getUserAccessList().add(entity);

        userDao.save(entity);
    }

    public static User getUserByUsername(String username) {

        return userDao.getUserByUsername(username);
    }


}
