package org.danekja.discussment.core.service;

import org.danekja.discussment.core.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Martin Bláha on 19.02.17.
 */
public class DiscussionServiceTest {


    private Topic topic;

    @Before
    public void setUp() throws Exception {
        topic = new Topic();
        topic.setName("testTopic");
        topic.setDescription("testDes");

        topic = TopicService.createTopic(topic);
    }

    @After
    public void tearDown() throws Exception {
        TopicService.removeTopic(topic);
    }

    @Test
    public void createDiscussion() throws Exception {
        Discussion discussion = DiscussionService.createDiscussion(new Discussion("test"));

        assertNotNull(discussion);

        //clear
        DiscussionService.removeDiscussion(discussion);
    }

    @Test
    public void createDiscussionWithTopic() throws Exception {
        Discussion discussion = DiscussionService.createDiscussion(new Discussion("test"), topic);

        assertNotNull(discussion);

        //clear
        DiscussionService.removeDiscussion(discussion);
    }

    @Test
    public void getDiscussionsByTopic() throws Exception {
        Discussion discussion1 = DiscussionService.createDiscussion(new Discussion("test1"), topic);
        Discussion discussion2 = DiscussionService.createDiscussion(new Discussion("test"), topic);

        assertEquals(2, DiscussionService.getDiscussionsByTopic(topic).size());

        //clear
        DiscussionService.removeDiscussion(discussion1);
        DiscussionService.removeDiscussion(discussion2);
    }

    @Test
    public void getDiscussionById() throws Exception {
        Discussion discussion = DiscussionService.createDiscussion(new Discussion("test"), topic);


        assertNotNull(DiscussionService.getDiscussionById(discussion.getId()));

        //clear
        DiscussionService.removeDiscussion(discussion);
    }

    @Test
    public void removeDiscussion() throws Exception {
        User user = UserService.addUser(new User("test", "", ""), new Permission());

        Discussion discussion = DiscussionService.createDiscussion(new Discussion("test"), topic);

        Post post = new Post();
        post.setText("text");
        post.setUser(user);

        post = PostService.sendPost(discussion, post);

        Post reply = new Post();
        post.setText("reply test");
        post.setUser(user);

        PostService.sendReply(reply, post);

        DiscussionService.removeDiscussion(discussion);

        //clear
        UserService.removeUser(user);
    }

}