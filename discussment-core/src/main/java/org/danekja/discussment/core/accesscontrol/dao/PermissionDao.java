package org.danekja.discussment.core.accesscontrol.dao;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.Permission;
import org.danekja.discussment.core.dao.GenericDao;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface extends GenericDao on methods for working with permission in a database
 */
public interface PermissionDao extends GenericDao<Long, Permission> {

    Permission getUsersPermissions(IDiscussionUser user);
}
