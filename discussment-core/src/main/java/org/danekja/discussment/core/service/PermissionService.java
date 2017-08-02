package org.danekja.discussment.core.service;

import org.danekja.discussment.core.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.Permission;

import java.io.Serializable;

/**
 * Interface containing methods for working with user's permissions.
 *
 * Created by Zdenek Vales on 1.8.2017.
 */
public interface PermissionService extends Serializable {

    /**
     * Get user's permissions.
     * @param user User object. If null, null is returned.
     * @return
     */
    Permission getUsersPermissions(IDiscussionUser user);

    /**
     * Adds a permission for user. If user already has permission, it will be overwritten with provided
     * object.
     * @param permission Permission to be set for user.
     * @param user User.
     * @return Saved permission.
     */
    Permission addPermissionForUser(Permission permission, IDiscussionUser user);
}
