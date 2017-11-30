package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.dao.jpa.OldPermissionDaoJPA;
import org.danekja.discussment.core.accesscontrol.domain.Permission;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.accesscontrol.service.PermissionService;
import org.danekja.discussment.core.accesscontrol.service.impl.DefaultPermissionService;
import org.danekja.discussment.core.dao.jpa.DiscussionDaoJPA;
import org.danekja.discussment.core.dao.jpa.PostDaoJPA;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.mock.User;
import org.danekja.discussment.core.mock.UserDaoMock;
import org.danekja.discussment.core.service.imp.DefaultDiscussionService;
import org.danekja.discussment.core.service.imp.DefaultPostService;
import org.danekja.discussment.core.service.mock.DefaultUserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Martin Bláha on 19.02.17.
 */
public class PostServiceTest {

    private PermissionDao permissionDao;

    private DiscussionService discussionService;
    private PostService postService;
    private PermissionService permissionService;

    private Discussion discussion;
    private User user;
    private Permission p;

    @Before
    public void setUp() throws Exception {
        this.permissionDao = new OldPermissionDaoJPA();
        DiscussionUserService userService = new DefaultUserService(new UserDaoMock(), permissionDao);
        this.permissionService = new DefaultPermissionService(permissionDao, userService);
        discussionService = new DefaultDiscussionService(new DiscussionDaoJPA(), permissionService, userService);
        postService = new DefaultPostService(new PostDaoJPA(), userService);

        discussion = new Discussion("test");
        discussion = discussionService.createDiscussion(discussion);
        user = new User("test", "", "");
        user.setId(-100L);
        p = permissionService.addPermissionForUser(new Permission(), user);
    }

    @After
    public void tearDown() throws Exception {
        //discussionService.removeDiscussion(discussion);
        permissionDao.remove(p);
    }

    @Test
    public void removePost() throws Exception {
        Post post = new Post();
        post.setText("text");
        post.setUserId(user.getDiscussionUserId());
        post = postService.sendPost(discussion, post);

        Post reply1 = new Post();
        reply1.setText("reply1Text");
        reply1.setUserId(user.getDiscussionUserId());
        reply1 = postService.sendReply(reply1, post);

        Post reply2 = new Post();
        reply2.setText("reply2Text");
        reply2.setUserId(user.getDiscussionUserId());
        reply2 = postService.sendReply(reply2, post);

        Post reply3 = new Post();
        reply3.setText("reply3Text");
        reply3.setUserId(user.getDiscussionUserId());
        reply3 = postService.sendReply(reply3, reply2);

        postService.removePost(reply2);
        assertNull(postService.getPostById(reply2.getId()));

        postService.removePost(post);
        assertNull(postService.getPostById(post.getId()));
    }

    @Test
    public void sendPost() throws Exception {
        //test
        Post post = new Post();
        post.setText("text");
        post.setUserId(user.getDiscussionUserId());
        post = postService.sendPost(discussion, post);

        assertEquals(0, post.getLevel());
        assertEquals("text", post.getText());

        //clear
        postService.removePost(post);
    }

    @Test
    public void sendReply() throws Exception {
        //prepare
        Post post = new Post();
        post.setText("text");
        post.setUserId(user.getDiscussionUserId());
        post = postService.sendPost(discussion, post);

        //test
        Post reply = new Post();
        reply.setText("replyText");
        reply.setUserId(user.getDiscussionUserId());
        reply = postService.sendReply(reply, post);

        assertEquals(1, reply.getLevel());
        assertEquals("replyText", reply.getText());

        //clear
        postService.removePost(post);
    }

    @Test
    public void disablePost() throws Exception {
        //prepare
        Post post = new Post();
        post.setText("text");
        post.setUserId(user.getDiscussionUserId());
        post = postService.sendPost(discussion, post);

        postService.enablePost(post);

        //test
        post = postService.disablePost(post);
        assertTrue(post.isDisabled());

        //clear
        postService.removePost(post);
    }

    @Test
    public void enablePost() throws Exception {
        //prepare
        Post post = new Post();
        post.setText("text");
        post.setUserId(user.getDiscussionUserId());
        post = postService.sendPost(discussion, post);

        postService.disablePost(post);

        //test
        post = postService.enablePost(post);
        assertFalse(post.isDisabled());

        //clear
        postService.removePost(post);
    }

}