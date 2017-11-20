package org.danekja.discussment.core.accesscontrol.service;

import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;

import java.io.Serializable;

public interface AccessControlService extends Serializable {

    /*
    ################### POST ACCESS ####################
     */

    boolean canAddPost(Discussion discussion);

    boolean canEditPost(Post post);

    boolean canRemovePost(Post post);

    boolean canViewPosts(Discussion discussion);

    /*
    ################### DISCUSSION ACCESS ################
     */

    boolean canAddDiscussion(Topic topic);

    boolean canEditDiscussion(Discussion discussion);

    boolean canRemoveDiscussion(Discussion discussion);

    boolean canViewDiscussions(Topic discussion);

    /*
    #################### TOPIC ACCESS ###################
     */

    boolean canAddTopic(Category category);

    boolean canEditTopic(Topic topic);

    boolean canRemoveTopic(Topic topic);

    boolean canViewTopics(Category category);

    /*
    #################### CATEGORY ACCESS ####################
     */

    boolean canAddCategory();

    boolean canEditCategory(Category category);

    boolean canRemoveCategory(Category category);

    boolean canViewCategories();
}
