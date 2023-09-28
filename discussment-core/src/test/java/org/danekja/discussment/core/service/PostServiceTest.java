package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.*;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.accesscontrol.service.impl.PermissionService;
import org.danekja.discussment.core.configuration.service.ConfigurationService;
import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.exception.MaxReplyLevelExceeded;
import org.danekja.discussment.core.exception.MessageLengthExceeded;
import org.danekja.discussment.core.mock.User;
import org.danekja.discussment.core.service.imp.DefaultPostService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


/**
 * This test case is using services with accesscontrol component.
 */
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    private static User testUser;

    @Mock
    private PostDao postDao;

    @Mock
    private PermissionDao permissionDao;

    @Mock
    private DiscussionUserService discussionUserService;

    @Mock
    private DiscussionService discussionService;

    @Mock
    private ConfigurationService configurationService;

    private PostService postService;

    private List<Post> postRepository;

    @BeforeAll
    static void setUpGlobal() {
        testUser = new User("john.doe", "John Doe");
    }

    @BeforeEach
    void setUp() throws DiscussionUserNotFoundException {
        postRepository = new ArrayList<>();

        lenient().when(discussionUserService.getCurrentlyLoggedUser()).thenReturn(testUser);
        lenient().when(discussionUserService.getUserById(anyString())).thenReturn(testUser);
        lenient().when(postDao.save(any(Post.class))).then(new SavePostMock());
        lenient().doAnswer(new RemovePostMock()).when(postDao).remove(any(Post.class));
        lenient().when(postDao.getById(anyLong())).then((invocationOnMock) -> {
            Long id = invocationOnMock.getArgument(0);
            if (id != null) {
                for (Post p : postRepository) {
                    if (p.getId().equals(id)) {
                        return p;
                    }
                }
            }
            return null;
        });
        lenient().when(postDao.getNumbersOfPosts(anyList())).then((invocationOnMock -> {
            List<Long> discussionIds = invocationOnMock.getArgument(0);

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
        lenient().when(configurationService.maxReplyLevel()).thenReturn(ConfigurationService.UNLIMITED_REPLY_LEVEL);
        lenient().when(configurationService.messageLengthLimit()).thenReturn(ConfigurationService.DEFAULT_MESSAGE_LIMIT);

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

        postService = new DefaultPostService(postDao, discussionUserService, accessControlService, configurationService);
    }

    @Test
    void getPostById() throws AccessDeniedException {
        long postId = 1L;

        when(postDao.getById(postId)).thenReturn(null);

        assertNull(postService.getPostById(postId));
    }

    /**
     * Create post, add some replies, delete root post (with permissions set) and verify
     * that the whole chain has been deleted.
     */
    @Test
    void removePost1() throws MaxReplyLevelExceeded, AccessDeniedException, MessageLengthExceeded {
        Post rootPost = new Post(testUser, "Root post");
        rootPost = postService.sendPost(new Discussion(), rootPost);
        Post reply = new Post(testUser, "This is a reply");
        reply = postService.sendReply(reply, rootPost);

        postService.removePost(rootPost);
        assertNull(postService.getPostById(rootPost.getId()), "Root post should be null!");
        assertNull(postService.getPostById(reply.getId()), "Reply should be null!");
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
    void removePost2() throws MaxReplyLevelExceeded, AccessDeniedException, MessageLengthExceeded {
        Post root = new Post(testUser, "root");
        Post reply1 = new Post(testUser, "reply1");
        Post reply2 = new Post(testUser, "reply2");
        Post reply3 = new Post(testUser, "reply3");

        root = postService.sendPost(new Discussion(), root);
        reply1 = postService.sendReply(reply1, root);
        reply2 = postService.sendReply(reply2, reply1);
        reply3 = postService.sendReply(reply3, root);

        postService.removePost(reply1);
        assertNull(postService.getPostById(reply1.getId()), "Reply 1 should be null!");
        assertNull(postService.getPostById(reply2.getId()), "Reply 2 should be null!");
        assertNotNull(postService.getPostById(reply3.getId()), "Reply 3 shouldn't be null!");
        assertNotNull(postService.getPostById(root.getId()), "Root shouldn't be null!");
    }

    @Test
    void sendPost() throws AccessDeniedException, DiscussionUserNotFoundException, MessageLengthExceeded {
        Post post = new Post(testUser, "root");
        post = postService.sendPost(new Discussion(), post);

        assertNotNull(postService.getPostById(post.getId()), "Post shouldn't be null!");
        assertNotNull(postService.getPostAuthor(post), "Author of the post shouldn't be null!");
        assertEquals(testUser.getDisplayName(), postService.getPostAuthor(post).getDisplayName(), "Wrong name of the author of the post!");
    }

    @Test
    void sendPost_messageLengthExceeded() {
        when(configurationService.messageLengthLimit()).thenReturn(1);

        Post post = new Post(testUser, "text");
        assertThrows(MessageLengthExceeded.class, () -> postService.sendPost(new Discussion(), post));
    }

    @Test
    void sendReply() throws MaxReplyLevelExceeded, AccessDeniedException, DiscussionUserNotFoundException, MessageLengthExceeded {
        Post originalPost = new Post(testUser, "original post");
        originalPost = postService.sendPost(new Discussion(), originalPost);

        Post reply = new Post(testUser, "reply to original post");
        reply = postService.sendReply(reply, originalPost);

        assertNotNull(postService.getPostById(reply.getId()), "Reply shouldn't be null!");
        assertNotNull(postService.getPostAuthor(reply), "Author of the reply shouldn't be null!");
        assertEquals(testUser.getDisplayName(), postService.getPostAuthor(reply).getDisplayName(), "Wrong name of the author of the reply!");
    }

    @Test
    void sendReply_maxReplyLevelExceeded() throws AccessDeniedException, MessageLengthExceeded {
        when(configurationService.maxReplyLevel()).thenReturn(0);

        Post originalPost = postService.sendPost(new Discussion(), new Post(testUser, "original post"));

        Post reply = new Post(testUser, "reply to original post");
        assertThrows(MaxReplyLevelExceeded.class, () -> postService.sendReply(reply, originalPost));
    }

    @Test
    void sendReply_messageLengthExceeded() throws AccessDeniedException, MessageLengthExceeded {
        when(configurationService.maxReplyLevel()).thenReturn(1);
        when(configurationService.messageLengthLimit()).thenReturn(20);

        Post originalPost = postService.sendPost(new Discussion(), new Post(testUser, "Original post"));

        Post reply = new Post(testUser, "This reply is very very long and should fail");
        assertThrows(MessageLengthExceeded.class, () -> postService.sendReply(reply, originalPost));
    }

    @Test
    void disablePost() throws AccessDeniedException, MessageLengthExceeded {
        Post post = new Post(testUser, "some text");
        post = postService.sendPost(new Discussion(), post);

        postService.disablePost(post);

        Post disabledPost = postService.getPostById(post.getId());
        assertNotNull(disabledPost, "Post is null after disabling!");
        assertTrue(disabledPost.isDisabled(), "Post is not disabled!");
    }

    @Test
    void enablePost() throws AccessDeniedException, MessageLengthExceeded {
        Post post = new Post(testUser, "some text");
        post.setDisabled(true);
        post = postService.sendPost(new Discussion(), post);

        postService.enablePost(post);
        Post enabledPost = postService.getPostById(post.getId());
        assertNotNull(enabledPost, "Post is null after enabling!");
        assertFalse(enabledPost.isDisabled(), "Post is not enabled!");
    }

    @Test
    void getNumbersOfPosts() throws AccessDeniedException, MessageLengthExceeded {
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
            Post post = (Post) invocationOnMock.getArgument(0);
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
            Post post = (Post) invocationOnMock.getArgument(0);

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

