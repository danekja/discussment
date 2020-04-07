package org.danekja.discussment.core.mock;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Martin Bláha on 04.01.17.
 */
public class UserDaoMock implements UserDao {

    /**
     * Repository to store users.
     */
    private static Set<User> userRepository = new HashSet<User>();

    /**
     * Returns id for new user.
     * @return
     */
    private static Long getNewId() {
        Long id = 0L;
        for (User u : userRepository) {
            if(u.getId().equals(id)) {
                id++;
            }
        }

        return id;
    }

    public User save(User obj) {
        if(obj.getId() == null) {
            // save
            obj.setId(getNewId());
            userRepository.add(obj);
        } else {
            // merge
            userRepository.add(obj);
        }
        return obj;
    }

    public User getUserByUsername(String username) {
        for (User u : userRepository) {
            if (u.getDisplayName().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public User getById(Long id) {
        for (User u : userRepository) {
            if(u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return new ArrayList<User>(userRepository);
    }

    @Override
    public List<IDiscussionUser> getUsersByIds(Collection<String> userIds) {
        return userRepository.stream().filter(u -> userIds.contains(u.getDiscussionUserId())).collect(Collectors.toList());
    }

    public void remove(User obj) {
        userRepository.remove(obj);
    }

}
