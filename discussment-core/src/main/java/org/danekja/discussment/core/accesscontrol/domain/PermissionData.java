package org.danekja.discussment.core.accesscontrol.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Permission data define access rights for the user and item(s) they belong to.
 * <p>
 * For the actual posts, the behaviour is clear - user either can or cannot add/read/edit/delete posts.
 * <p>
 * For the remaining entities (topics, categories, discussions), the permissions don't influence posts themselves,
 * only the ability to add/remove groups and to manage their metadata (such as name, permissions, etc).
 *
 * PremissionData object is immutable and permission may be set only via constructor. Setters and getters are protected
 * for this reason.
 *
 * @author Jakub Danek
 */
@Embeddable
public class PermissionData implements Serializable {

    /**
     * User is allowed to create new items.
     */
    private boolean create;
    /**
     * User is allowed to remove items of other users
     */
    private boolean delete;
    /**
     * User is allowed to edit items of other users.
     */
    private boolean edit;
    /**
     * User is allowed to read respective items.
     */
    private boolean view;

    /**
     * Creates new permission data object with each permission set to false.
     */
    public PermissionData() {
        this(false, false, false, false);
    }

    public PermissionData(boolean create, boolean delete, boolean edit, boolean view) {
        this.create = create;
        this.delete = delete;
        this.edit = edit;
        this.view = view;
    }

    @Transient
    public boolean canDo(Action action) {
        switch (action) {
            case EDIT:
                return isEdit();
            case VIEW:
                return isView();
            case DELETE:
                return isDelete();
            case CREATE:
                return isCreate();
            default:
                throw new UnsupportedOperationException("Action not implemented yet!");
        }
    }

    /**
     * User is allowed to create new items.
     */
    @Column(name = "can_create", nullable = false)
    protected boolean isCreate() {
        return create;
    }

    protected void setCreate(boolean create) {
        this.create = create;
    }

    /**
     * User is allowed to remove items of other users
     */
    @Column(name = "can_delete", nullable = false)
    protected boolean isDelete() {
        return delete;
    }

    protected void setDelete(boolean delete) {
        this.delete = delete;
    }

    /**
     * User is allowed to edit items of other users.
     */
    @Column(name = "can_edit", nullable = false)
    protected boolean isEdit() {
        return edit;
    }

    protected void setEdit(boolean edit) {
        this.edit = edit;
    }

    /**
     * User is allowed to read respective items.
     */
    @Column(name = "can_view", nullable = false)
    protected boolean isView() {
        return view;
    }

    protected void setView(boolean view) {
        this.view = view;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionData that = (PermissionData) o;

        if (create != that.create) return false;
        if (delete != that.delete) return false;
        if (edit != that.edit) return false;
        return view == that.view;
    }

    @Override
    public int hashCode() {
        int result = (create ? 1 : 0);
        result = 31 * result + (delete ? 1 : 0);
        result = 31 * result + (edit ? 1 : 0);
        result = 31 * result + (view ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PermissionData{" +
                "create=" + create +
                ", delete=" + delete +
                ", edit=" + edit +
                ", view=" + view +
                '}';
    }
}
