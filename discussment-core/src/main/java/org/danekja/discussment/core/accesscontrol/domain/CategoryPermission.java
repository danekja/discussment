package org.danekja.discussment.core.accesscontrol.domain;

import javax.persistence.Entity;

/**
 * Permission class for access management related to categories.
 * <p>
 * Configures whether user can view or add categories and/or remove or edit categories of other users.
 * <p>
 * The edit permission doesn't influence whether user can edit or remove his own categories' metadata. He can always do that
 * unless forum configuration prohibits it (if such configuration option has been implemented).
 * <p>
 * CATEGORY, TOPIC and DISCUSSION permission levels make no sense here.
 *
 * @author Jakub Danek
 */
@Entity
public class CategoryPermission extends AbstractPermission {

    public CategoryPermission(String userId, PermissionData data) {
        super(userId, data);
    }

    public CategoryPermission(String userId, PermissionLevel type, Long itemId, PermissionData data) {
        super(userId, type, itemId, data);

        switch (type) {
            case CATEGORY:
            case TOPIC:
            case DISCUSSION:
                throw new IllegalArgumentException("CATEGORY, TOPIC and DISCUSSION permission levels make no sense here!");
            default:
                //pass
                break;

        }
    }

    protected CategoryPermission() {
    }
}
