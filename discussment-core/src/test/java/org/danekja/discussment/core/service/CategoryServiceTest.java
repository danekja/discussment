package org.danekja.discussment.core.service;

import org.danekja.discussment.core.dao.jpa.*;
import org.danekja.discussment.core.domain.*;
import org.danekja.discussment.core.service.imp.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Martin Bláha on 20.02.17.
 */
public class CategoryServiceTest {

    private ITopicService topicService;
    private ICategoryService categoryService;
    private IUserService userService;
    private IDiscussionService discussionService;
    private IPostService postService;

    @Before
    public void setUp() throws Exception {
        topicService = new TopicService(new TopicJPA(), new CategoryJPA());
        categoryService = new CategoryService(new CategoryJPA());
        userService = new UserService(new UserJPA(), new PermissionJPA());
        discussionService = new DiscussionService(new DiscussionJPA());
        postService = new PostService(new PostJPA());

    }

    @Test
    public void createCategory() throws Exception {

        Category category = categoryService.createCategory(new Category("category"));

        assertNotNull(category);

        //clear
        categoryService.removeCategory(category);
    }

    @Test
    public void getCategory() throws Exception {
        Category category = categoryService.createCategory(new Category("category"));

        assertNotNull(categoryService.getCategoryById(category.getId()));

        //clear
        categoryService.removeCategory(category);
    }

    @Test
    public void getCategories() throws Exception {
        categoryService.createCategory(new Category("category1"));
        categoryService.createCategory(new Category("category2"));

        assertEquals(2, categoryService.getCategories().size());
    }

    @Test
    public void removeCategory() throws Exception {

        User user = userService.addUser(new User("test", "", ""), new Permission());
        Category category = categoryService.createCategory(new Category("category"));

        Topic topic = new Topic();
        topic.setName("test1");
        topic.setDescription("test des");

        topic = topicService.createTopic(topic, category);

        Discussion discussion = new Discussion("test");
        discussion = discussionService.createDiscussion(discussion, topic);

        Post IPost = postService.sendPost(discussion, new Post(user, "text"));
        postService.sendReply(new Post(user, "reply text"), IPost);

        categoryService.removeCategory(category);

        //clear
        userService.removeUser(user);
    }

}