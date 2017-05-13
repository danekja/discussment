package org.danekja.discussment.core.service;

import org.danekja.discussment.core.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Martin Bláha on 20.02.17.
 */
public class TopicServiceTest {

    private Category category;

    @Before
    public void setUp() throws Exception {
        category = CategoryService.createCategory(new Category("text"));
    }

    @After
    public void tearDown() throws Exception {
        CategoryService.removeCategory(category);
    }

    @Test
    public void createTopicWithoutCategory() throws Exception {
        Topic topic = new Topic();
        topic.setName("test");
        topic.setDescription("test det");

        topic = TopicService.createTopic(topic);

        assertNotNull(topic);
    }

    @Test
    public void createTopic() throws Exception {
        Topic topic = new Topic();
        topic.setName("test");
        topic.setDescription("test det");

        topic = TopicService.createTopic(topic, category);

        assertNotNull(topic);
    }

    @Test
    public void getTopicsByCategory() throws Exception {
        Topic topic1 = new Topic();
        topic1.setName("test1");
        topic1.setDescription("test det");
        TopicService.createTopic(topic1, category);

        Topic topic2 = new Topic();
        topic2.setName("test2");
        topic2.setDescription("test det");
        TopicService.createTopic(topic2, category);

        assertEquals(2, TopicService.getTopicsByCategory(category).size());
    }

    @Test
    public void getTopicsWithoutCategory() throws Exception {
        Topic topic1 = new Topic();
        topic1.setName("test1");
        topic1.setDescription("test det");
        TopicService.createTopic(topic1);

        Topic topic2 = new Topic();
        topic2.setName("test2");
        topic2.setDescription("test det");
        TopicService.createTopic(topic2, category);

        Topic topic3 = new Topic();
        topic3.setName("test3");
        topic3.setDescription("test det");
        TopicService.createTopic(topic3, category);

        assertEquals(1, TopicService.getTopicsWithoutCategory().size());
    }

    @Test
    public void removeTopic() throws Exception {
        User user = UserService.addUser(new User("test", "", ""), new Permission());

        Topic topic = new Topic();
        topic.setName("test");
        topic.setDescription("test det");
        topic = TopicService.createTopic(topic, category);

        Discussion discussion = DiscussionService.createDiscussion(new Discussion("test"), topic);

        Post post = new Post();
        post.setText("text");
        post.setUser(user);
        post = PostService.sendPost(discussion, post);

        Post reply = new Post();
        reply.setText("reply1Text");
        reply.setUser(user);
        PostService.sendReply(reply, post);

        TopicService.removeTopic(topic);

        //clear
        UserService.removeUser(user);
    }

}