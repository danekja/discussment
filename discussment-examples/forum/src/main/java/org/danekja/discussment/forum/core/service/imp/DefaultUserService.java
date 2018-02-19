package org.danekja.discussment.forum.core.service.imp;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.forum.core.dao.UserDao;
import org.danekja.discussment.forum.core.domain.User;
import org.danekja.discussment.forum.core.service.UserService;
import org.danekja.discussment.ui.wicket.session.SessionUtil;

import java.util.List;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class DefaultUserService implements UserService {

    private UserDao userDao;

    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;

    }

    public User addUser(User entity) {

        return userDao.save(entity);
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public IDiscussionUser getUserById(String userId) throws DiscussionUserNotFoundException {
        IDiscussionUser user = userDao.getById(Long.valueOf(userId));
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
