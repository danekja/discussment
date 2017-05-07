package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Martin Bláha on 20.02.17.
 */
public class TopicServiceTest {

    private CategoryModel category;

    @Before
    public void setUp() throws Exception {
        category = CategoryService.createCategory("test");
    }

    @After
    public void tearDown() throws Exception {
        CategoryService.removeCategoryById(category.getId());
    }

    @Test
    public void createTopicWithoutCategory() throws Exception {
        TopicModel topic = TopicService.createTopic("test", "test des");

        assertNotNull(topic);
    }

    @Test
    public void createTopic() throws Exception {
        TopicModel topic = TopicService.createTopic("test", "test des", category.getId());

        assertNotNull(topic);
    }

    @Test
    public void getTopicsByCategoryId() throws Exception {
        TopicService.createTopic("test1", "test des", category.getId());
        TopicService.createTopic("test2", "test des", category.getId());

        assertEquals(2, TopicService.getTopicsByCategoryId(category.getId()).size());
    }

    @Test
    public void getTopicsWithoutCategory() throws Exception {
        TopicService.createTopic("test0", "test des");
        TopicService.createTopic("test1", "test des", category.getId());
        TopicService.createTopic("test2", "test des", category.getId());

        assertEquals(1, TopicService.getTopicsWithoutCategory().size());
    }

    @Test
    public void removeTopicById() throws Exception {
        UserModel user = UserService.addUser("test", "", "");

        TopicModel topic = TopicService.createTopic("test1", "test des", category.getId());

        DiscussionModel discussion = DiscussionService.createDiscussion("test", topic.getId());
        PostModel post = PostService.sendPost(discussion.getId(), user.getId(), "text");
        PostService.sendReply(user.getId(), "reply text", post.getId());

        TopicService.removeTopicById(topic.getId());

        //clear
        UserService.removeUserById(user.getId());
    }

}