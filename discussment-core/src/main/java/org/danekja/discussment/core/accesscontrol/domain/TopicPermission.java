package org.danekja.discussment.core.accesscontrol.domain;

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
public class TopicPermission extends AbstractPermission {

    public TopicPermission(String userId, PermissionData data) {
        super(userId, data);
    }

    public TopicPermission(String userId, PermissionLevel type, Long itemId, PermissionData data) {
        super(userId, type, itemId, data);

        switch (type) {
            case DISCUSSION:
            case TOPIC:
                throw new IllegalArgumentException("TOPIC and DISCUSSION permission levels make no sense here!");
            default:
                //pass
                break;

        }
    }

    protected TopicPermission() {
    }
}
