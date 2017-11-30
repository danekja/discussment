package org.danekja.discussment.core.accesscontrol.domain;

import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

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
@NamedQueries({
        @NamedQuery(name = PostPermission.QUERY_BY_USER,
                query = "SELECT pp FROM PostPermission pp" +
                        " WHERE pp.id.userId = :" + PostPermission.PARAM_USER_ID +
                        " AND ((pp.id.level = org.danekja.discussment.core.accesscontrol.domain.PermissionLevel.DISCUSSION" +
                        "       AND pp.id.itemId = :" + PostPermission.PARAM_DISCUSSION_ID + ")" +
                        " OR (pp.id.level = org.danekja.discussment.core.accesscontrol.domain.PermissionLevel.TOPIC" +
                        "       AND pp.id.itemId = :" + PostPermission.PARAM_TOPIC_ID + ")" +
                        " OR (pp.id.level = org.danekja.discussment.core.accesscontrol.domain.PermissionLevel.CATEGORY" +
                        "       AND pp.id.itemId = :" + PostPermission.PARAM_CATEGORY_ID + ")" +
                        " OR pp.id.level = org.danekja.discussment.core.accesscontrol.domain.PermissionLevel.GLOBAL)")
})
@Entity
@DiscriminatorValue("POST")
public class PostPermission extends AbstractPermission {

    public static final String QUERY_BY_USER = "PostPermission.findForUser";

    public PostPermission(String userId, PermissionData data) {
        super(userId, data, PermissionType.POST);
    }

    public PostPermission(String userId, Discussion discussion, PermissionData data) {
        super(userId, PermissionLevel.DISCUSSION, discussion.getId(), data, PermissionType.POST);
    }

    public PostPermission(String userId, Topic topic, PermissionData data) {
        super(userId, PermissionLevel.TOPIC, topic.getId(), data, PermissionType.POST);
    }

    public PostPermission(String userId, Category category, PermissionData data) {
        super(userId, PermissionLevel.CATEGORY, category.getId(), data, PermissionType.POST);
    }

    protected PostPermission() {
    }
}
