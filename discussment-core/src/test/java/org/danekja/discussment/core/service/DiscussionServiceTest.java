package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.DiscussionDao;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.mock.User;
import org.danekja.discussment.core.service.imp.DefaultDiscussionService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
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
    private AccessControlService accessControlService;

    @Mock
    private DiscussionUserService discussionUserService;

    @Mock
    private TopicService topicService;

    private DiscussionService discussionService;


    @BeforeClass
    public static void setUpGlobal() {
        testUser = new User("john.doe", "John Doe");
    }

    @Before
    public void setUp() throws DiscussionUserNotFoundException {
        MockitoAnnotations.initMocks(DiscussionServiceTest.class);

        when(discussionUserService.getCurrentlyLoggedUser()).then(invocationOnMock -> testUser);
        when(discussionUserService.getUserById(anyString())).then(invocationOnMock -> testUser);
        when(accessControlService.canRemoveDiscussion(any(Discussion.class))).then(invocationOnMock -> true);
        when(accessControlService.canAddDiscussion(any(Topic.class))).then(invocationOnMock -> true);
        when(accessControlService.canViewDiscussions(any(Topic.class))).then(invocationOnMock -> true);
        when(accessControlService.canEditDiscussion(any(Discussion.class))).then(invocationOnMock -> true);
        when(accessControlService.canViewPosts(any(Discussion.class))).then(invocationOnMock -> true);


        discussionService = new DefaultDiscussionService(discussionDao, topicService, accessControlService, discussionUserService);
    }

    @Test
    public void testCreateDiscussion() throws AccessDeniedException, DiscussionUserNotFoundException {
        Discussion discussion = new Discussion(55L,"Some discussion");
        when(discussionDao.save(any(Discussion.class))).then(invocationOnMock -> (Discussion)invocationOnMock.getArguments()[0]);
        discussion = discussionService.createDiscussion(new Topic(), discussion);

        assertNotNull("Discussion is null!", discussion);
        assertNotNull("Id is null!", discussion.getId());
        assertNotNull("Discussion has null topic!", discussion.getTopic());
    }

    @Test
    public void testGetDiscussionsByTopic() throws AccessDeniedException {
        final Discussion discussion1 = new Discussion(-10L, "discussion1");
        final Discussion discussion2 = new Discussion(-11L, "discussion2");

        when(discussionDao.getDiscussionsByTopic(any(Topic.class))).then(invocationOnMock -> Arrays.asList(new Discussion[] {discussion1, discussion2}));

        List<Discussion> discussions = discussionService.getDiscussionsByTopic(new Topic());
        assertNotNull("Null list returned!", discussions);
        assertEquals("Wrong number of discussions returned!", 2, discussions.size());
        assertTrue("Discussion 1 not included!", discussions.contains(discussion1));
        assertTrue("Discussion 2 not included!", discussions.contains(discussion2));
    }

    @Test
    public void testGetDiscussionById() throws AccessDeniedException {
        final Discussion discussion1 = new Discussion(-10L, "discussion1");

        when(discussionDao.getById(-10L)).then(invocationOnMock -> discussion1);

        assertEquals("Wrong discussion returned!", discussion1, discussionService.getDiscussionById(-10L));
        assertNull("Discussion with id -12345 shouldn't exist!", discussionService.getDiscussionById(-12345L));
    }

    @Test
    public void testRemoveDiscussion() throws AccessDeniedException {
        final List<Discussion> discussionRepository = new ArrayList<>();

        // mock get, save and remove methods
        when(discussionDao.getById(any(Long.class))).then(invocationOnMock -> {
            Long id = (Long) invocationOnMock.getArguments()[0];
            for(Discussion d : discussionRepository) {
                if(d.getId().equals(id)) {
                    return d;
                }
            }

            return null;
        });
        when(discussionDao.save(any(Discussion.class))).then(invocationOnMock -> {
            Discussion discussion = (Discussion) invocationOnMock.getArguments()[0];
            discussionRepository.add(discussion);
            return discussion;
        });
        doAnswer(invocationOnMock -> {
            Discussion toBeRemoved = (Discussion) invocationOnMock.getArguments()[0];
            for(Discussion d : discussionRepository) {
                if(d.getId().equals(toBeRemoved.getId())) {
                    discussionRepository.remove(d);
                    break;
                }
            }

            return invocationOnMock;
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
}
