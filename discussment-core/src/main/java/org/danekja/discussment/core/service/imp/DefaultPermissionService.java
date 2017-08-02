package org.danekja.discussment.core.service.imp;

import org.danekja.discussment.core.dao.PermissionDao;
import org.danekja.discussment.core.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.Permission;
import org.danekja.discussment.core.service.PermissionService;

/**
 * Created by Zdenek Vales on 1.8.2017.
 */
public class DefaultPermissionService implements PermissionService {

    private PermissionDao permissionDao;

    public DefaultPermissionService(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    public Permission getUsersPermissions(IDiscussionUser user) {
        if(user == null) {
            return null;
        }
        return permissionDao.getUsersPermissions(user);
    }

    public Permission addPermissionForUser(Permission permission, IDiscussionUser user) {
        Permission current = getUsersPermissions(user);
        if(current == null) {
            permission.setUserId(user.getId());
            return permissionDao.save(permission);
        } else {
            current.update(permission);
            return permissionDao.save(permission);
        }
    }
}
