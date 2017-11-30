package org.danekja.discussment.core.mock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void remove(User obj) {
        userRepository.remove(obj);
    }

}
