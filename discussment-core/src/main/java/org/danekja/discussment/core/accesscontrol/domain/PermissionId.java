package org.danekja.discussment.core.accesscontrol.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Composite primary key for @{@link AbstractPermission} derivates.
 *
 * @author Jakub Danek
 */
@Embeddable
public class PermissionId implements Serializable {

    private String userId;
    private PermissionLevel level;
    private long itemId;
    private PermissionType permissionType;

    PermissionId(String userId, PermissionType permissionType) {
        this.userId = userId;
        this.level = PermissionLevel.GLOBAL;
        this.itemId = 0;
        this.permissionType = permissionType;
    }

    PermissionId(String userId, PermissionLevel level, long itemId, PermissionType permissionType) {
        this.userId = userId;
        this.level = level;
        this.itemId = itemId;
        this.permissionType = permissionType;
    }

    protected PermissionId() {
    }

    @Enumerated(value = EnumType.STRING)
    @AttributeOverride(name = "permissionType", column = @Column(name = "permission_type", nullable = false, updatable = false))
    public PermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    /**
     * Item to which this permission applies. Actual entity depends on the value of
     * {#link itemId}.
     */
    @Column(name = "item_id", nullable = true, updatable = false)
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * PK of the user which has this permission.
     */
    @Column(name = "user_id", nullable = false, updatable = false)
    public String getUserId() {
        return userId;
    }

    protected void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Level for which the permission applies (single discussion, topic, category or global)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    public PermissionLevel getLevel() {
        return level;
    }

    protected void setLevel(PermissionLevel level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionId that = (PermissionId) o;

        if (itemId != that.itemId) return false;
        if (!userId.equals(that.userId)) return false;
        return level == that.level;
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + level.hashCode();
        result = 31 * result + (int) (itemId ^ (itemId >>> 32));
        return result;
    }
}
