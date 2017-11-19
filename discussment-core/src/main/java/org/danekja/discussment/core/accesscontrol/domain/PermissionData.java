package org.danekja.discussment.core.accesscontrol.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Permission data define access rights for the user and item(s) they belong to.
 * <p>
 * For the actual posts, the behaviour is clear - user either can or cannot add/read/edit/delete posts.
 * <p>
 * For the remaining entities (topics, categories, discussions), the permissions don't influence posts themselves,
 * only the ability to add/remove groups and to manage their metadata (such as name, permissions, etc).
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
     * User is allowed to create new items.
     */
    @Column(name = "create", nullable = false)
    public boolean isCreate() {
        return create;
    }

    protected void setCreate(boolean create) {
        this.create = create;
    }

    /**
     * User is allowed to remove items of other users
     */
    @Column(name = "delete", nullable = false)
    public boolean isDelete() {
        return delete;
    }

    protected void setDelete(boolean delete) {
        this.delete = delete;
    }

    /**
     * User is allowed to edit items of other users.
     */
    @Column(name = "edit", nullable = false)
    public boolean isEdit() {
        return edit;
    }

    protected void setEdit(boolean edit) {
        this.edit = edit;
    }

    /**
     * User is allowed to read respective items.
     */
    @Column(name = "view", nullable = false)
    public boolean isView() {
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
}
