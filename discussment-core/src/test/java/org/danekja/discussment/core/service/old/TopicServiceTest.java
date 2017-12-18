package org.danekja.discussment.core.service.old;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.dao.jpa.OldPermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.domain.Permission;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.accesscontrol.service.PermissionService;
import org.danekja.discussment.core.accesscontrol.service.impl.DefaultPermissionService;
import org.danekja.discussment.core.dao.jpa.CategoryDaoJPA;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.core.dao.jpa.PostDaoJPA;
import org.danekja.discussment.core.dao.jpa.TopicDaoJPA;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.mock.User;
import org.danekja.discussment.core.mock.UserDaoMock;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.core.service.DiscussionService;
import org.danekja.discussment.core.service.PostService;
import org.danekja.discussment.core.service.TopicService;
import org.danekja.discussment.core.service.imp.DefaultCategoryService;
import org.danekja.discussment.core.service.imp.DefaultDiscussionService;
import org.danekja.discussment.core.service.imp.DefaultPostService;
import org.danekja.discussment.core.service.imp.DefaultTopicService;
import org.danekja.discussment.core.service.mock.DefaultUserService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Martin Bláha on 20.02.17.
 */

// TODO: write similar tests for new access control package
// tests should check out the basic stuff - create post / discussion / topic, assign permission to some users
// and check that they have access (also try different levels)
// also check same stuff for users without permissions

public class TopicServiceTest {

    private TopicService topicService;
    private CategoryService categoryService;
    private DiscussionService discussionService;
    private PostService postService;
    private PermissionService permissionService;

    private PermissionDao permissionDao;

    private Category category;

    @Before
    public void setUp() throws Exception {
        this.permissionDao = new OldPermissionDaoJPA();
        DiscussionUserService userService = new DefaultUserService(new UserDaoMock(), permissionDao);
        topicService = new DefaultTopicService(new TopicDaoJPA(), new CategoryDaoJPA());
        categoryService = new DefaultCategoryService(new CategoryDaoJPA());
        this.permissionService = new DefaultPermissionService(permissionDao, userService);
        discussionService = new DefaultDiscussionService(new DiscussionDaoJPA(), permissionService, userService);
        postService = new DefaultPostService(new PostDaoJPA(), userService);

        category = categoryService.createCategory(new Category("text"));
    }

    @Test
    @Ignore
    public void createTopicWithoutCategory() throws Exception {
        Topic topic = new Topic();
        topic.setName("test");
        topic.setDescription("test det");

//        topic = topicService.createTopic(topic);

        assertNotNull(topic);
    }

    @Test
    public void createTopic() throws Exception {
        Topic topic = new Topic();
        topic.setName("test");
        topic.setDescription("test det");

        topic = topicService.createTopic(category, topic);

        assertNotNull(topic);
    }

    @Test
    public void getTopicsByCategory() throws Exception {
        Topic topic1 = new Topic();
        topic1.setName("test1");
        topic1.setDescription("test det");
        topicService.createTopic(category, topic1);

        Topic topic2 = new Topic();
        topic2.setName("test2");
        topic2.setDescription("test det");
        topicService.createTopic(category, topic2);

        assertEquals(2, topicService.getTopicsByCategory(category).size());
    }

    @Test
    @Ignore
    public void getTopicsWithoutCategory() throws Exception {
        Topic topic1 = new Topic();
        topic1.setName("test1");
        topic1.setDescription("test det");
//        topicService.createTopic(topic1);

        Topic topic2 = new Topic();
        topic2.setName("test2");
        topic2.setDescription("test det");
        topicService.createTopic(category, topic2);

        Topic topic3 = new Topic();
        topic3.setName("test3");
        topic3.setDescription("test det");
        topicService.createTopic(category, topic3);

        assertEquals(1, topicService.getTopicsWithoutCategory().size());
    }

    @Test
    public void removeTopic() throws Exception {
        User user = new User("test", "", "");
        user.setId(-100L);
        Permission p = permissionService.addPermissionForUser(new Permission(), user);

        Topic topic = new Topic();
        topic.setName("test");
        topic.setDescription("test det");
        topic = topicService.createTopic(category, topic);

        Discussion IDiscussion = discussionService.createDiscussion(topic,new Discussion("test"));

        Post post = new Post();
        post.setText("text");
        post.setUserId(user.getDiscussionUserId());
        post = postService.sendPost(IDiscussion, post);

        Post reply = new Post();
        reply.setText("reply1Text");
        reply.setUserId(user.getDiscussionUserId());
        postService.sendReply(reply, post);

        topicService.removeTopic(topic);

        //clear
        permissionDao.remove(p);
    }

    @Test
    public void removeTopicWithUserAccess() throws Exception {
        User user = new User("test", "", "");
        user.setId(-100L);
        Permission p = permissionService.addPermissionForUser(new Permission(), user);

        Topic topic = new Topic();
        topic.setName("test");
        topic.setDescription("test det");
        topic = topicService.createTopic(category, topic);

        Discussion discussion = discussionService.createDiscussion(topic, new Discussion("test"));


//        discussionService.addAccessToDiscussion(user, discussion);


        topicService.removeTopic(topic);


        //clear
        permissionDao.remove(p);
    }

}