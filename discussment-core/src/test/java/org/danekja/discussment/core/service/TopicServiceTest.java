package org.danekja.discussment.core.service;

import org.danekja.discussment.core.dao.jpa.*;
import org.danekja.discussment.core.domain.*;
import org.danekja.discussment.core.service.imp.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Martin Bláha on 20.02.17.
 */
public class TopicServiceTest {

    private ITopicService topicService;
    private ICategoryService categoryService;
    private IUserService userService;
    private IDiscussionService discussionService;
    private IPostService postService;

    private Category category;

    @Before
    public void setUp() throws Exception {
        topicService = new TopicService(new TopicJPA(), new CategoryJPA());
        categoryService = new CategoryService(new CategoryJPA());
        userService = new UserService(new UserJPA(), new PermissionJPA());
        discussionService = new DiscussionService(new DiscussionJPA());
        postService = new PostService(new PostJPA());

        category = categoryService.createCategory(new Category("text"));
    }

    @After
    public void tearDown() throws Exception {
        categoryService.removeCategory(category);
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
        User user = userService.addUser(new User("test", "", ""), new Permission());

        Topic topic = new Topic();
        topic.setName("test");
        topic.setDescription("test det");
        topic = topicService.createTopic(topic, category);

        Discussion IDiscussion = discussionService.createDiscussion(new Discussion("test"), topic);

        Post post = new Post();
        post.setText("text");
        post.setUser(user);
        post = postService.sendPost(IDiscussion, post);

        Post reply = new Post();
        reply.setText("reply1Text");
        reply.setUser(user);
        postService.sendReply(reply, post);

        topicService.removeTopic(topic);

        //clear
        userService.removeUser(user);
    }

}