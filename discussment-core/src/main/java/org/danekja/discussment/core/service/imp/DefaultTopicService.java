package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.accesscontrol.domain.Action;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.PermissionType;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.dao.TopicDao;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.event.TopicCreatedEvent;
import org.danekja.discussment.core.event.TopicEvent;
import org.danekja.discussment.core.event.TopicRemovedEvent;
import org.danekja.discussment.core.service.CategoryService;
import org.danekja.discussment.core.service.TopicService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class DefaultTopicService implements TopicService, ApplicationEventPublisherAware {

    private final TopicDao topicDao;
    private final CategoryService categoryService;
    private final AccessControlService accessControlService;
    private final DiscussionUserService discussionUserService;
    private ApplicationEventPublisher applicationEventPublisher;

    public DefaultTopicService(TopicDao topicDao, CategoryService categoryService, AccessControlService accessControlService, DiscussionUserService discussionUserService) {
        this.topicDao = topicDao;
        this.categoryService = categoryService;
        this.accessControlService = accessControlService;
        this.discussionUserService = discussionUserService;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Topic createTopic(Category category, Topic topic) throws AccessDeniedException {
        if (accessControlService.canAddTopic(category)) {
            topic.setCategory(category);
            Topic createdTopic = topicDao.save(topic);
            publishEvent(new TopicCreatedEvent(createdTopic));
            return createdTopic;
        } else {
            throw new AccessDeniedException(Action.CREATE, getCurrentUserId(), category.getId(), PermissionType.TOPIC);
        }
    }

    @Override
    public Topic getTopicById(long topicId) throws AccessDeniedException {
        Topic t = topicDao.getById(topicId);
        if(t == null) {
            return null;
        }

        if (accessControlService.canViewTopics(t.getCategory())) {
            return t;
        } else {
            throw new AccessDeniedException(Action.VIEW, getCurrentUserId(), topicId, PermissionType.TOPIC);
        }
    }

    @Override
    public List<Topic> getTopicsByCategory(Category category) throws AccessDeniedException {

        if (accessControlService.canViewTopics(category)) {
            return topicDao.getTopicsByCategory(category);
        } else {
            throw new AccessDeniedException(Action.VIEW, getCurrentUserId(), category.getId(), PermissionType.TOPIC);
        }
    }

    @Override
    public Topic getDefaultTopic() {
        Topic topic = topicDao.getById(Topic.DEFAULT_TOPIC_ID);
        if (topic == null) {
            topic = new Topic(Topic.DEFAULT_TOPIC_ID, "default topic", null);
            topic.setCategory(categoryService.getDefaultCategory());
            topicDao.save(topic);
        }
        return topic;
    }

    @Override
    public void removeTopic(Topic topic) throws AccessDeniedException {
        if (accessControlService.canRemoveTopic(topic)) {
            topicDao.remove(topic);
            publishEvent(new TopicRemovedEvent(topic));
        } else {
            throw new AccessDeniedException(Action.DELETE, getCurrentUserId(), topic.getId(), PermissionType.TOPIC);
        }
    }

    /**
     * Returns the id of the currently logged user. Used when throwing access denied exception.
     * @return Id of the currently logged user or null if no user is logged.
     */
    private String getCurrentUserId() {
        IDiscussionUser user = discussionUserService.getCurrentlyLoggedUser();
        return user == null ? null : user.getDiscussionUserId();
    }

    /**
     * Publishes event if {@link #applicationEventPublisher} is not null.
     * @param event Event to be published.
     */
    private void publishEvent(TopicEvent event) {
        if (applicationEventPublisher != null) {
            applicationEventPublisher.publishEvent(event);
        }
    }
}
