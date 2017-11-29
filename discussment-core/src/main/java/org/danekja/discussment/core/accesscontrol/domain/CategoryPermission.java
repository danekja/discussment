package org.danekja.discussment.core.accesscontrol.domain;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

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
@NamedQueries({
        @NamedQuery(name = CategoryPermission.QUERY_BY_USER,
                query = "SELECT cp FROM CategoryPermission cp" +
                        " WHERE cp.id.userId = :" + CategoryPermission.PARAM_USER_ID +
                        " AND cp.id.level = org.danekja.discussment.core.accesscontrol.domain.PermissionLevel.GLOBAL")
})
@Entity
public class CategoryPermission extends AbstractPermission {

    public static final String QUERY_BY_USER = "CategoryPermission.findForUser";

    public CategoryPermission(String userId, PermissionData data) {
        super(userId, data);
    }

    protected CategoryPermission() {
    }
}
