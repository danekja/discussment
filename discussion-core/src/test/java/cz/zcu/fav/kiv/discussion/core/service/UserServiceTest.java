package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.entity.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Martin Bláha on 17.02.17.
 */
public class UserServiceTest {

    private UserEntity user;

    @Before
    public void setUp() throws Exception {
        user = UserService.addUser(new UserEntity("username", "name", "lastname"), new PermissionEntity());
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
        UserEntity removeUser = UserService.addUser(new UserEntity("test", "test", "test"), new PermissionEntity());

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

        TopicEntity topic = new TopicEntity();
        topic.setName("testTopic");
        topic.setDescription("");
        topic.setCategory(CategoryService.getCategoryById(CategoryEntity.WITHOUT_CATEGORY));
        topic = TopicService.createTopic(topic);

        DiscussionEntity discussion = new DiscussionEntity();
        discussion.setPass("password");
        discussion.setName("test");

        discussion = DiscussionService.createDiscussion(discussion, topic);

        //test
        UserService.addAccessToDiscussion(user, discussion);

        //clear
        DiscussionService.removeDiscussion(discussion);

    }


}