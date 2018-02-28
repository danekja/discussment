package org.danekja.discussment.core.accesscontrol.service;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;

/**
 * Interface for checking user's permissions and viewing them
 */
public interface AccessControlManagerService {

    /*
    ################### POST ACCESS ####################
     */

    boolean canAddPost(IDiscussionUser user, Discussion discussion);

    boolean canViewPost(IDiscussionUser user, Post post);
    boolean canViewPosts(IDiscussionUser user, Discussion discussion);

    boolean canEditPost(IDiscussionUser user, Post post);
    boolean canEditPosts(IDiscussionUser user, Discussion discussion);

    boolean canRemovePost(IDiscussionUser user, Post post);
    boolean canRemovePosts(IDiscussionUser user, Discussion discussion);

    /*
    ################### DISCUSSION ACCESS ################
     */

    boolean canAddDiscussion(IDiscussionUser user, Topic topic);

    boolean canEditDiscussion(IDiscussionUser user, Discussion discussion);

    boolean canRemoveDiscussion(IDiscussionUser user, Discussion discussion);

    boolean canViewDiscussions(IDiscussionUser user, Topic topic);

    /*
    #################### TOPIC ACCESS ###################
     */

    boolean canAddTopic(IDiscussionUser user, Category category);

    boolean canEditTopic(IDiscussionUser user, Topic topic);

    boolean canRemoveTopic(IDiscussionUser user, Topic topic);

    boolean canViewTopics(IDiscussionUser user, Category category);

    /*
    #################### TOPIC ACCESS ###################
     */

    boolean canAddCategory(IDiscussionUser user);

    boolean canEditCategory(IDiscussionUser user, Category category);

    boolean canRemoveCategory(IDiscussionUser user, Category category);

    boolean canViewCategories(IDiscussionUser user);
}
