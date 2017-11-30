package org.danekja.discussment.core.accesscontrol.domain;

import org.danekja.discussment.core.domain.Category;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Permission class for access management related to topics.
 * <p>
 * Configures whether user can view/add topics and/or remove or edit metadata of topics of other users
 * in category/everywhere.
 * <p>
 * The edit permission doesn't influence whether user can remove or edit his own topics' metadata. He can always do that
 * unless forum configuration prohibits it (if such configuration option has been implemented).
 * <p>
 * DISCUSSION and TOPIC permission levels are not allowed for this permission.
 *
 * @author Jakub Danek
 */
@Entity
@DiscriminatorValue("TOPIC")
public class TopicPermission extends AbstractPermission {

    public TopicPermission(String userId, PermissionData data) {
        super(userId, data, PermissionType.TOPIC);
    }

    public TopicPermission(String userId, Category category, PermissionData data) {
        super(userId, PermissionLevel.CATEGORY, category.getId(), data, PermissionType.TOPIC);
    }

    protected TopicPermission() {
    }
}
