package org.danekja.discussment.core.accesscontrol.dao;

import org.danekja.discussment.core.accesscontrol.domain.*;
import org.danekja.discussment.core.dao.GenericDao;

import java.util.List;

/**
 * TODO rename this to {@link PermissionDao} before release
 */
public interface PermissionDao extends GenericDao<PermissionId, AbstractPermission> {

    List<PostPermission> findForUser(IDiscussionUser user, Long discussionId, Long topicId, Long categoryId);

    List<DiscussionPermission> findForUser(IDiscussionUser user, Long topicId, Long categoryId);

    List<TopicPermission> findForUser(IDiscussionUser user, Long categoryId);

    List<CategoryPermission> findForUser(IDiscussionUser user);

    <Z extends AbstractPermission> List<Z> findByType(IDiscussionUser user, Class<Z> type, PermissionLevel level, Long itemId);
}
