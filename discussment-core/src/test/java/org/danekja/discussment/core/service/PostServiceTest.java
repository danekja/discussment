package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.*;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.mock.User;
import org.danekja.discussment.core.service.imp.DefaultPostService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.*;

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
    private PermissionDao permissionDao;

    @Mock
    private DiscussionUserService discussionUserService;

    @Mock
    private DiscussionService discussionService;

    private PostService postService;

    private List<Post> postRepository;

    @BeforeClass
    public static void setUpGlobal() {
        testUser = new User(-100L, "PMS Test User");

    }

    @Before
    public void setUp() throws DiscussionUserNotFoundException {
        MockitoAnnotations.initMocks(PostServiceTest.class);

        postRepository = new ArrayList<>();

        when(discussionUserService.getCurrentlyLoggedUser()).then(invocationOnMock -> testUser);
        when(discussionUserService.getUserById(anyString())).then(invocationOnMock -> testUser);
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
        when(postDao.getNumbersOfPosts(anyListOf(Long.class))).then((invocationOnMock -> {
            List<Long> discussionIds = (List<Long>) invocationOnMock.getArguments()[0];

            Map<Long, Long> numbersOfPosts = new HashMap<>();
            for (Post p : postRepository) {
                if (numbersOfPosts.containsKey(p.getDiscussion().getId())) {
                    Long number = numbersOfPosts.get(p.getDiscussion().getId());
                    numbersOfPosts.replace(p.getDiscussion().getId(), number + 1L);
                } else {
                    numbersOfPosts.put(p.getDiscussion().getId(), 1L);
                }
            }

            Map<Long, Long> resultMap = new HashMap<>();
            for (Long discussionId : discussionIds) {
                if (numbersOfPosts.containsKey(discussionId)) {
                    resultMap.put(discussionId, numbersOfPosts.get(discussionId));
                }
            }

            return resultMap;
        }));

        AccessControlService accessControlService = new PermissionService(permissionDao, discussionUserService) {
            @Override
            protected List<PostPermission> getPostPermissions(IDiscussionUser user, Discussion discussion) {
                return Collections.emptyList();
            }

            @Override
            protected boolean checkPermissions(Action action, List<? extends AbstractPermission> permissions) {
                return true;
            }
        };

        postService = new DefaultPostService(postDao, discussionUserService, accessControlService);
    }

    @Test
    public void testGetPostById() throws AccessDeniedException {
        long postId = 1L;

        when(postDao.getById(postId)).thenReturn(null);

        assertNull(postService.getPostById(postId));
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
        assertEquals("Wrong name of the author of the post!", testUser.getDisplayName(), postService.getPostAuthor(post).getDisplayName());
    }

    @Test
    public void testSendReply() throws AccessDeniedException, DiscussionUserNotFoundException {
        Post originalPost = new Post(testUser, "original post");
        originalPost = postService.sendPost(new Discussion(), originalPost);

        Post reply = new Post(testUser, "reply to original post");
        reply = postService.sendReply(reply, originalPost);

        assertNotNull("Reply shouldn't be null!", postService.getPostById(reply.getId()));
        assertNotNull("Author of the reply shouldn't be null!", postService.getPostAuthor(reply));
        assertEquals("Wrong name of the author of the reply!", testUser.getDisplayName(), postService.getPostAuthor(reply).getDisplayName());
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

    @Test
    public void testGetNumbersOfPosts() throws AccessDeniedException {
        when(discussionService.createDiscussion(any(Topic.class), any(Discussion.class))).then(invocationOnMock -> invocationOnMock.getArguments()[1]);

        Discussion discussion1 = new Discussion(55L,"Some discussion");
        discussion1 = discussionService.createDiscussion(new Topic(), discussion1);
        Post post1 = new Post(testUser, "bar");
        postService.sendPost(discussion1, post1);

        Discussion discussion2 = new Discussion(56L,"Other discussion");
        discussion2 = discussionService.createDiscussion(new Topic(), discussion2);
        Post post2 = new Post(testUser, "bar");
        postService.sendPost(discussion2, post2);
        Post post3 = new Post(testUser, "baz");
        postService.sendPost(discussion2, post3);

        Discussion discussion3 = new Discussion(57L,"Yet another discussion");
        discussion3 = discussionService.createDiscussion(new Topic(), discussion3);

        Map<Long, Long> numbersOfPosts = postService.getNumbersOfPosts(Arrays.asList(discussion1.getId(), discussion2.getId(), discussion3.getId()));
        assertEquals(1L, numbersOfPosts.get(discussion1.getId()).longValue());
        assertEquals(2L, numbersOfPosts.get(discussion2.getId()).longValue());
        assertFalse(numbersOfPosts.containsKey(discussion3.getId()));
    }

    /**
     * Mock post removing form the repository.
     */
    private class RemovePostMock implements Answer<Void> {
        @Override
        public Void answer(InvocationOnMock invocationOnMock) {
            Post post = (Post) invocationOnMock.getArguments()[0];
            Iterator<Post> pIterator = postRepository.iterator();
            while(pIterator.hasNext()) {
                Post p = pIterator.next();
                if (p.equals(post) || (p.getChainId() != null && p.getChainId().contains(post.getId().toString()))) {
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
        public Post answer(InvocationOnMock invocationOnMock) {
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

            postRepository.add(post);
            return post;
        }
    }
}

