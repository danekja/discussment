package org.danekja.discussment.core.service;

import org.danekja.discussment.core.dao.jpa.*;
import org.danekja.discussment.core.domain.*;
import org.danekja.discussment.core.service.imp.DiscussionService;
import org.danekja.discussment.core.service.imp.PostService;
import org.danekja.discussment.core.service.imp.TopicService;
import org.danekja.discussment.core.service.imp.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Martin Bláha on 19.02.17.
 */
public class DiscussionServiceTest {

    private ITopicService topicService;
    private IUserService userService;
    private IDiscussionService discussionService;
    private IPostService postService;

    private Topic topic;

    @Before
    public void setUp() throws Exception {
        topicService = new TopicService(new TopicJPA(), new CategoryJPA());
        userService = new UserService(new UserJPA(), new PermissionJPA());
        discussionService = new DiscussionService(new DiscussionJPA());
        postService = new PostService(new PostJPA());

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

        Discussion discussion = discussionService.createDiscussion(new Discussion("test"), topic);

        Post post = new Post();
        post.setText("text");
        post.setUser(user);

        post = postService.sendPost(discussion, post);

        Post reply = new Post();
        post.setText("reply test");
        post.setUser(user);

        postService.sendReply(reply, post);

        discussionService.removeDiscussion(discussion);

        //clear
        userService.removeUser(user);
    }

}