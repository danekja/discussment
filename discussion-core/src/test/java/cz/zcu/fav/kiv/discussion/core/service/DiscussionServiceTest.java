package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.model.DiscussionModel;
import cz.zcu.fav.kiv.discussion.core.model.PostModel;
import cz.zcu.fav.kiv.discussion.core.model.TopicModel;
import cz.zcu.fav.kiv.discussion.core.model.UserModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Martin Bláha on 19.02.17.
 */
public class DiscussionServiceTest {


    private TopicModel topic;

    @Before
    public void setUp() throws Exception {
        topic = TopicService.createTopic("testTopic", "testDes");
    }

    @After
    public void tearDown() throws Exception {
        TopicService.removeTopicById(topic.getId());
    }

    @Test
    public void createDiscussion() throws Exception {
        DiscussionModel discussion = DiscussionService.createDiscussion("test");

        assertNotNull(discussion);

        //clear
        DiscussionService.removeDiscussionById(discussion.getId());
    }

    @Test
    public void createDiscussionWithTopic() throws Exception {
        DiscussionModel discussion = DiscussionService.createDiscussion("test", topic.getId());

        assertNotNull(discussion);

        //clear
        DiscussionService.removeDiscussionById(discussion.getId());
    }

    @Test
    public void getDiscussionsByTopicId() throws Exception {
        DiscussionModel discussion1 = DiscussionService.createDiscussion("test1", topic.getId());
        DiscussionModel discussion2 = DiscussionService.createDiscussion("test2", topic.getId());

        assertEquals(2, DiscussionService.getDiscussionsByTopicId(topic.getId()).size());

        //clear
        DiscussionService.removeDiscussionById(discussion1.getId());
        DiscussionService.removeDiscussionById(discussion2.getId());
    }

    @Test
    public void getDiscussionById() throws Exception {
        DiscussionModel discussion = DiscussionService.createDiscussion("test", topic.getId());


        assertNotNull(DiscussionService.getDiscussionById(discussion.getId()));

        //clear
        DiscussionService.removeDiscussionById(discussion.getId());
    }

    @Test
    public void removeDiscussionById() throws Exception {
        UserModel user = UserService.addUser("test", "", "");

        DiscussionModel discussion = DiscussionService.createDiscussion("test", topic.getId());
        PostModel post = PostService.sendPost(discussion.getId(), user.getId(), "text");
        PostService.sendReply(user.getId(), "reply text", post.getId());

        DiscussionService.removeDiscussionById(discussion.getId());

        //clear
        UserService.removeUserById(user.getId());
    }

}