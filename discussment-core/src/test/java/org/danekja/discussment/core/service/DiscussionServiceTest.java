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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * This test case is using services with accesscontrol component.
 */
@ExtendWith(MockitoExtension.class)
class DiscussionServiceTest {


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


    @BeforeAll
    static void setUpGlobal() {
        testUser = new User("john.doe", "John Doe");
    }

    @BeforeEach
    void setUp() throws DiscussionUserNotFoundException {
        lenient().when(discussionUserService.getCurrentlyLoggedUser()).thenReturn(testUser);
        lenient().when(discussionUserService.getUserById(anyString())).thenReturn(testUser);
        lenient().when(accessControlService.canRemoveDiscussion(any(Discussion.class))).thenReturn(true);
        lenient().when(accessControlService.canAddDiscussion(any(Topic.class))).thenReturn(true);
        lenient().when(accessControlService.canViewDiscussions(nullable(Topic.class))).thenReturn(true);
        lenient().when(accessControlService.canEditDiscussion(any(Discussion.class))).thenReturn(true);
        lenient().when(accessControlService.canViewPosts(any(Discussion.class))).thenReturn(true);

        discussionService = new DefaultDiscussionService(discussionDao, topicService, accessControlService, discussionUserService);
    }

    @Test
    void createDiscussion() throws AccessDeniedException {
        Discussion discussion = new Discussion(55L,"Some discussion");
        when(discussionDao.save(any(Discussion.class))).then(invocationOnMock -> invocationOnMock.getArgument(0));
        discussion = discussionService.createDiscussion(new Topic(), discussion);

        assertNotNull(discussion, "Discussion is null!");
        assertNotNull(discussion.getId(), "Id is null!");
        assertNotNull(discussion.getTopic(), "Discussion has null topic!");
    }

    @Test
    void getDiscussionsByTopic() throws AccessDeniedException {
        final Discussion discussion1 = new Discussion(-10L, "discussion1");
        final Discussion discussion2 = new Discussion(-11L, "discussion2");

        when(discussionDao.getDiscussionsByTopic(any(Topic.class))).thenReturn(Arrays.asList(discussion1, discussion2));

        List<Discussion> discussions = discussionService.getDiscussionsByTopic(new Topic());
        assertNotNull(discussions, "Null list returned!");
        assertEquals(2, discussions.size(), "Wrong number of discussions returned!");
        assertTrue(discussions.contains(discussion1), "Discussion 1 not included!");
        assertTrue(discussions.contains(discussion2), "Discussion 2 not included!");
    }

    @Test
    void getDiscussionById() throws AccessDeniedException {
        final Discussion discussion1 = new Discussion(-10L, "discussion1");

        when(discussionDao.getById(-10L)).thenReturn(discussion1);

        assertEquals(discussion1, discussionService.getDiscussionById(-10L), "Wrong discussion returned!");
        assertNull(discussionService.getDiscussionById(-12345L), "Discussion with id -12345 shouldn't exist!");
    }

    @Test
    void removeDiscussion() throws AccessDeniedException {
        final List<Discussion> discussionRepository = new ArrayList<>();

        // mock get, save and remove methods
        when(discussionDao.getById(any(Long.class))).then(invocationOnMock -> {
            Long id = (Long) invocationOnMock.getArgument(0);
            for(Discussion d : discussionRepository) {
                if(d.getId().equals(id)) {
                    return d;
                }
            }

            return null;
        });
        when(discussionDao.save(any(Discussion.class))).then(invocationOnMock -> {
            Discussion discussion = (Discussion) invocationOnMock.getArgument(0);
            discussionRepository.add(discussion);
            return discussion;
        });
        doAnswer(invocationOnMock -> {
            Discussion toBeRemoved = (Discussion) invocationOnMock.getArgument(0);
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
        assertNotNull(toBeRemoved, "Discussion to be removed not found!");
        discussionService.removeDiscussion(toBeRemoved);
        assertNull(discussionService.getDiscussionById(toBeRemoved.getId()), "Discussion not removed!");
    }
}
