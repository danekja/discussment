package org.danekja.discussment.core.accesscontrol.service;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.PermissionData;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;

import java.io.Serializable;

/**
 * Interface for permission management - define which actions are
 * users permitted to do and which not.
 */
public interface PermissionManagementService extends Serializable {

    /**
     * Configures posting permissions for the user within the given discussion.
     *
     * @param discussion  discussion for which permissions are configured
     * @param user        user for which permissions are set
     * @param permissions the actual permission values
     */
    void configurePostPermissions(IDiscussionUser user, Discussion discussion, PermissionData permissions);

    /**
     * Configures posting permissions for the user within the given topic.
     *
     * @param topic       topic for which permissions are configured
     * @param user        user for which permissions are set
     * @param permissions the actual permission values
     */
    void configurePostPermissions(IDiscussionUser user, Topic topic, PermissionData permissions);

    /**
     * Configures posting permissions for the user within the given category.
     *
     * @param category    category for which permissions are configured
     * @param user        user for which permissions are set
     * @param permissions the actual permission values
     */
    void configurePostPermissions(IDiscussionUser user, Category category, PermissionData permissions);

    /**
     * Configures global posting permissions for the user.
     *
     * @param user        user for which permissions are set
     * @param permissions the actual permission values
     */
    void configurePostPermissions(IDiscussionUser user, PermissionData permissions);

    /**
     * Configures discussion creation and management permissions for the user within the given topic.
     *
     * @param topic       topic for which permissions are configured
     * @param user        user for which permissions are set
     * @param permissions the actual permission values
     */
    void configureDiscussionPermissions(IDiscussionUser user, Topic topic, PermissionData permissions);

    /**
     * Configures discussion creation and management permissions for the user within the given category.
     *
     * @param category    category for which permissions are configured
     * @param user        user for which permissions are set
     * @param permissions the actual permission values
     */
    void configureDiscussionPermissions(IDiscussionUser user, Category category, PermissionData permissions);

    /**
     * Configures global discussion creation and management permissions for the user.
     *
     * @param user        user for which permissions are set
     * @param permissions the actual permission values
     */
    void configureDiscussionPermissions(IDiscussionUser user, PermissionData permissions);

    /**
     * Configures topic creation and management permissions for the user within the given category.
     *
     * @param category    category for which permissions are configured
     * @param user        user for which permissions are set
     * @param permissions the actual permission values
     */
    void configureTopicPermissions(IDiscussionUser user, Category category, PermissionData permissions);

    /**
     * Configures global topic creation and management permissions for the user.
     *
     * @param user        user for which permissions are set
     * @param permissions the actual permission values
     */
    void configureTopicPermissions(IDiscussionUser user, PermissionData permissions);

    /**
     * Configures global category creation and management permissions for the user.
     *
     * @param user        user for which permissions are set
     * @param permissions the actual permission values
     */
    void configureCategoryPermissions(IDiscussionUser user, PermissionData permissions);
}
