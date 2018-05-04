package org.danekja.discussment.core.accesscontrol.domain;

import org.danekja.discussment.core.domain.Category;

import javax.persistence.*;

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
@NamedQueries({
        @NamedQuery(name = TopicPermission.QUERY_BY_USER,
                query = "SELECT tp FROM TopicPermission tp" +
                        " WHERE tp.id.userId = :" + TopicPermission.PARAM_USER_ID +
                        " AND ((tp.id.level = org.danekja.discussment.core.accesscontrol.domain.PermissionLevel.CATEGORY" +
                        "       AND tp.id.itemId = :" + TopicPermission.PARAM_CATEGORY_ID + ")" +
                        " OR tp.id.level = org.danekja.discussment.core.accesscontrol.domain.PermissionLevel.GLOBAL)")
})
@Entity
@DiscriminatorValue("TOPIC")
public class TopicPermission extends AbstractPermission {

    public static final String QUERY_BY_USER = "TopicPermission.findForUser";

    public TopicPermission(String userId, PermissionData data) {
        super(userId, data, PermissionType.TOPIC);
    }

    public TopicPermission(String userId, Category category, PermissionData data) {
        super(userId, PermissionLevel.CATEGORY, category.getId(), data, PermissionType.TOPIC);
    }

    protected TopicPermission() {
    }
}
