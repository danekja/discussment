package org.danekja.discussment.core.service;

import org.danekja.discussment.core.dao.PermissionDao;
import org.danekja.discussment.core.dao.jpa.*;
import org.danekja.discussment.core.domain.*;
import org.danekja.discussment.core.service.imp.*;
import org.danekja.discussment.core.service.mock.DefaultUserService;
import org.danekja.discussment.core.service.mock.User;
import org.danekja.discussment.core.service.mock.UserDaoMock;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Martin Bláha on 20.02.17.
 */
public class TopicServiceTest {

    private TopicService topicService;
    private CategoryService categoryService;
    private DiscussionService discussionService;
    private PostService postService;
    private PermissionService permissionService;

    private PermissionDao permissionDao;

    private Category category;

    @Before
    public void setUp() throws Exception {
        this.permissionDao = new PermissionDaoJPA();
        DiscussionUserService userService = new DefaultUserService(new UserDaoMock(), permissionDao);
        topicService = new DefaultTopicService(new TopicDaoJPA(), new CategoryDaoJPA());
        categoryService = new DefaultCategoryService(new CategoryDaoJPA());
        this.permissionService = new DefaultPermissionService(permissionDao, userService);
        discussionService = new DefaultDiscussionService(new DiscussionDaoJPA(), permissionService, userService);
        postService = new DefaultPostService(new PostDaoJPA(), userService);

        category = categoryService.createCategory(new Category("text"));
    }

    @Test
    public void createTopicWithoutCategory() throws Exception {
        Topic topic = new Topic();
        topic.setName("test");
        topic.setDescription("test det");

        topic = topicService.createTopic(topic);

        assertNotNull(topic);
    }

    @Test
    public void createTopic() throws Exception {
        Topic topic = new Topic();
        topic.setName("test");
        topic.setDescription("test det");

        topic = topicService.createTopic(topic, category);

        assertNotNull(topic);
    }

    @Test
    public void getTopicsByCategory() throws Exception {
        Topic topic1 = new Topic();
        topic1.setName("test1");
        topic1.setDescription("test det");
        topicService.createTopic(topic1, category);

        Topic topic2 = new Topic();
        topic2.setName("test2");
        topic2.setDescription("test det");
        topicService.createTopic(topic2, category);

        assertEquals(2, topicService.getTopicsByCategory(category).size());
    }

    @Test
    public void getTopicsWithoutCategory() throws Exception {
        Topic topic1 = new Topic();
        topic1.setName("test1");
        topic1.setDescription("test det");
        topicService.createTopic(topic1);

        Topic topic2 = new Topic();
        topic2.setName("test2");
        topic2.setDescription("test det");
        topicService.createTopic(topic2, category);

        Topic topic3 = new Topic();
        topic3.setName("test3");
        topic3.setDescription("test det");
        topicService.createTopic(topic3, category);

        assertEquals(1, topicService.getTopicsWithoutCategory().size());
    }

    @Test
    public void removeTopic() throws Exception {
        User user = new User("test", "", "");
        user.setId(-100L);
        Permission p = permissionService.addPermissionForUser(new Permission(), user);

        Topic topic = new Topic();
        topic.setName("test");
        topic.setDescription("test det");
        topic = topicService.createTopic(topic, category);

        Discussion IDiscussion = discussionService.createDiscussion(new Discussion("test"), topic);

        Post post = new Post();
        post.setText("text");
        post.setUser(user.getId());
        post = postService.sendPost(IDiscussion, post);

        Post reply = new Post();
        reply.setText("reply1Text");
        reply.setUser(user.getId());
        postService.sendReply(reply, post);

        topicService.removeTopic(topic);

        //clear
        permissionDao.remove(p);
    }

    @Test
    public void removeTopicWithUserAccess() throws Exception {
        User user = new User("test", "", "");
        user.setId(-100L);
        Permission p = permissionService.addPermissionForUser(new Permission(), user);

        Topic topic = new Topic();
        topic.setName("test");
        topic.setDescription("test det");
        topic = topicService.createTopic(topic, category);

        Discussion discussion = discussionService.createDiscussion(new Discussion("test"), topic);


        discussionService.addAccessToDiscussion(user, discussion);


        topicService.removeTopic(topic);


        //clear
        permissionDao.remove(p);
    }

}