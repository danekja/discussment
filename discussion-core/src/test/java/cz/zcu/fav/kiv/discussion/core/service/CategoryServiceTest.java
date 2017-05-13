package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.entity.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Martin Bláha on 20.02.17.
 */
public class CategoryServiceTest {

    @Test
    public void createCategory() throws Exception {

        CategoryEntity category = CategoryService.createCategory(new CategoryEntity("category"));

        assertNotNull(category);

        //clear
        CategoryService.removeCategory(category);
    }

    @Test
    public void getCategory() throws Exception {
        CategoryEntity category = CategoryService.createCategory(new CategoryEntity("category"));

        assertNotNull(CategoryService.getCategoryById(category.getId()));

        //clear
        CategoryService.removeCategory(category);
    }

    @Test
    public void getCategories() throws Exception {
        CategoryService.createCategory(new CategoryEntity("category1"));
        CategoryService.createCategory(new CategoryEntity("category2"));

        assertEquals(2, CategoryService.getCategories().size());
    }

    @Test
    public void removeCategory() throws Exception {

        UserEntity user = UserService.addUser(new UserEntity("test", "", ""), new PermissionEntity());
        CategoryEntity category = CategoryService.createCategory(new CategoryEntity("category"));

        TopicEntity topic = new TopicEntity();
        topic.setName("test1");
        topic.setDescription("test des");

        topic = TopicService.createTopic(topic, category);

        DiscussionEntity discussion = new DiscussionEntity("test");
        discussion = DiscussionService.createDiscussion(discussion, topic);

        PostEntity post = PostService.sendPost(discussion, new PostEntity(user, "text"));
        PostService.sendReply(new PostEntity(user, "reply text"), post);

        CategoryService.removeCategory(category);

        //clear
        UserService.removeUser(user);
    }

}