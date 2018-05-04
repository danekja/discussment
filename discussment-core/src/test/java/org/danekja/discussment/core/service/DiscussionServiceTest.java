package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.DiscussionDao;
import org.danekja.discussment.core.dao.PostDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.mock.User;
import org.danekja.discussment.core.service.imp.DefaultDiscussionService;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * This test case is using services with accesscontrol component.
 */
@RunWith(MockitoJUnitRunner.class)
public class DiscussionServiceTest {


    private static User testUser;

    @Mock
    private DiscussionDao discussionDao;

    @Mock
    private PostDao postDao;


    @Mock
    private AccessControlService accessControlService;

    @Mock
    private DiscussionUserService discussionUserService;

    @Mock
    private TopicService topicService;

    private DiscussionService discussionService;


    @BeforeClass
    public static void setUpGlobal() {
        testUser = new User(-100L, "PMS Test User");
    }

    @Before
    public void setUp() throws DiscussionUserNotFoundException {
        MockitoAnnotations.initMocks(DiscussionServiceTest.class);

        when(discussionUserService.getCurrentlyLoggedUser()).then(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                return testUser;
            }
        });
        when(discussionUserService.getUserById(anyString())).then(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                return testUser;
            }
        });
        when(accessControlService.canRemoveDiscussion(any(Discussion.class))).then(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                return true;
            }
        });
        when(accessControlService.canAddDiscussion(any(Topic.class))).then(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                return true;
            }
        });
        when(accessControlService.canViewDiscussions(any(Topic.class))).then(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                return true;
            }
        });
        when(accessControlService.canEditDiscussion(any(Discussion.class))).then(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                return true;
            }
        });
        when(accessControlService.canViewPosts(any(Discussion.class))).then(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                return true;
            }
        });


        discussionService = new DefaultDiscussionService(discussionDao, postDao, topicService, accessControlService, discussionUserService);
    }

    @Test
    public void testCreateDiscussion() throws AccessDeniedException, DiscussionUserNotFoundException {
        Discussion discussion = new Discussion(55L,"Some discussion");
        when(discussionDao.save(any(Discussion.class))).then(new Answer<Discussion>() {
            @Override
            public Discussion answer(InvocationOnMock invocationOnMock) throws Throwable {
                return (Discussion) invocationOnMock.getArguments()[0];
            }
        });
        when(postDao.getBasePostsByDiscussion(any(Discussion.class))).then(new Answer<List<Post>>() {
            @Override
            public List<Post> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return Collections.emptyList();
            }
        });
        discussion = discussionService.createDiscussion(new Topic(), discussion);

        assertNotNull("Discussion is null!", discussion);
        assertNotNull("Id is null!", discussion.getId());
        assertNull("There shouldn't be any posts in discussion!", discussionService.getLastPostAuthor(discussion));
        assertNotNull("Discussion has null topic!", discussion.getTopic());
    }

    @Test
    public void testGetDiscussionsByTopic() throws AccessDeniedException {
        final Discussion discussion1 = new Discussion(-10L, "discussion1");
        final Discussion discussion2 = new Discussion(-11L, "discussion2");

        when(discussionDao.getDiscussionsByTopic(any(Topic.class))).then(new Answer<List<Discussion>>() {
            @Override
            public List<Discussion> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return Arrays.asList(discussion1, discussion2);
            }
        });

        List<Discussion> discussions = discussionService.getDiscussionsByTopic(new Topic());
        assertNotNull("Null list returned!", discussions);
        assertEquals("Wrong number of discussions returned!", 2, discussions.size());
        assertTrue("Discussion 1 not included!", discussions.contains(discussion1));
        assertTrue("Discussion 2 not included!", discussions.contains(discussion2));
    }

    @Test
    public void testGetDiscussionById() throws AccessDeniedException {
        final Discussion discussion1 = new Discussion(-10L, "discussion1");

        when(discussionDao.getById(-10L)).then(new Answer<Discussion>() {
            @Override
            public Discussion answer(InvocationOnMock invocationOnMock) throws Throwable {
                return discussion1;
            }
        });

        assertEquals("Wrong discussion returned!", discussion1, discussionService.getDiscussionById(-10L));
        assertNull("Discussion with id -12345 shouldn't exist!", discussionService.getDiscussionById(-12345L));
    }

    @Test
    public void testRemoveDiscussion() throws AccessDeniedException {
        final List<Discussion> discussionRepository = new ArrayList<>();

        // mock get, save and remove methods
        when(discussionDao.getById(any(Long.class))).then(new Answer<Discussion>() {
            @Override
            public Discussion answer(InvocationOnMock invocationOnMock) throws Throwable {
                Long id = (Long) invocationOnMock.getArguments()[0];
                for (Discussion d : discussionRepository) {
                    if (d.getId().equals(id)) {
                        return d;
                    }
                }

                return null;
            }
        });
        when(discussionDao.save(any(Discussion.class))).then(new Answer<Discussion>() {
            @Override
            public Discussion answer(InvocationOnMock invocationOnMock) throws Throwable {
                Discussion discussion = (Discussion) invocationOnMock.getArguments()[0];
                discussionRepository.add(discussion);
                return discussion;
            }
        });
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Discussion toBeRemoved = (Discussion) invocationOnMock.getArguments()[0];
                for(Discussion d : discussionRepository) {
                    if(d.getId().equals(toBeRemoved.getId())) {
                        discussionRepository.remove(d);
                        break;
                    }
                }

                return invocationOnMock;
            }
        }).when(discussionDao).remove(any(Discussion.class));

        // create discussion to be removed
        Discussion discussion = new Discussion(55L, "Test discussion");
        discussionService.createDiscussion(new Topic(), discussion);

        // test discussion removing
        Discussion toBeRemoved = discussionService.getDiscussionById(discussion.getId());
        assertNotNull("Discussion to be removed not found!", toBeRemoved);
        discussionService.removeDiscussion(toBeRemoved);
        assertNull("Discussion not removed!", discussionService.getDiscussionById(toBeRemoved.getId()));
    }

    @Test
    public void testGetLastPostAuthor() throws DiscussionUserNotFoundException, AccessDeniedException {
        when(postDao.getLastPost(any(Discussion.class))).then(new Answer<Post>() {
            @Override
            public Post answer(InvocationOnMock invocationOnMock) throws Throwable {
                return new Post(testUser, "Test post");
            }
        });

        assertEquals("Wrong author!", testUser.getDisplayName(), discussionService.getLastPostAuthor(new Discussion()).getDisplayName());
    }
}
