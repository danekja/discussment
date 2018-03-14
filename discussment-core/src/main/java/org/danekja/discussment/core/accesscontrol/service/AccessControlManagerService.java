package org.danekja.discussment.core.accesscontrol.service;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface for checking user's permissions and viewing them
 */
public interface AccessControlManagerService {

    /*
    ################### POST ACCESS ####################
     */

    @Transactional
    boolean canAddPost(IDiscussionUser user, Discussion discussion);

    @Transactional
    boolean canViewPost(IDiscussionUser user, Post post);
    @Transactional
    boolean canViewPosts(IDiscussionUser user, Discussion discussion);

    @Transactional
    boolean canEditPost(IDiscussionUser user, Post post);
    @Transactional
    boolean canEditPosts(IDiscussionUser user, Discussion discussion);

    @Transactional
    boolean canRemovePost(IDiscussionUser user, Post post);
    @Transactional
    boolean canRemovePosts(IDiscussionUser user, Discussion discussion);

    /*
    ################### DISCUSSION ACCESS ################
     */

    @Transactional
    boolean canAddDiscussion(IDiscussionUser user, Topic topic);

    @Transactional
    boolean canEditDiscussion(IDiscussionUser user, Discussion discussion);

    @Transactional
    boolean canRemoveDiscussion(IDiscussionUser user, Discussion discussion);

    @Transactional
    boolean canViewDiscussions(IDiscussionUser user, Topic topic);

    /*
    #################### TOPIC ACCESS ###################
     */

    @Transactional
    boolean canAddTopic(IDiscussionUser user, Category category);

    @Transactional
    boolean canEditTopic(IDiscussionUser user, Topic topic);

    @Transactional
    boolean canRemoveTopic(IDiscussionUser user, Topic topic);

    @Transactional
    boolean canViewTopics(IDiscussionUser user, Category category);

    /*
    #################### TOPIC ACCESS ###################
     */

    @Transactional
    boolean canAddCategory(IDiscussionUser user);

    @Transactional
    boolean canEditCategory(IDiscussionUser user, Category category);

    @Transactional
    boolean canRemoveCategory(IDiscussionUser user, Category category);

    @Transactional
    boolean canViewCategories(IDiscussionUser user);
}
