package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.model.DiscussionModel;
import cz.zcu.fav.kiv.discussion.core.model.TopicModel;
import cz.zcu.fav.kiv.discussion.core.model.UserModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Martin Bláha on 17.02.17.
 */
public class UserServiceTest {

    private UserModel user;

    @Before
    public void setUp() throws Exception {
        user = UserService.addUser("username", "name", "lastname");
    }

    @After
    public void tearDown() throws Exception {
        UserService.removeUserById(user.getId());
    }

    @Test
    public void registrationUser() throws Exception {

        assertNotNull("The user was not created", user);
        assertEquals("The username must be 'username'", "username", user.getUsername());
        assertEquals("The name must be 'name'", "name", user.getName());
        assertEquals("The lastname must be 'lastname'", "lastname", user.getLastname());

    }

    @Test
    public void removeUserById() throws Exception {
        UserModel removeUser = UserService.addUser("test", "test", "test");

        UserService.removeUserById(removeUser.getId());

        assertNull("Must be null", UserService.getUserById(removeUser.getId()));
    }


    @Test
    public void getUserById() throws Exception {
        assertEquals(user, UserService.getUserById(user.getId()));
    }

    @Test
    public void getUserByUsername() throws Exception {
        assertEquals(user, UserService.getUserByUsername("username"));
    }

    @Test
    public void setUsername() throws Exception {
        UserService.setUsername(user.getId(), "username1");
        user = UserService.getUserById(user.getId());

        assertEquals("The username must be 'username1'", "username1", user.getUsername());

        //clear
        UserService.setUsername(user.getId(), "username");
        user = UserService.getUserById(user.getId());
    }

    @Test
    public void setName() throws Exception {
        UserService.setName(user.getId(), "name1");
        user = UserService.getUserById(user.getId());

        assertEquals("The name must be 'name1'", "name1", user.getName());

        //clear
        UserService.setName(user.getId(), "name");
        user = UserService.getUserById(user.getId());
    }

    @Test
    public void setLastname() throws Exception {
        UserService.setLastname(user.getId(), "lastname1");
        user = UserService.getUserById(user.getId());

        assertEquals("The lastname must be 'lastname1'", "lastname1", user.getLastname());

        //clear
        UserService.setLastname(user.getId(), "lastname");
        user = UserService.getUserById(user.getId());
    }

    @Test
    public void addAccessToDiscussion() throws Exception {
        //prepare
        TopicModel topic = TopicService.createTopic("testTopic", "", 0);
        DiscussionModel discussion = DiscussionService.createDiscussion("test", topic.getId(), "password");

        //test
        UserService.addAccessToDiscussion(user.getId(), discussion.getId());

        //clear
        DiscussionService.removeDiscussionById(discussion.getId());

    }


}