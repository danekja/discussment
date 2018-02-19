package org.danekja.discussment.core.service.old;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.dao.jpa.OldPermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.domain.Permission;
import org.danekja.discussment.core.accesscontrol.service.PermissionService;
import org.danekja.discussment.core.accesscontrol.service.impl.DefaultPermissionService;
import org.danekja.discussment.core.dao.jpa.CategoryDaoJPA;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.core.dao.jpa.TopicDaoJPA;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.mock.User;
import org.danekja.discussment.core.mock.UserDaoMock;
import org.danekja.discussment.core.mock.UserService;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.core.service.imp.DefaultDiscussionService;
import org.danekja.discussment.core.service.imp.DefaultTopicService;
import org.danekja.discussment.core.service.mock.DefaultUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

/**
 * Created by Martin Bláha on 19.02.17.
 */
@Ignore
public class DiscussionServiceTest {
    private EntityManager em;

    private TopicService topicService;
    private DiscussionService discussionService;
    private PermissionService permissionService;

    private PermissionDao permissionDao;

    private Topic topic;

    @Before
    public void setUp() throws Exception {
        topicService = new DefaultTopicService(new TopicDaoJPA(em), new CategoryDaoJPA(em), em);
        this.permissionDao = new OldPermissionDaoJPA(em);
        UserService userService = new DefaultUserService(new UserDaoMock(), permissionDao);
        this.permissionService = new DefaultPermissionService(permissionDao, userService);
        discussionService = new DefaultDiscussionService(new DiscussionDaoJPA(em), permissionService, userService);

        topic = new Topic();
        topic.setName("testTopic");
        topic.setDescription("testDes");

//        topic = topicService.createTopic(topic);
    }

    @After
    public void tearDown() throws Exception {
        topicService.removeTopic(topic);
    }

    @Test
    public void createDiscussionWithTopic() throws Exception {
        Discussion discussion = discussionService.createDiscussion(topic, new Discussion("test"));

        assertNotNull(discussion);

        //clear
        discussionService.removeDiscussion(discussion);
    }

    @Test
    public void getDiscussionsByTopic() throws Exception {
        Discussion discussion1 = discussionService.createDiscussion(topic, new Discussion("test1"));
        Discussion discussion2 = discussionService.createDiscussion(topic, new Discussion("test"));

        assertEquals(2, discussionService.getDiscussionsByTopic(topic).size());

        //clear
        discussionService.removeDiscussion(discussion1);
        discussionService.removeDiscussion(discussion2);
    }

    @Test
    public void getDiscussionById() throws Exception {
        Discussion discussion = discussionService.createDiscussion(topic, new Discussion("test"));


        assertNotNull(discussionService.getDiscussionById(discussion.getId()));

        //clear
        discussionService.removeDiscussion(discussion);
    }

    @Test
    public void removeDiscussion() throws Exception {
        User user = new User("test", "", "");
        user.setId(-100L);
        Permission p = permissionService.addPermissionForUser(new Permission(), user);

        Discussion discussion = discussionService.createDiscussion(topic, new Discussion("test"));
        discussionService.removeDiscussion(discussion);

        assertNull("Must be null", discussionService.getDiscussionById(discussion.getId()));

        //clear
        permissionDao.remove(p);
    }

}