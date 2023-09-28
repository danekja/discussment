package org.danekja.discussment.article.core.service.imp;

import org.apache.wicket.Session;
import org.danekja.discussment.article.core.dao.UserDao;
import org.danekja.discussment.article.core.domain.User;
import org.danekja.discussment.article.core.service.UserService;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.ui.wicket.session.SessionUtil;

import java.util.List;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class DefaultUserService implements UserService {

    private final UserDao userDao;

    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User addUser(User entity) {

        return userDao.save(entity);
    }

    @Override
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

    @Override
    public User getUserByUsername(String username) {

        return userDao.getUserByUsername(username);
    }

    public IDiscussionUser getCurrentlyLoggedUser() {
        return (IDiscussionUser) Session.get().getAttribute("user");
    }
}
