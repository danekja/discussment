package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.Permission;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface extends GenericDao on methods for working with permission in a database
 */
public interface PermissionDao extends GenericDao<Permission> {

    Permission getUsersPermissions(IDiscussionUser user);
}
