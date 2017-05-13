package org.danekja.discussment.core.service;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Permission;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.User;
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

    private Discussion discussion;
    private User user;

    @Before
    public void setUp() throws Exception {
        discussion = DiscussionService.createDiscussion(new Discussion("test"));
        user = UserService.addUser(new User("test", "test", "test"), new Permission());
    }

    @After
    public void tearDown() throws Exception {
        DiscussionService.removeDiscussion(discussion);
        UserService.removeUser(user);
    }

    @Test
    public void removePost() throws Exception {
        Post post = new Post();
        post.setText("text");
        post.setUser(user);
        post = PostService.sendPost(discussion, post);

        Post reply1 = new Post();
        reply1.setText("reply1Text");
        reply1.setUser(user);
        reply1 = PostService.sendReply(reply1, post);

        Post reply2 = new Post();
        reply2.setText("reply2Text");
        reply2.setUser(user);
        reply2 = PostService.sendReply(reply2, post);

        Post reply3 = new Post();
        reply3.setText("reply3Text");
        reply3.setUser(user);
        reply3 = PostService.sendReply(reply3, reply2);

        PostService.removePost(reply2);
        assertNull(PostService.getPostById(reply2.getId()));

        PostService.removePost(post);
        assertNull(PostService.getPostById(post.getId()));
    }

    @Test
    public void sendPost() throws Exception {
        //test
        Post post = new Post();
        post.setText("text");
        post.setUser(user);
        post = PostService.sendPost(discussion, post);

        assertEquals(0, post.getLevel());
        assertEquals("text", post.getText());

        //clear
        PostService.removePost(post);
    }

    @Test
    public void sendReply() throws Exception {
        //prepare
        Post post = new Post();
        post.setText("text");
        post.setUser(user);
        post = PostService.sendPost(discussion, post);

        //test
        Post reply = new Post();
        reply.setText("replyText");
        reply.setUser(user);
        reply = PostService.sendReply(reply, post);

        assertEquals(1, reply.getLevel());
        assertEquals("replyText", reply.getText());

        //clear
        PostService.removePost(post);
    }

    @Test
    public void disablePost() throws Exception {
        //prepare
        Post post = new Post();
        post.setText("text");
        post.setUser(user);
        post = PostService.sendPost(discussion, post);

        PostService.enablePost(post);

        //test
        post = PostService.disablePost(post);
        assertTrue(post.isDisabled());

        //clear
        PostService.removePost(post);
    }

    @Test
    public void enablePost() throws Exception {
        //prepare
        Post post = new Post();
        post.setText("text");
        post.setUser(user);
        post = PostService.sendPost(discussion, post);

        PostService.disablePost(post);

        //test
        post = PostService.enablePost(post);
        assertFalse(post.isDisabled());

        //clear
        PostService.removePost(post);
    }

}