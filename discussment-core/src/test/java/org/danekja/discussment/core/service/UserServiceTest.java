package org.danekja.discussment.core.service;

import org.danekja.discussment.core.dao.jpa.*;
import org.danekja.discussment.core.domain.*;
import org.danekja.discussment.core.service.imp.CategoryService;
import org.danekja.discussment.core.service.imp.DiscussionService;
import org.danekja.discussment.core.service.imp.TopicService;
import org.danekja.discussment.core.service.imp.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Martin Bláha on 17.02.17.
 */
public class UserServiceTest {

    private ITopicService topicService;
    private ICategoryService categoryService;
    private IUserService userService;
    private IDiscussionService discussionService;

    private User user;

    @Before
    public void setUp() throws Exception {
        topicService = new TopicService(new TopicJPA(), new CategoryJPA());
        categoryService = new CategoryService(new CategoryJPA());
        userService = new UserService(new UserJPA(), new PermissionJPA());
        discussionService = new DiscussionService(new DiscussionJPA());

        user = userService.addUser(new User("username", "name", "lastname"), new Permission());
    }

    @After
    public void tearDown() throws Exception {
        userService.removeUser(user);
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
        User removeUser = userService.addUser(new User("test", "test", "test"), new Permission());

        userService.removeUser(removeUser);

        assertNull("Must be null", userService.getUserById(removeUser.getId()));
    }


    @Test
    public void getUserById() throws Exception {
        assertEquals(user, userService.getUserById(user.getId()));
    }

    @Test
    public void addAccessToDiscussion() throws Exception {
        //prepare

        Topic topic = new Topic();
        topic.setName("testTopic");
        topic.setDescription("");
        topic.setCategory(categoryService.getCategoryById(Category.WITHOUT_CATEGORY));
        topic = topicService.createTopic(topic);

        Discussion discussion = new Discussion();
        discussion.setPass("password");
        discussion.setName("test");

        discussion = discussionService.createDiscussion(discussion, topic);

        //test
        userService.addAccessToDiscussion(user, discussion);

        //clear
        discussionService.removeDiscussion(discussion);

    }


}