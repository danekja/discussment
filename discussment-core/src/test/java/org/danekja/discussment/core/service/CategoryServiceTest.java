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
public class CategoryServiceTest {

    private TopicService topicService;
    private CategoryService categoryService;
    private DiscussionService discussionService;
    private PostService postService;
    private PermissionService permissionService;

    private PermissionDao permissionDao;

    @Before
    public void setUp() throws Exception {
        this.permissionDao = new PermissionDaoJPA();
        DiscussionUserService userService = new DefaultUserService(new UserDaoMock(), permissionDao);
        topicService = new DefaultTopicService(new TopicDaoJPA(), new CategoryDaoJPA());
        categoryService = new DefaultCategoryService(new CategoryDaoJPA());
        this.permissionService = new DefaultPermissionService(permissionDao, userService);
        discussionService = new DefaultDiscussionService(new DiscussionDaoJPA(), permissionService, userService);
        postService = new DefaultPostService(new PostDaoJPA(), userService);

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

        User user = new User("test", "", "");
        user.setId(-100L);
        Permission p = permissionService.addPermissionForUser(new Permission(), user);
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
        permissionDao.remove(p);
    }

}