package org.danekja.discussment.core.accesscontrol.service.impl;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.*;
import org.danekja.discussment.core.accesscontrol.service.AccessControlManagerService;
import org.danekja.discussment.core.accesscontrol.service.AccessControlService;
import org.danekja.discussment.core.accesscontrol.service.DiscussionUserService;
import org.danekja.discussment.core.accesscontrol.service.PermissionManagementService;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.Topic;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
public class PermissionService implements PermissionManagementService, AccessControlService, AccessControlManagerService {

    private PermissionDao permissionDao;
    private DiscussionUserService userService;

    public PermissionService(PermissionDao permissionDao, DiscussionUserService userService) {
        this.permissionDao = permissionDao;
        this.userService = userService;
    }

    @Override
    public void configurePostPermissions(IDiscussionUser user, Discussion discussion, PermissionData permissions) {
        permissionDao.save(new PostPermission(user.getDiscussionUserId(), discussion, permissions));
    }

    @Override
    public void configurePostPermissions(IDiscussionUser user, Topic topic, PermissionData permissions) {
        permissionDao.save(new PostPermission(user.getDiscussionUserId(), topic, permissions));
    }

    @Override
    public void configurePostPermissions(IDiscussionUser user, Category category, PermissionData permissions) {
        permissionDao.save(new PostPermission(user.getDiscussionUserId(), category, permissions));
    }

    @Override
    public void configurePostPermissions(IDiscussionUser user, PermissionData permissions) {
        permissionDao.save(new PostPermission(user.getDiscussionUserId(), permissions));
    }

    @Override
    public void configureDiscussionPermissions(IDiscussionUser user, Topic topic, PermissionData permissions) {
        permissionDao.save(new DiscussionPermission(user.getDiscussionUserId(), topic, permissions));
    }

    @Override
    public void configureDiscussionPermissions(IDiscussionUser user, Category category, PermissionData permissions) {
        permissionDao.save(new DiscussionPermission(user.getDiscussionUserId(), category, permissions));
    }

    @Override
    public void configureDiscussionPermissions(IDiscussionUser user, PermissionData permissions) {
        permissionDao.save(new DiscussionPermission(user.getDiscussionUserId(), permissions));
    }

    @Override
    public void configureTopicPermissions(IDiscussionUser user, Category category, PermissionData permissions) {
        permissionDao.save(new TopicPermission(user.getDiscussionUserId(), category, permissions));
    }

    @Override
    public void configureTopicPermissions(IDiscussionUser user, PermissionData permissions) {
        permissionDao.save(new TopicPermission(user.getDiscussionUserId(), permissions));
    }

    @Override
    public void configureCategoryPermissions(IDiscussionUser user, PermissionData permissions) {
        permissionDao.save(new CategoryPermission(user.getDiscussionUserId(), permissions));
    }

    @Override
    public boolean canViewPost(Post post) {
        return canViewPost(userService.getCurrentlyLoggedUser(), post);
    }

    @Override
    public boolean canAddPost(Discussion discussion) {
        return canAddPost(userService.getCurrentlyLoggedUser(), discussion);
    }

    @Override
    public boolean canEditPost(Post post) {
        return canEditPost(userService.getCurrentlyLoggedUser(), post);
    }

    @Override
    public boolean canRemovePost(Post post) {
        return canRemovePost(userService.getCurrentlyLoggedUser(), post);
    }

    @Override
    public boolean canViewPosts(Discussion discussion) {
        return canViewPosts(userService.getCurrentlyLoggedUser(), discussion);
    }

    @Override
    public boolean canAddDiscussion(Topic topic) {
        return canAddDiscussion(userService.getCurrentlyLoggedUser(), topic);
    }

    @Override
    public boolean canEditDiscussion(Discussion discussion) {
        return canEditDiscussion(userService.getCurrentlyLoggedUser(), discussion);
    }

    @Override
    public boolean canRemoveDiscussion(Discussion discussion) {
        return canRemoveDiscussion(userService.getCurrentlyLoggedUser(), discussion);
    }

    @Override
    public boolean canViewDiscussions(Topic topic) {
        return canViewDiscussions(userService.getCurrentlyLoggedUser(), topic);
    }

    @Override
    public boolean canAddTopic(Category category) {
        return canAddTopic(userService.getCurrentlyLoggedUser(), category);
    }

    @Override
    public boolean canEditTopic(Topic topic) {
        return canEditTopic(userService.getCurrentlyLoggedUser(), topic);
    }

    @Override
    public boolean canRemoveTopic(Topic topic) {
        return canRemoveTopic(userService.getCurrentlyLoggedUser(), topic);
    }

    @Override
    public boolean canViewTopics(Category category) {
        return canViewTopics(userService.getCurrentlyLoggedUser(), category);
    }

    @Override
    public boolean canAddCategory() {
        return canAddCategory(userService.getCurrentlyLoggedUser());
    }

    @Override
    public boolean canEditCategory(Category category) {
        return canEditCategory(userService.getCurrentlyLoggedUser(), category);
    }

    @Override
    public boolean canRemoveCategory(Category category) {
        return canRemoveCategory(userService.getCurrentlyLoggedUser(), category);
    }

    @Override
    public boolean canViewCategories() {
        return canViewCategories(userService.getCurrentlyLoggedUser());
    }

    @Override
    public boolean canAddPost(IDiscussionUser user, Discussion discussion) {
        List<PostPermission> permissions = getPostPermissions(user, discussion);
        return checkPermissions(Action.CREATE, permissions);
    }

    @Override
    public boolean canEditPost(IDiscussionUser user, Post post){
        if (user == null) {
            return false;
        } else if (Objects.equals(user.getDiscussionUserId(), post.getUserId())) {
            return true;
        } else {
            return canEditPosts(user, post.getDiscussion());
        }
    }

    @Override
    public boolean canEditPosts(IDiscussionUser user, Discussion discussion) {
        List<PostPermission> permissions = getPostPermissions(user, discussion);
        return checkPermissions(Action.EDIT, permissions);
    }

    @Override
    public boolean canRemovePost(IDiscussionUser user, Post post){
        if (user == null) {
            return false;
        } else if (Objects.equals(user.getDiscussionUserId(), post.getUserId())) {
            return true;
        } else {
            return canRemovePosts(user, post.getDiscussion());
        }
    }

    @Override
    public boolean canRemovePosts(IDiscussionUser user, Discussion discussion) {
        List<PostPermission> permissions = getPostPermissions(user, discussion);
        return checkPermissions(Action.DELETE, permissions);
    }

    @Override
    public boolean canViewPost(IDiscussionUser user, Post post) {
        if (user == null) {
            return false;
        } else if (Objects.equals(user.getDiscussionUserId(), post.getUserId())) {
            return true;
        } else {
            return canViewPosts(user, post.getDiscussion());
        }
    }

    @Override
    public boolean canViewPosts(IDiscussionUser user, Discussion discussion) {
        List<PostPermission> permissions = getPostPermissions(user, discussion);
        return checkPermissions(Action.VIEW, permissions);
    }

    @Override
    public boolean canAddDiscussion(IDiscussionUser user, Topic topic) {
        List<DiscussionPermission> permissions = getDiscussionPermissions(user, topic);
        return checkPermissions(Action.CREATE, permissions);
    }

    @Override
    public boolean canEditDiscussion(IDiscussionUser user, Discussion discussion) {
        List<DiscussionPermission> permissions = getDiscussionPermissions(user, discussion.getTopic());
        return checkPermissions(Action.EDIT, permissions);
    }

    @Override
    public boolean canRemoveDiscussion(IDiscussionUser user, Discussion discussion) {
        List<DiscussionPermission> permissions = getDiscussionPermissions(user, discussion.getTopic());
        return checkPermissions(Action.DELETE, permissions);
    }

    @Override
    public boolean canViewDiscussions(IDiscussionUser user, Topic topic) {
        List<DiscussionPermission> permissions = getDiscussionPermissions(user, topic);
        return checkPermissions(Action.VIEW, permissions);
    }

    @Override
    public boolean canAddTopic(IDiscussionUser user, Category category) {
        List<TopicPermission> permissions = getTopicPermissions(user, category);
        return checkPermissions(Action.CREATE, permissions);
    }

    @Override
    public boolean canEditTopic(IDiscussionUser user, Topic topic) {
        List<TopicPermission> permissions = getTopicPermissions(user, topic.getCategory());
        return checkPermissions(Action.EDIT, permissions);
    }

    @Override
    public boolean canRemoveTopic(IDiscussionUser user, Topic topic) {
        List<TopicPermission> permissions = getTopicPermissions(user, topic.getCategory());
        return checkPermissions(Action.DELETE, permissions);
    }

    @Override
    public boolean canViewTopics(IDiscussionUser user, Category category) {
        List<TopicPermission> permissions = getTopicPermissions(user, category);
        return checkPermissions(Action.VIEW, permissions);
    }

    @Override
    public boolean canAddCategory(IDiscussionUser user) {
        List<CategoryPermission> permissions = getCategoryPermissions(user);
        return checkPermissions(Action.CREATE, permissions);
    }

    @Override
    public boolean canEditCategory(IDiscussionUser user, Category category) {
        List<CategoryPermission> permissions = getCategoryPermissions(user);
        return checkPermissions(Action.EDIT, permissions);
    }

    @Override
    public boolean canRemoveCategory(IDiscussionUser user, Category category) {
        List<CategoryPermission> permissions = getCategoryPermissions(user);
        return checkPermissions(Action.DELETE, permissions);
    }

    @Override
    public boolean canViewCategories(IDiscussionUser user) {
        List<CategoryPermission> permissions = getCategoryPermissions(user);
        return checkPermissions(Action.VIEW, permissions);
    }

    protected List<PostPermission> getPostPermissions(IDiscussionUser user, Discussion discussion) {
        if (user == null) {
            return Collections.emptyList();
        } else {
            Long dId = discussion.getId();
            Long tId = discussion.getTopic().getId();
            Long cId = discussion.getTopic().getCategory().getId();
            return permissionDao.findForUser(user, dId, tId, cId);
        }
    }

    protected List<DiscussionPermission> getDiscussionPermissions(IDiscussionUser user, Topic topic) {
        if (user == null) {
            return Collections.emptyList();
        } else {
            Long tId = topic.getId();
            Long cId = topic.getCategory().getId();
            return permissionDao.findForUser(user, tId, cId);
        }
    }

    protected List<TopicPermission> getTopicPermissions(IDiscussionUser user, Category category) {
        if (user == null) {
            return Collections.emptyList();
        } else {
            Long cId = category.getId();
            return permissionDao.findForUser(user, cId);
        }
    }

    protected List<CategoryPermission> getCategoryPermissions(IDiscussionUser user) {
        if (user == null) {
            return Collections.emptyList();
        } else {
            return permissionDao.findForUser(user);
        }
    }

    /**
     * Helper method which resolves permission for a particular action.
     * Method will create a copy of provided permissions and sort them by level so that most detailed permission
     * is the first one. This most detailed permission is then used to resolve whether action can or can't be performed.
     *
     * @param action Action to be performed.
     * @param permissions List of permissions which may apply to item the action is to be performed on.
     * @return True if the action can be performed, false otherwise.
     */

    protected boolean checkPermissions(Action action, List<? extends AbstractPermission> permissions) {
        if (permissions.isEmpty()) {
            return false;
        }

        // only the lowest level (DISCUSSION < TOPIC < CATEGORY < GLOBAL) permission matters
        // Levels in PermissionLevel are sorted in descending order (from GLOBAL to DISCUSSION)
        // so Comparator.reverseOrder() is added so that permissions are sorted in ascending order
        List<? extends AbstractPermission> sortedPermissions = new ArrayList<>(permissions);
        sortedPermissions.sort(Comparator.comparing(o -> o.getId().getLevel(), Comparator.reverseOrder()));
        return sortedPermissions.get(0).getData().canDo(action);
    }



}
