package org.danekja.discussment.core.service;

import org.danekja.discussment.core.domain.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Martin Bláha on 20.02.17.
 */
public class CategoryServiceTest {

    @Test
    public void createCategory() throws Exception {

        Category category = CategoryService.createCategory(new Category("category"));

        assertNotNull(category);

        //clear
        CategoryService.removeCategory(category);
    }

    @Test
    public void getCategory() throws Exception {
        Category category = CategoryService.createCategory(new Category("category"));

        assertNotNull(CategoryService.getCategoryById(category.getId()));

        //clear
        CategoryService.removeCategory(category);
    }

    @Test
    public void getCategories() throws Exception {
        CategoryService.createCategory(new Category("category1"));
        CategoryService.createCategory(new Category("category2"));

        assertEquals(2, CategoryService.getCategories().size());
    }

    @Test
    public void removeCategory() throws Exception {

        User user = UserService.addUser(new User("test", "", ""), new Permission());
        Category category = CategoryService.createCategory(new Category("category"));

        Topic topic = new Topic();
        topic.setName("test1");
        topic.setDescription("test des");

        topic = TopicService.createTopic(topic, category);

        Discussion discussion = new Discussion("test");
        discussion = DiscussionService.createDiscussion(discussion, topic);

        Post post = PostService.sendPost(discussion, new Post(user, "text"));
        PostService.sendReply(new Post(user, "reply text"), post);

        CategoryService.removeCategory(category);

        //clear
        UserService.removeUser(user);
    }

}