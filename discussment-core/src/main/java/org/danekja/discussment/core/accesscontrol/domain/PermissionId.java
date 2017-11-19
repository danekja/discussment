package org.danekja.discussment.core.accesscontrol.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
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
    private Long itemId;

    PermissionId(String userId) {
        this.userId = userId;
        this.level = PermissionLevel.GLOBAL;
    }

    PermissionId(String userId, PermissionLevel level, Long itemId) {
        this.userId = userId;
        this.level = level;
        this.itemId = itemId;
    }

    protected PermissionId() {
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
    @Enumerated
    @Column(name = "type", nullable = false, updatable = false)
    public PermissionLevel getLevel() {
        return level;
    }

    protected void setLevel(PermissionLevel level) {
        this.level = level;
    }

    /**
     * Item to which this permission applies. Actual entity depends on the value of
     * {@link #level}
     */
    @Column(name = "item_id", nullable = true, updatable = false)
    public Long getItemId() {
        return itemId;
    }

    protected void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionId that = (PermissionId) o;

        if (!userId.equals(that.userId)) return false;
        if (level != that.level) return false;
        return itemId != null ? itemId.equals(that.itemId) : that.itemId == null;
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + level.hashCode();
        result = 31 * result + (itemId != null ? itemId.hashCode() : 0);
        return result;
    }
}
