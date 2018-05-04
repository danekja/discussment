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
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class TopicServiceTest {

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

    @BeforeClass
    public static void setUpGlobal() {
        testUser = new User(-100L, "PMS Test User");
    }

    @Before
    public void setUp() throws DiscussionUserNotFoundException {
        MockitoAnnotations.initMocks(TopicServiceTest.class);

        when(accessControlService.canAddTopic(any(Category.class))).then(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                return true;
            }
        });
        when(accessControlService.canViewTopics(any(Category.class))).then(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                return true;
            }
        });
        when(accessControlService.canRemoveTopic(any(Topic.class))).then(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                return true;
            }
        });
        when(discussionUserService.getUserById(any(String.class))).then(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                return testUser;
            }
        });
        when(discussionUserService.getCurrentlyLoggedUser()).then(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                return testUser;
            }
        });

        topicService = new DefaultTopicService(topicDao, categoryService, accessControlService, discussionUserService);
    }

    @Test
    public void testCreateTopic() throws AccessDeniedException {
        when(topicDao.save(any(Topic.class))).then(new Answer<Topic>() {
            @Override
            public Topic answer(InvocationOnMock invocationOnMock) throws Throwable {
                return (Topic) invocationOnMock.getArguments()[0];
            }
        });

        Topic t = new Topic(98L, "Test topic", "Description");
        t = topicService.createTopic(new Category(), t);

        assertNotNull("Null topic returned!", t);
        assertNotNull("Topic has null category!", t.getCategory());
    }

    @Test
    public void testGetTopicById() throws AccessDeniedException {
        when(topicDao.getById(55L)).then(new Answer<Topic>() {
            @Override
            public Topic answer(InvocationOnMock invocationOnMock) throws Throwable {
                return new Topic(55L, "Mock topic", "Mock description");
            }
        });
        when(topicDao.getById(not(eq(55L)))).then(new Answer<Topic>() {
            @Override
            public Topic answer(InvocationOnMock invocationOnMock) throws Throwable {
                return null;
            }
        });

        assertNotNull("Null returned on existing topic!", topicService.getTopicById(55L));
        assertNull("Topic shouldn't exist!", topicService.getTopicById(-5798L));
    }

    @Test
    public void testGetTopicsByCategory() throws AccessDeniedException {
        final Category c1 = new Category(45L, "test cat 1");
        final Category c2 = new Category(87L, "no topics cat");
        when(topicDao.getTopicsByCategory(c1)).then(new Answer<List<Topic>>() {
            @Override
            public List<Topic> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return Arrays.asList(new Topic(87L, "test topic", "desc"));
            }
        });
        when(topicDao.getTopicsByCategory(c2)).then(new Answer<List<Topic>>() {
            @Override
            public List<Topic> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return Collections.emptyList();
            }
        });

        assertEquals("Wrong number of topics returned for c1!", 1, topicService.getTopicsByCategory(c1).size());
        assertEquals("Wrong number of topics returned for c2!", 0, topicService.getTopicsByCategory(c2).size());
    }

    @Test
    public void testRemoveTopic() throws AccessDeniedException {
        final List<Topic> topicRepository = new ArrayList<>();

        // mock get, save and remove methods
        when(topicDao.getById(any(Long.class))).then(new Answer<Topic>() {
            @Override
            public Topic answer(InvocationOnMock invocationOnMock) throws Throwable {
                Long id = (Long) invocationOnMock.getArguments()[0];
                for(Topic t : topicRepository) {
                    if(t.getId().equals(id)) {
                        return t;
                    }
                }

                return null;
            }
        });
        when(topicDao.save(any(Topic.class))).then(new Answer<Topic>() {
            @Override
            public Topic answer(InvocationOnMock invocationOnMock) throws Throwable {
                Topic topic = (Topic) invocationOnMock.getArguments()[0];
                topicRepository.add(topic);
                return topic;
            }
        });
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Topic toBeRemoved = (Topic) invocationOnMock.getArguments()[0];
                for(Topic t : topicRepository) {
                    if(t.getId().equals(toBeRemoved.getId())) {
                        topicRepository.remove(t);
                        break;
                    }
                }

                return invocationOnMock;
            }
        }).when(topicDao).remove(any(Topic.class));

        // create topic to be removed
        Topic topic = new Topic(-87L, "Topic to be removed", "This will be removed too");
        topicService.createTopic(new Category(), topic);

        // test discussion removing
        Topic toBeRemoved = topicService.getTopicById(topic.getId());
        assertNotNull("Topic to be removed not found!", toBeRemoved);
        topicService.removeTopic(toBeRemoved);
        assertNull("Topic not removed!", topicService.getTopicById(toBeRemoved.getId()));
    }

}
