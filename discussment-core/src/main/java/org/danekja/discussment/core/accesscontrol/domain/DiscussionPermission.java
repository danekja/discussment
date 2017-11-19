package org.danekja.discussment.core.accesscontrol.domain;

import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;

import javax.persistence.Entity;

/**
 * Permission class for access management related to discussions.
 * <p>
 * Configures whether user can view or add discussions and/or remove or edit discussion metadata of other users
 * in a topic/all topics in a category/everywhere.
 * <p>
 * The edit permission doesn't influence whether user can remove or edit his own discussions' metadata. He can always do that
 * unless forum configuration prohibits it (if such configuration option has been implemented).
 * <p>
 * DISCUSSION permission level is not allowed for this permission type.
 *
 * @author Jakub Danek
 */
@Entity
public class DiscussionPermission extends AbstractPermission {

    public DiscussionPermission(String userId, PermissionData data) {
        super(userId, data);
    }


    public DiscussionPermission(String userId, Topic topic, PermissionData data) {
        super(userId, PermissionLevel.TOPIC, topic.getId(), data);
    }

    public DiscussionPermission(String userId, Category category, PermissionData data) {
        super(userId, PermissionLevel.CATEGORY, category.getId(), data);
    }

    protected DiscussionPermission() {
    }
}
