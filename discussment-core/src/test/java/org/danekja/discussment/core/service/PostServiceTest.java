package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.mock.User;
import org.danekja.discussment.core.service.imp.NewPostService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * This test case is using services with accesscontrol component.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    private static User testUser;

    @Mock
    private PostDao postDao;

    @Mock
    private AccessControlService accessControlService;

    @Mock
    private DiscussionUserService discussionUserService;

    private PostService postService;

    private List<Post> postRepository;

    @BeforeClass
    public static void setUpGlobal() throws Exception {
        testUser = new User(-100L, "PMS Test User");
    }

    @Before
    public void setUp() throws DiscussionUserNotFoundException {
        MockitoAnnotations.initMocks(PostServiceTest.class);

        postRepository = new ArrayList<>();

        when(discussionUserService.getCurrentlyLoggedUser()).then(invocationOnMock -> testUser);
        when(discussionUserService.getUserById(anyString())).then(invocationOnMock -> testUser);
        when(accessControlService.canRemovePost(any(Post.class))).then(invocationOnMock -> true);
        when(accessControlService.canAddPost(any(Discussion.class))).then(invocationOnMock -> true);
        when(accessControlService.canViewPost(any(Post.class))).then(invocationOnMock -> true);
        when(accessControlService.canEditPost(any(Post.class))).then(invocationOnMock -> true);
        when(postDao.save(any(Post.class))).then(new SavePostMock());
        doAnswer(new RemovePostMock()).when(postDao).remove(any(Post.class));
        when(postDao.getById(anyLong())).then((invocationOnMock) -> {
            Long id = (Long) invocationOnMock.getArguments()[0];
            if(id == null) {
               return null;
           } else {
               for(Post p : postRepository) {
                   if (p.getId().equals(id)) {
                       return p;
                   }
               }
               return null;
           }
        });

        postService = new NewPostService(discussionUserService, accessControlService, postDao);
    }

    /**
     * Create post, add some replies, delete root post (with permissions set) and verify
     * that the whole chain has been deleted.
     */
    @Test
    public void testRemovePost1() throws AccessDeniedException {
        Post rootPost = new Post(testUser, "Root post");
        rootPost = postService.sendPost(new Discussion(), rootPost);
        Post reply = new Post(testUser, "This is a reply");
        reply = postService.sendReply(reply, rootPost);

        postService.removePost(rootPost);
        assertNull("Root post should be null!", postService.getPostById(rootPost.getId()));
        assertNull("Reply should be null!", postService.getPostById(reply.getId()));
    }

    /**
     * Create following post tree
     * root +
     *      - reply1 +
     *               - reply2
     *      - reply3
     * Delete reply 1 and verify that root and reply 3 are still present.
     */
    @Test
    public void testRemovePost2() throws AccessDeniedException {
        Post root = new Post(testUser, "root");
        Post reply1 = new Post(testUser, "reply1");
        Post reply2 = new Post(testUser, "reply2");
        Post reply3 = new Post(testUser, "reply3");

        root = postService.sendPost(new Discussion(), root);
        reply1 = postService.sendReply(reply1, root);
        reply2 = postService.sendReply(reply2, reply1);
        reply3 = postService.sendReply(reply3, root);

        postService.removePost(reply1);
        assertNull("Reply 1 should be null!", postService.getPostById(reply1.getId()));
        assertNull("Reply 2 should be null!", postService.getPostById(reply2.getId()));
        assertNotNull("Reply 3 shouldn't be null!", postService.getPostById(reply3.getId()));
        assertNotNull("Root shouldn't be null!", postService.getPostById(root.getId()));
    }

    @Test
    public void testSendPost() throws AccessDeniedException, DiscussionUserNotFoundException {
        Post post = new Post(testUser, "root");
        post = postService.sendPost(new Discussion(), post);

        assertNotNull("Post shouldn't be null!", postService.getPostById(post.getId()));
        assertNotNull("Author of the post shouldn't be null!", postService.getPostAuthor(post));
        assertEquals("Wrong name of the author of the post!", testUser.getDisplayName(), postService.getPostAuthor(post));
    }

    @Test
    public void testSendReply() throws AccessDeniedException, DiscussionUserNotFoundException {
        Post originalPost = new Post(testUser, "original post");
        originalPost = postService.sendPost(new Discussion(), originalPost);

        Post reply = new Post(testUser, "reply to original post");
        reply = postService.sendReply(reply, originalPost);

        assertNotNull("Reply shouldn't be null!", postService.getPostById(reply.getId()));
        assertNotNull("Author of the reply shouldn't be null!", postService.getPostAuthor(reply));
        assertEquals("Wrong name of the author of the reply!", testUser.getDisplayName(), postService.getPostAuthor(reply));
    }

    @Test
    public void testDisablePost() throws AccessDeniedException {
        Post post = new Post(testUser, "some text");
        post = postService.sendPost(new Discussion(), post);

        postService.disablePost(post);

        Post disabledPost = postService.getPostById(post.getId());
        assertNotNull("Post is null after disabling!", disabledPost);
        assertTrue("Post is not disabled!", disabledPost.isDisabled());
    }

    @Test
    public void testEnablePost() throws AccessDeniedException {
        Post post = new Post(testUser, "some text");
        post.setDisabled(true);
        post = postService.sendPost(new Discussion(), post);

        postService.enablePost(post);
        Post enabledPost = postService.getPostById(post.getId());
        assertNotNull("Post is null after enabling!", enabledPost);
        assertTrue("Post is not enabled!", !enabledPost.isDisabled());
    }

    /**
     * Mock post removing form the repository.
     */
    private class RemovePostMock implements Answer<Void> {
        @Override
        public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
            Post post = (Post) invocationOnMock.getArguments()[0];
            Iterator<Post> pIterator = postRepository.iterator();
            while(pIterator.hasNext()) {
                Post p = pIterator.next();
                if (p.equals(post) || p.getChainId().startsWith(post.getChainId())) {
                    pIterator.remove();
                }
            }

            return null;
        }
    }

    /**
     * Mock post saving.
     */
    private class SavePostMock implements Answer<Post> {
        @Override
        public Post answer(InvocationOnMock invocationOnMock) throws Throwable {
            Post post = (Post) invocationOnMock.getArguments()[0];

            if (post.getId() != null) {
                return mergePost(post);
            } else {
                return savePost(post);
            }
        }

        /**
         * Merges existing post. Just removes the old post and replaces it with the new one.
         * @param post Post to be merged.
         * @return Merged post.
         */
        private Post mergePost(Post post) {
            for (Post p : postRepository) {
                if(p.getId().equals(post.getId())) {
                    postRepository.remove(p);
                    break;
                }
            }

            postRepository.add(post);
            return post;
        }

        /**
         * Saves new post and generates its id.
         * @return Saved post.
         */
        private Post savePost(Post post) {
            Long max = Long.MIN_VALUE;
            if (postRepository.isEmpty()) {
                max = 0L;
            }
            for (Post p : postRepository) {
                if (p.getId() > max) {
                    max = p.getId();
                }
            }
            post.setId(max+1);
            post.appendToChainId(post.getId().toString());

            postRepository.add(post);
            return post;
        }
    }
}
