package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.model.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Martin Bláha on 20.02.17.
 */
public class CategoryServiceTest {

    @Test
    public void createCategory() throws Exception {
        CategoryModel category = CategoryService.createCategory("category");

        assertNotNull(category);

        //clear
        CategoryService.removeCategoryById(category.getId());
    }

    @Test
    public void getCategoryById() throws Exception {
        CategoryModel category = CategoryService.createCategory("category");

        assertNotNull(CategoryService.getCategoryById(category.getId()));

        //clear
        CategoryService.removeCategoryById(category.getId());
    }

    @Test
    public void getCategories() throws Exception {
        CategoryService.createCategory("category1");
        CategoryService.createCategory("category2");

        assertEquals(2, CategoryService.getCategories().size());
    }

    @Test
    public void removeCategoryById() throws Exception {
        UserModel user = UserService.addUser("test", "", "");

        CategoryModel category = CategoryService.createCategory("category");

        TopicModel topic = TopicService.createTopic("test1", "test des", category.getId());
        DiscussionModel discussion = DiscussionService.createDiscussion("test", topic.getId());
        PostModel post = PostService.sendPost(discussion.getId(), user.getId(), "text");
        PostService.sendReply(user.getId(), "reply text", post.getId());

        CategoryService.removeCategoryById(category.getId());

        //clear
        UserService.removeUserById(user.getId());
    }

}