package org.danekja.discussment.example.core;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.Permission;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.ui.wicket.session.SessionUtil;

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

        return userDao.save(entity);
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public IDiscussionUser getUserById(Long userId) throws DiscussionUserNotFoundException {
        IDiscussionUser user = userDao.getById(userId);
        if(user == null) {
            throw new DiscussionUserNotFoundException(userId);
        }
        return user;
    }

    public User getUserByUsername(String username) {

        return userDao.getUserByUsername(username);
    }

    public IDiscussionUser getCurrentlyLoggedUser() {
        return SessionUtil.getUser();
    }
}
