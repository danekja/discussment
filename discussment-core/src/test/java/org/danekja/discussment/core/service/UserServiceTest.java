package org.danekja.discussment.core.service;

import org.danekja.discussment.core.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Martin Bláha on 17.02.17.
 */
public class UserServiceTest {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = UserService.addUser(new User("username", "name", "lastname"), new Permission());
    }

    @After
    public void tearDown() throws Exception {
        UserService.removeUser(user);
    }

    @Test
    public void registrationUser() throws Exception {

        assertNotNull("The user was not created", user);
        assertEquals("The username must be 'username'", "username", user.getUsername());
        assertEquals("The name must be 'name'", "name", user.getName());
        assertEquals("The lastname must be 'lastname'", "lastname", user.getLastname());

    }

    @Test
    public void removeUser() throws Exception {
        User removeUser = UserService.addUser(new User("test", "test", "test"), new Permission());

        UserService.removeUser(removeUser);

        assertNull("Must be null", UserService.getUserById(removeUser.getId()));
    }


    @Test
    public void getUserById() throws Exception {
        assertEquals(user, UserService.getUserById(user.getId()));
    }

    @Test
    public void addAccessToDiscussion() throws Exception {
        //prepare

        Topic topic = new Topic();
        topic.setName("testTopic");
        topic.setDescription("");
        topic.setCategory(CategoryService.getCategoryById(Category.WITHOUT_CATEGORY));
        topic = TopicService.createTopic(topic);

        Discussion discussion = new Discussion();
        discussion.setPass("password");
        discussion.setName("test");

        discussion = DiscussionService.createDiscussion(discussion, topic);

        //test
        UserService.addAccessToDiscussion(user, discussion);

        //clear
        DiscussionService.removeDiscussion(discussion);

    }


}