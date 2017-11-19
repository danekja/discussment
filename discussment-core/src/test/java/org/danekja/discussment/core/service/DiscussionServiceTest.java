package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.dao.jpa.PermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.domain.Permission;
import org.danekja.discussment.core.accesscontrol.service.PermissionService;
import org.danekja.discussment.core.accesscontrol.service.impl.DefaultPermissionService;
import org.danekja.discussment.core.dao.jpa.CategoryDaoJPA;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.core.dao.jpa.TopicDaoJPA;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.imp.DefaultDiscussionService;
import org.danekja.discussment.core.service.imp.DefaultTopicService;
import org.danekja.discussment.core.service.mock.DefaultUserService;
import org.danekja.discussment.core.service.mock.User;
import org.danekja.discussment.core.service.mock.UserDaoMock;
import org.danekja.discussment.core.service.mock.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Martin Bláha on 19.02.17.
 */
public class DiscussionServiceTest {

    private TopicService topicService;
    private DiscussionService discussionService;
    private PermissionService permissionService;

    private PermissionDao permissionDao;

    private Topic topic;

    @Before
    public void setUp() throws Exception {
        topicService = new DefaultTopicService(new TopicDaoJPA(), new CategoryDaoJPA());
        this.permissionDao = new PermissionDaoJPA();
        UserService userService = new DefaultUserService(new UserDaoMock(), permissionDao);
        this.permissionService = new DefaultPermissionService(permissionDao, userService);
        discussionService = new DefaultDiscussionService(new DiscussionDaoJPA(), permissionService, userService);

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
        User user = new User("test", "", "");
        user.setId(-100L);
        Permission p = permissionService.addPermissionForUser(new Permission(), user);

        Discussion discussion = discussionService.createDiscussion(new Discussion("test"));
        discussionService.removeDiscussion(discussion);

        assertNull("Must be null", discussionService.getDiscussionById(discussion.getId()));

        //clear
        permissionDao.remove(p);
    }

}