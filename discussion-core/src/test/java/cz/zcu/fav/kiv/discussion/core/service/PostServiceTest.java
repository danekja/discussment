package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.model.DiscussionModel;
import cz.zcu.fav.kiv.discussion.core.model.PostModel;
import cz.zcu.fav.kiv.discussion.core.model.UserModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Martin Bláha on 19.02.17.
 */
public class PostServiceTest {

    private DiscussionModel discussion;
    private UserModel user;

    @Before
    public void setUp() throws Exception {
        discussion = DiscussionService.createDiscussion("test");
        user = UserService.addUser("test", "test", "test");
    }

    @After
    public void tearDown() throws Exception {
        DiscussionService.removeDiscussionById(discussion.getId());
        UserService.removeUserById(user.getId());
    }

    @Test
    public void removePostById() throws Exception {
        PostModel post = PostService.sendPost(discussion.getId(), user.getId(), "text");


        PostModel reply1 = PostService.sendReply(user.getId(), "reply1Text", post.getId());


        PostModel reply2 = PostService.sendReply(user.getId(), "reply2Text", post.getId());
        PostModel reply3 = PostService.sendReply(user.getId(), "reply3Text", reply2.getId());


        PostService.removePostById(reply2.getId());
        assertNull(PostService.getPostById(reply2.getId()));


        PostService.removePostById(post.getId());
        assertNull(PostService.getPostById(post.getId()));

    }

    @Test
    public void sendPost() throws Exception {
        //test
        PostModel post = PostService.sendPost(discussion.getId(), user.getId(), "text");

        assertEquals(0, post.getLevel());
        assertEquals("text", post.getText());

        //clear
        PostService.removePostById(post.getId());
    }

    @Test
    public void sendReply() throws Exception {
        //prepare
        PostModel post = PostService.sendPost(discussion.getId(), user.getId(), "text");

        //test
        PostModel reply = PostService.sendReply(user.getId(), "replyText", post.getId());
        assertEquals(1, reply.getLevel());
        assertEquals("replyText", reply.getText());

        //clear
        PostService.removePostById(post.getId());
    }

    @Test
    public void disablePost() throws Exception {
        //prepare
        PostModel post = PostService.sendPost(discussion.getId(), user.getId(), "text");
        PostService.enablePost(post.getId());

        //test
        post = PostService.disablePost(post.getId());
        assertTrue(post.isDisabled());

        //clear
        PostService.removePostById(post.getId());
    }

    @Test
    public void enablePost() throws Exception {
        //prepare
        PostModel post = PostService.sendPost(discussion.getId(), user.getId(), "text");
        PostService.disablePost(post.getId());

        //test
        post = PostService.enablePost(post.getId());
        assertFalse(post.isDisabled());

        //clear
        PostService.removePostById(post.getId());
    }

}