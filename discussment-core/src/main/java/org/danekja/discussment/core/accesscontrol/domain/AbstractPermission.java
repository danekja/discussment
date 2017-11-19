package org.danekja.discussment.core.accesscontrol.domain;

import org.danekja.discussment.core.domain.BaseEntity;

import javax.persistence.*;

/**
 * Base class for all permission types.
 *
 * @author Jakub Danek
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "permission")
public abstract class AbstractPermission extends BaseEntity<PermissionId> {

    private PermissionData data;

    AbstractPermission(String userId, PermissionData data) {
        this.setId(new PermissionId(userId));
        this.data = data;
    }

    AbstractPermission(String userId, PermissionLevel type, Long itemId, PermissionData data) {
        this.setId(new PermissionId(userId, type, itemId));
        this.data = data;
    }

    protected AbstractPermission() {
        this.data = new PermissionData();
    }

    /* ####################### MAPPINGS ######################### */

    /**
     * Permission's primary key.
     */
    @EmbeddedId
    @Override
    public PermissionId getId() {
        return super.getId();
    }


    /**
     * Information on allowed/prohibited actions.
     */
    @Embedded
    public PermissionData getData() {
        return data;
    }

    public void setData(PermissionData data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AbstractPermission that = (AbstractPermission) o;

        return data.equals(that.data);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }
}
