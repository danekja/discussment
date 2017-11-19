package org.danekja.discussment.core.accesscontrol.service.impl;

import org.danekja.discussment.core.accesscontrol.dao.NewPermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.*;
import org.danekja.discussment.core.accesscontrol.service.PermissionManagementService;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;

public class PermissionService implements PermissionManagementService {

    private NewPermissionDao permissionDao;

    public PermissionService(NewPermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    public void configurePostPermissions(IDiscussionUser user, Discussion discussion, PermissionData permissions) {
        permissionDao.save(new PostPermission(user.getUsername(), discussion, permissions));
    }

    public void configurePostPermissions(IDiscussionUser user, Topic topic, PermissionData permissions) {
        permissionDao.save(new PostPermission(user.getUsername(), topic, permissions));
    }

    public void configurePostPermissions(IDiscussionUser user, Category category, PermissionData permissions) {
        permissionDao.save(new PostPermission(user.getUsername(), category, permissions));
    }

    public void configurePostPermissions(IDiscussionUser user, PermissionData permissions) {
        permissionDao.save(new PostPermission(user.getUsername(), permissions));
    }

    public void configureDiscussionPermissions(IDiscussionUser user, Topic topic, PermissionData permissions) {
        permissionDao.save(new DiscussionPermission(user.getUsername(), topic, permissions));
    }

    public void configureDiscussionPermissions(IDiscussionUser user, Category category, PermissionData permissions) {
        permissionDao.save(new DiscussionPermission(user.getUsername(), category, permissions));
    }

    public void configureDiscussionPermissions(IDiscussionUser user, PermissionData permissions) {
        permissionDao.save(new DiscussionPermission(user.getUsername(), permissions));
    }

    public void configureTopicPermissions(IDiscussionUser user, Category category, PermissionData permissions) {
        permissionDao.save(new TopicPermission(user.getUsername(), category, permissions));
    }

    public void configureTopicPermissions(IDiscussionUser user, PermissionData permissions) {
        permissionDao.save(new TopicPermission(user.getUsername(), permissions));
    }

    public void configureCategoryPermissions(IDiscussionUser user, PermissionData permissions) {
        permissionDao.save(new CategoryPermission(user.getUsername(), permissions));
    }
}
