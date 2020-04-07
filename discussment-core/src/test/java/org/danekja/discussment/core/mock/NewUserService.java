package org.danekja.discussment.core.mock;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.List;

/**
 * New class for user service. Will replace DefaultUserServiceClass.
 *
 * Created by Zdenek Vales on 26.11.2017.
 */
public class NewUserService implements DiscussionUserService {

    private UserDao userDao;

    public NewUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public IDiscussionUser getUserById(String userId) throws DiscussionUserNotFoundException {
        return userDao.getById(Long.parseLong(userId));
    }

    public IDiscussionUser getCurrentlyLoggedUser() {
        // todo: implement this
        throw new NotImplementedException();
    }

    @Override
    public List<IDiscussionUser> getUsersByIds(Collection<String> userIds) {
        return userDao.getUsersByIds(userIds);
    }
}
