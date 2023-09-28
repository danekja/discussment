package org.danekja.discussment.core.service;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.exception.DiscussionUserNotFoundException;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.TopicDao;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.mock.User;
import org.danekja.discussment.core.service.imp.DefaultTopicService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

    private static User testUser;

    @Mock
    private TopicDao topicDao;

    @Mock
    private DiscussionUserService discussionUserService;

    @Mock
    private AccessControlService accessControlService;

    @Mock
    private CategoryService categoryService;

    private TopicService topicService;

    @BeforeAll
    static void setUpGlobal() {
        testUser = new User("john.doe", "John Doe");
    }

    @BeforeEach
    void setUp() throws DiscussionUserNotFoundException {
        lenient().when(accessControlService.canAddTopic(any(Category.class))).thenReturn(true);
        lenient().when(accessControlService.canViewTopics(nullable(Category.class))).thenReturn(true);
        lenient().when(accessControlService.canRemoveTopic(any(Topic.class))).thenReturn(true);
        lenient().when(discussionUserService.getUserById(any(String.class))).thenReturn(testUser);
        lenient().when(discussionUserService.getCurrentlyLoggedUser()).thenReturn(testUser);

        topicService = new DefaultTopicService(topicDao, categoryService, accessControlService, discussionUserService);
    }

    @Test
    void createTopic() throws AccessDeniedException {
        when(topicDao.save(any(Topic.class))).then(invocationOnMock -> invocationOnMock.getArgument(0));

        Topic t = new Topic(98L, "Test topic", "Description");
        t = topicService.createTopic(new Category(), t);

        assertNotNull(t, "Null topic returned!");
        assertNotNull(t.getCategory(), "Topic has null category!");
    }

    @Test
    void getTopicById() throws AccessDeniedException {
        when(topicDao.getById(55L)).then(invocationOnMock -> new Topic(55L, "Mock topic", "Mock description"));
        when(topicDao.getById(not(eq(55L)))).then(invocationOnMock -> null);

        assertNotNull(topicService.getTopicById(55L), "Null returned on existing topic!");
        assertNull(topicService.getTopicById(-5798L), "Topic shouldn't exist!");
    }

    @Test
    void getTopicsByCategory() throws AccessDeniedException {
        final Category c1 = new Category(45L, "test cat 1");
        final Category c2 = new Category(87L, "no topics cat");
        when(topicDao.getTopicsByCategory(c1)).then(invocationOnMock -> Arrays.asList(new Topic(87L, "test topic", "desc")));
        when(topicDao.getTopicsByCategory(c2)).then(invocationOnMock -> new ArrayList<>());

        assertEquals(1, topicService.getTopicsByCategory(c1).size(), "Wrong number of topics returned for c1!");
        assertEquals(0, topicService.getTopicsByCategory(c2).size(), "Wrong number of topics returned for c2!");
    }

    @Test
    void removeTopic() throws AccessDeniedException {
        final List<Topic> topicRepository = new ArrayList<>();

        // mock get, save and remove methods
        when(topicDao.getById(any(Long.class))).then(invocationOnMock -> {
            Long id = (Long) invocationOnMock.getArgument(0);
            for(Topic t : topicRepository) {
                if(t.getId().equals(id)) {
                    return t;
                }
            }

            return null;
        });
        when(topicDao.save(any(Topic.class))).then(invocationOnMock -> {
            Topic topic = (Topic) invocationOnMock.getArgument(0);
            topicRepository.add(topic);
            return topic;
        });
        doAnswer(invocationOnMock -> {
            Topic toBeRemoved = (Topic) invocationOnMock.getArgument(0);
            for(Topic t : topicRepository) {
                if(t.getId().equals(toBeRemoved.getId())) {
                    topicRepository.remove(t);
                    break;
                }
            }

            return invocationOnMock;
        }).when(topicDao).remove(any(Topic.class));

        // create topic to be removed
        Topic topic = new Topic(-87L, "Topic to be removed", "This will be removed too");
        topicService.createTopic(new Category(), topic);

        // test discussion removing
        Topic toBeRemoved = topicService.getTopicById(topic.getId());
        assertNotNull(toBeRemoved, "Topic to be removed not found!");
        topicService.removeTopic(toBeRemoved);
        assertNull(topicService.getTopicById(toBeRemoved.getId()), "Topic not removed!");
    }

}
