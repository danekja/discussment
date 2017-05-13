package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.entity.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Martin Bláha on 20.02.17.
 */
public class TopicServiceTest {

    private CategoryEntity category;

    @Before
    public void setUp() throws Exception {
        category = CategoryService.createCategory(new CategoryEntity("text"));
    }

    @After
    public void tearDown() throws Exception {
        CategoryService.removeCategory(category);
    }

    @Test
    public void createTopicWithoutCategory() throws Exception {
        TopicEntity topic = new TopicEntity();
        topic.setName("test");
        topic.setDescription("test det");

        topic = TopicService.createTopic(topic);

        assertNotNull(topic);
    }

    @Test
    public void createTopic() throws Exception {
        TopicEntity topic = new TopicEntity();
        topic.setName("test");
        topic.setDescription("test det");

        topic = TopicService.createTopic(topic, category);

        assertNotNull(topic);
    }

    @Test
    public void getTopicsByCategory() throws Exception {
        TopicEntity topic1 = new TopicEntity();
        topic1.setName("test1");
        topic1.setDescription("test det");
        TopicService.createTopic(topic1, category);

        TopicEntity topic2 = new TopicEntity();
        topic2.setName("test2");
        topic2.setDescription("test det");
        TopicService.createTopic(topic2, category);

        assertEquals(2, TopicService.getTopicsByCategory(category).size());
    }

    @Test
    public void getTopicsWithoutCategory() throws Exception {
        TopicEntity topic1 = new TopicEntity();
        topic1.setName("test1");
        topic1.setDescription("test det");
        TopicService.createTopic(topic1);

        TopicEntity topic2 = new TopicEntity();
        topic2.setName("test2");
        topic2.setDescription("test det");
        TopicService.createTopic(topic2, category);

        TopicEntity topic3 = new TopicEntity();
        topic3.setName("test3");
        topic3.setDescription("test det");
        TopicService.createTopic(topic3, category);

        assertEquals(1, TopicService.getTopicsWithoutCategory().size());
    }

    @Test
    public void removeTopic() throws Exception {
        UserEntity user = UserService.addUser(new UserEntity("test", "", ""), new PermissionEntity());

        TopicEntity topic = new TopicEntity();
        topic.setName("test");
        topic.setDescription("test det");
        topic = TopicService.createTopic(topic, category);

        DiscussionEntity discussion = DiscussionService.createDiscussion(new DiscussionEntity("test"), topic);

        PostEntity post = new PostEntity();
        post.setText("text");
        post.setUser(user);
        post = PostService.sendPost(discussion, post);

        PostEntity reply = new PostEntity();
        reply.setText("reply1Text");
        reply.setUser(user);
        PostService.sendReply(reply, post);

        TopicService.removeTopic(topic);

        //clear
        UserService.removeUser(user);
    }

}