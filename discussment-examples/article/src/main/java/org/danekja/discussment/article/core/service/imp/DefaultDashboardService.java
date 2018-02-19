package org.danekja.discussment.article.core.service.imp;

import org.danekja.discussment.article.core.domain.User;
import org.danekja.discussment.article.core.service.DashboardService;
import org.danekja.discussment.core.accesscontrol.dao.NewPermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.*;
import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DefaultDashboardService implements DashboardService {

    private NewPermissionDao permissionDao;

    public DefaultDashboardService(NewPermissionDao permissionDao){
        this.permissionDao = permissionDao;
    }

    public boolean canViewPost(User user, Discussion discussion) {
        List<PostPermission> permissions = getPostPermissions(user, discussion);
        return checkPermissions(Action.VIEW, permissions);
    }

    public boolean canAddPost(User user, Discussion discussion) {
        List<PostPermission> permissions = getPostPermissions(user, discussion);
        return checkPermissions(Action.CREATE, permissions);
    }

    public boolean canEditPost(User user, Discussion discussion) {
        List<PostPermission> permissions = getPostPermissions(user, discussion);
        return checkPermissions(Action.EDIT, permissions);
    }

    public boolean canRemovePost(User user, Discussion discussion) {
        List<PostPermission> permissions = getPostPermissions(user, discussion);
        return checkPermissions(Action.DELETE, permissions);
    }

    public boolean canViewPosts(User user, Discussion discussion) {
        List<PostPermission> permissions = getPostPermissions(user, discussion);
        return checkPermissions(Action.VIEW, permissions);
    }

    public boolean canAddDiscussion(User user, Topic topic) {
        List<DiscussionPermission> permissions = getDiscussionPermissions(user, topic);
        return checkPermissions(Action.CREATE, permissions);
    }

    public boolean canEditDiscussion(User user, Discussion discussion) {
        List<DiscussionPermission> permissions = getDiscussionPermissions(user, discussion.getTopic());
        return checkPermissions(Action.EDIT, permissions);
    }

    public boolean canRemoveDiscussion(User user, Discussion discussion) {
        List<DiscussionPermission> permissions = getDiscussionPermissions(user, discussion.getTopic());
        return checkPermissions(Action.DELETE, permissions);
    }

    public boolean canViewDiscussions(User user, Topic topic) {
        List<DiscussionPermission> permissions = getDiscussionPermissions(user, topic);
        return checkPermissions(Action.VIEW, permissions);
    }

    public boolean canAddTopic(User user, Category category) {
        List<TopicPermission> permissions = getTopicPermissions(user, category);
        return checkPermissions(Action.CREATE, permissions);
    }

    public boolean canEditTopic(User user, Topic topic) {
        List<TopicPermission> permissions = getTopicPermissions(user, topic.getCategory());
        return checkPermissions(Action.EDIT, permissions);
    }

    public boolean canRemoveTopic(User user, Topic topic) {
        List<TopicPermission> permissions = getTopicPermissions(user, topic.getCategory());
        return checkPermissions(Action.DELETE, permissions);
    }

    public boolean canViewTopics(User user, Category category) {
        List<TopicPermission> permissions = getTopicPermissions(user, category);
        return checkPermissions(Action.VIEW, permissions);
    }

    public boolean canAddCategory(User user) {
        List<CategoryPermission> permissions = getCategoryPermissions(user);
        return checkPermissions(Action.CREATE, permissions);
    }

    public boolean canEditCategory(User user, Category category) {
        List<CategoryPermission> permissions = getCategoryPermissions(user);
        return checkPermissions(Action.EDIT, permissions);
    }

    public boolean canRemoveCategory(User user, Category category) {
        List<CategoryPermission> permissions = getCategoryPermissions(user);
        return checkPermissions(Action.DELETE, permissions);
    }

    public boolean canViewCategories(User user) {
        List<CategoryPermission> permissions = getCategoryPermissions(user);
        return checkPermissions(Action.VIEW, permissions);
    }

    protected List<PostPermission> getPostPermissions(User user, Discussion discussion) {
        Long dId = discussion.getId();
        Long tId = discussion.getTopic().getId();
        Long cId = discussion.getTopic().getCategory().getId();
        return permissionDao.findForUser(user, dId, tId, cId);
    }

    protected List<DiscussionPermission> getDiscussionPermissions(User user, Topic topic) {
        Long tId = topic.getId();
        Long cId = topic.getCategory().getId();
        return permissionDao.findForUser(user, tId, cId);
    }

    protected List<TopicPermission> getTopicPermissions(User user, Category category) {
        Long cId = category.getId();
        return permissionDao.findForUser(user, cId);
    }

    protected List<CategoryPermission> getCategoryPermissions(User user) {
        return permissionDao.findForUser(user);
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
