package org.danekja.discussment.core.service;

import org.danekja.discussment.core.dao.jpa.*;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Permission;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.imp.DefaultDiscussionService;
import org.danekja.discussment.core.service.imp.DefaultTopicService;
import org.danekja.discussment.core.service.mock.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Martin Bláha on 19.02.17.
 */
public class DiscussionServiceTest {

    private TopicService topicService;
    private UserService userService;
    private DiscussionService discussionService;

    private UserDao userDao;

    private Topic topic;

    @Before
    public void setUp() throws Exception {
        topicService = new DefaultTopicService(new TopicDaoJPA(), new CategoryDaoJPA());
        this.userDao = new UserDaoJPA();
        userService = new DefaultUserService(userDao, new PermissionDaoJPA());
        discussionService = new DefaultDiscussionService(new DiscussionDaoJPA());

        topic = new Topic();
        topic.setName("testTopic");
        topic.setDescription("testDes");

        topic = topicService.createTopic(topic);
    }

    @After
    public void tearDown() throws Exception {
        topicService.removeTopic(topic);
    }

    @Test
    public void createDiscussion() throws Exception {
        Discussion discussion = discussionService.createDiscussion(new Discussion("test"));

        assertNotNull(discussion);

        //clear
        discussionService.removeDiscussion(discussion);
    }

    @Test
    public void createDiscussionWithTopic() throws Exception {
        Discussion discussion = discussionService.createDiscussion(new Discussion("test"), topic);

        assertNotNull(discussion);

        //clear
        discussionService.removeDiscussion(discussion);
    }

    @Test
    public void getDiscussionsByTopic() throws Exception {
        Discussion discussion1 = discussionService.createDiscussion(new Discussion("test1"), topic);
        Discussion discussion2 = discussionService.createDiscussion(new Discussion("test"), topic);

        assertEquals(2, discussionService.getDiscussionsByTopic(topic).size());

        //clear
        discussionService.removeDiscussion(discussion1);
        discussionService.removeDiscussion(discussion2);
    }

    @Test
    public void getDiscussionById() throws Exception {
        Discussion discussion = discussionService.createDiscussion(new Discussion("test"), topic);


        assertNotNull(discussionService.getDiscussionById(discussion.getId()));

        //clear
        discussionService.removeDiscussion(discussion);
    }

    @Test
    public void removeDiscussion() throws Exception {
        User user = userService.addUser(new User("test", "", ""), new Permission());

        Discussion discussion = discussionService.createDiscussion(new Discussion("test"));
        discussionService.removeDiscussion(discussion);

        assertNull("Must be null", userService.getUserById(discussion.getId()));

        //clear
        userDao.remove(user);
    }

}