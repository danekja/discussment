package org.danekja.discussment.core.accesscontrol.service;

import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

public interface AccessControlService extends Serializable {

    /*
    ################### POST ACCESS ####################
     */
    @Transactional
    boolean canViewPost(Post post);

    @Transactional
    boolean canAddPost(Discussion discussion);

    @Transactional
    boolean canEditPost(Post post);

    @Transactional
    boolean canRemovePost(Post post);

    @Transactional
    boolean canViewPosts(Discussion discussion);

    /*
    ################### DISCUSSION ACCESS ################
     */

    @Transactional
    boolean canAddDiscussion(Topic topic);

    @Transactional
    boolean canEditDiscussion(Discussion discussion);

    @Transactional
    boolean canRemoveDiscussion(Discussion discussion);

    @Transactional
    boolean canViewDiscussions(Topic discussion);

    /*
    #################### TOPIC ACCESS ###################
     */

    @Transactional
    boolean canAddTopic(Category category);

    @Transactional
    boolean canEditTopic(Topic topic);

    @Transactional
    boolean canRemoveTopic(Topic topic);

    @Transactional
    boolean canViewTopics(Category category);

    /*
    #################### CATEGORY ACCESS ####################
     */

    @Transactional
    boolean canAddCategory();

    @Transactional
    boolean canEditCategory(Category category);

    @Transactional
    boolean canRemoveCategory(Category category);

    @Transactional
    boolean canViewCategories();
}
