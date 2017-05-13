package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.entity.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Martin Bláha on 19.02.17.
 */
public class DiscussionServiceTest {


    private TopicEntity topic;

    @Before
    public void setUp() throws Exception {
        topic = new TopicEntity();
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
        DiscussionEntity discussion = DiscussionService.createDiscussion(new DiscussionEntity("test"));

        assertNotNull(discussion);

        //clear
        DiscussionService.removeDiscussion(discussion);
    }

    @Test
    public void createDiscussionWithTopic() throws Exception {
        DiscussionEntity discussion = DiscussionService.createDiscussion(new DiscussionEntity("test"), topic);

        assertNotNull(discussion);

        //clear
        DiscussionService.removeDiscussion(discussion);
    }

    @Test
    public void getDiscussionsByTopic() throws Exception {
        DiscussionEntity discussion1 = DiscussionService.createDiscussion(new DiscussionEntity("test1"), topic);
        DiscussionEntity discussion2 = DiscussionService.createDiscussion(new DiscussionEntity("test"), topic);

        assertEquals(2, DiscussionService.getDiscussionsByTopic(topic).size());

        //clear
        DiscussionService.removeDiscussion(discussion1);
        DiscussionService.removeDiscussion(discussion2);
    }

    @Test
    public void getDiscussionById() throws Exception {
        DiscussionEntity discussion = DiscussionService.createDiscussion(new DiscussionEntity("test"), topic);


        assertNotNull(DiscussionService.getDiscussionById(discussion.getId()));

        //clear
        DiscussionService.removeDiscussion(discussion);
    }

    @Test
    public void removeDiscussion() throws Exception {
        UserEntity user = UserService.addUser(new UserEntity("test", "", ""), new PermissionEntity());

        DiscussionEntity discussion = DiscussionService.createDiscussion(new DiscussionEntity("test"), topic);

        PostEntity post = new PostEntity();
        post.setText("text");
        post.setUser(user);

        post = PostService.sendPost(discussion, post);

        PostEntity reply = new PostEntity();
        post.setText("reply test");
        post.setUser(user);

        PostService.sendReply(reply, post);

        DiscussionService.removeDiscussion(discussion);

        //clear
        UserService.removeUser(user);
    }

}