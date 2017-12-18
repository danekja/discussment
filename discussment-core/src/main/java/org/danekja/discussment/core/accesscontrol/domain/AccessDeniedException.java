package org.danekja.discussment.core.accesscontrol.domain;

/**
 * An exception thrown when trying to perform an action over item without necessary permissions.
 */
public class AccessDeniedException extends Exception {

    /**
     * An action which wasn't performed due to lack of permissions.
     */
    public final Action action;

    /**
     * Id of user who tried to perform the action.
     */
    public final String userId;

    /**
     * Id of an item which was a subject of action (the action should have been performed over this item).
     *
     * When trying to create an item, actual itemId will be id of its parent item.
     * E. g. if creating topic in category with id 5, itemId would be 5 (category id) but permissionType would still be
     * TOPIC.
     *
     * When creating category or fetching all categories, itemId can be any number (0 is good) since it's not relevant at all.
     */
    public final long itemId;

    /**
     * Type of failed permission (category, post, ...).
     */
    public final PermissionType permissionType;

    /**
     * Creates a new access denied exception.
     *
     * @param action An action which wasn't performed due to lack of permissions.
     * @param userId Id of user who tried to perform the action. If null, '-' symbol will be displayed instead.
     * @param itemId Id of an item which was a subject of action (the action should have been performed over this item).
     * @param permissionType Type of failed permission (category, post, ...).
     */
    public AccessDeniedException(Action action, String userId, long itemId, PermissionType permissionType) {
        super(String.format("Access permission (%s) check failed to authorize user with id %s to perform action %s over item with id %d.",
                permissionType.name(), userId == null ? "-" : userId, action.name(), itemId));
        this.action = action;
        this.userId = userId;
        this.itemId = itemId;
        this.permissionType = permissionType;
    }
}
