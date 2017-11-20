package org.danekja.discussment.core.accesscontrol.service.impl;

import org.danekja.discussment.core.accesscontrol.dao.NewPermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.*;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.accesscontrol.service.PermissionManagementService;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;

import java.util.List;
import java.util.Objects;

public class PermissionService implements PermissionManagementService, AccessControlService {

    private NewPermissionDao permissionDao;
    private DiscussionUserService userService;

    public PermissionService(NewPermissionDao permissionDao, DiscussionUserService userService) {
        this.permissionDao = permissionDao;
        this.userService = userService;
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

    public boolean canAddPost(Discussion discussion) {
        List<PostPermission> permissions = getPostPermissions(userService.getCurrentlyLoggedUser(), discussion);
        return checkPermissions(Action.CREATE, permissions);
    }

    public boolean canEditPost(Post post) {
        IDiscussionUser user = userService.getCurrentlyLoggedUser();

        if (user == null) {
            return false;
        } else if (Objects.equals(user.getId(), post.getUserId())) {
            return true;
        }

        List<PostPermission> permissions = getPostPermissions(user, post.getDiscussion());
        return checkPermissions(Action.EDIT, permissions);
    }

    public boolean canRemovePost(Post post) {
        IDiscussionUser user = userService.getCurrentlyLoggedUser();

        if (user == null) {
            return false;
        } else if (Objects.equals(user.getId(), post.getUserId())) {
            return true;
        }

        List<PostPermission> permissions = getPostPermissions(user, post.getDiscussion());
        return checkPermissions(Action.DELETE, permissions);
    }

    public boolean canViewPosts(Discussion discussion) {
        List<PostPermission> permissions = getPostPermissions(userService.getCurrentlyLoggedUser(), discussion);
        return checkPermissions(Action.VIEW, permissions);
    }

    public boolean canAddDiscussion(Topic topic) {
        List<DiscussionPermission> permissions = getDiscussionPermissions(userService.getCurrentlyLoggedUser(), topic);
        return checkPermissions(Action.VIEW, permissions);
    }

    public boolean canEditDiscussion(Discussion discussion) {
        List<DiscussionPermission> permissions = getDiscussionPermissions(userService.getCurrentlyLoggedUser(), discussion.getTopic());
        return checkPermissions(Action.EDIT, permissions);
    }

    public boolean canRemoveDiscussion(Discussion discussion) {
        List<DiscussionPermission> permissions = getDiscussionPermissions(userService.getCurrentlyLoggedUser(), discussion.getTopic());
        return checkPermissions(Action.DELETE, permissions);
    }

    public boolean canViewDiscussions(Topic topic) {
        List<DiscussionPermission> permissions = getDiscussionPermissions(userService.getCurrentlyLoggedUser(), topic);
        return checkPermissions(Action.VIEW, permissions);
    }

    public boolean canAddTopic(Category category) {
        List<TopicPermission> permissions = getTopicPermissions(userService.getCurrentlyLoggedUser(), category);
        return checkPermissions(Action.CREATE, permissions);
    }

    public boolean canEditTopic(Topic topic) {
        List<TopicPermission> permissions = getTopicPermissions(userService.getCurrentlyLoggedUser(), topic.getCategory());
        return checkPermissions(Action.EDIT, permissions);
    }

    public boolean canRemoveTopic(Topic topic) {
        List<TopicPermission> permissions = getTopicPermissions(userService.getCurrentlyLoggedUser(), topic.getCategory());
        return checkPermissions(Action.DELETE, permissions);
    }

    public boolean canViewTopics(Category category) {
        List<TopicPermission> permissions = getTopicPermissions(userService.getCurrentlyLoggedUser(), category);
        return checkPermissions(Action.VIEW, permissions);
    }

    public boolean canAddCategory() {
        List<CategoryPermission> permissions = getCategoryPermissions(userService.getCurrentlyLoggedUser());
        return checkPermissions(Action.CREATE, permissions);
    }

    public boolean canEditCategory(Category category) {
        List<CategoryPermission> permissions = getCategoryPermissions(userService.getCurrentlyLoggedUser());
        return checkPermissions(Action.EDIT, permissions);
    }

    public boolean canRemoveCategory(Category category) {
        List<CategoryPermission> permissions = getCategoryPermissions(userService.getCurrentlyLoggedUser());
        return checkPermissions(Action.DELETE, permissions);
    }

    public boolean canViewCategories() {
        List<CategoryPermission> permissions = getCategoryPermissions(userService.getCurrentlyLoggedUser());
        return checkPermissions(Action.VIEW, permissions);
    }

    protected List<PostPermission> getPostPermissions(IDiscussionUser user, Discussion discussion) {
        Long dId = discussion.getId();
        Long tId = discussion.getTopic().getId();
        Long cId = discussion.getTopic().getCategory().getId();
        return permissionDao.findForUser(user, dId, tId, cId);
    }

    protected List<DiscussionPermission> getDiscussionPermissions(IDiscussionUser user, Topic topic) {
        Long tId = topic.getId();
        Long cId = topic.getCategory().getId();
        return permissionDao.findForUser(user, tId, cId);
    }

    protected List<TopicPermission> getTopicPermissions(IDiscussionUser user, Category category) {
        Long cId = category.getId();
        return permissionDao.findForUser(user, cId);
    }

    protected List<CategoryPermission> getCategoryPermissions(IDiscussionUser user) {
        return permissionDao.findForUser(user);
    }

    protected boolean checkPermissions(Action action, List<? extends AbstractPermission> permissions) {
        if (permissions.isEmpty()) {
            return false;
        }

        for (AbstractPermission permission : permissions) {
            if (!permission.getData().canDo(action)) {
                return false;
            }
        }
        return true;
    }

}
