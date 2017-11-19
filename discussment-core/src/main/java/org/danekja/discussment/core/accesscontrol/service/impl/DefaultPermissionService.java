package org.danekja.discussment.core.accesscontrol.service.impl;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.Permission;
import org.danekja.discussment.core.accesscontrol.service.PermissionService;
import org.danekja.discussment.core.service.DiscussionUserService;

/**
 * Created by Zdenek Vales on 1.8.2017.
 */
public class DefaultPermissionService implements PermissionService {

    private PermissionDao permissionDao;
    private DiscussionUserService userService;

    public DefaultPermissionService(PermissionDao permissionDao, DiscussionUserService userService) {
        this.permissionDao = permissionDao;
        this.userService = userService;
    }

    public Permission getCurrentlyLoggedUsersPermission() {
        IDiscussionUser user = userService.getCurrentlyLoggedUser();
        return getUsersPermissions(user);
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
