package org.danekja.discussment.example.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Mock repository for users.
 *
 * Created by Zdenek Vales on 2.8.2017.
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
            if(u.getUsername().equals(username)) {
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
