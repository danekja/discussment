package org.danekja.discussment.article.core.service;

import org.danekja.discussment.article.core.domain.User;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;

public interface DashboardService {

    boolean canViewPost(User user, Discussion discussion);
    boolean canAddPost(User user, Discussion discussion);
    boolean canRemovePost(User user, Discussion discussion);
    boolean canEditPost(User user, Discussion discussion);
    boolean canViewPosts(User user, Discussion discussion);

    boolean canAddDiscussion(User user, Topic topic);
    boolean canEditDiscussion(User user, Discussion discussion);
    boolean canRemoveDiscussion(User user, Discussion discussion);
    boolean canViewDiscussions(User user, Topic topic);

    boolean canAddTopic(User user, Category category);
    boolean canEditTopic(User user, Topic topic);
    boolean canRemoveTopic(User user, Topic topic);
    boolean canViewTopics(User user, Category category);

    boolean canAddCategory(User user);
    boolean canEditCategory(User user, Category category);
    boolean canRemoveCategory(User user, Category category);
    boolean canViewCategories(User user);
}
