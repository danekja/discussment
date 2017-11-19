package org.danekja.discussment.core.accesscontrol.domain;

import javax.persistence.Entity;

/**
 * Permission class for access management related to posts.
 * <p>
 * Configures whether user can view or add posts and/or remove or edit posts of other users
 * in discussion/all discussions in a topic/all discussions in all topics in a category/everywhere.
 * <p>
 * The edit permission doesn't influence whether user can remove or edit his own posts. He can always do that
 * unless forum configuration prohibits it (if such configuration option has been implemented).
 *
 * @author Jakub Danek
 */
@Entity
public class PostPermission extends AbstractPermission {

    public PostPermission(String userId, PermissionData data) {
        super(userId, data);
    }

    public PostPermission(String userId, PermissionLevel type, Long itemId, PermissionData data) {
        super(userId, type, itemId, data);
    }

    protected PostPermission() {
    }
}
