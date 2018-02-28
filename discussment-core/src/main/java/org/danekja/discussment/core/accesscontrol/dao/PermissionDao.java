package org.danekja.discussment.core.accesscontrol.dao;

import org.danekja.discussment.core.accesscontrol.domain.*;
import org.danekja.discussment.core.dao.GenericDao;

import java.util.List;

/**
 * The interface extends GenericDao on methods for working with permissions in a database
 */
public interface PermissionDao extends GenericDao<PermissionId, AbstractPermission> {

    /**
     * Gets user's post permissions in a database.
     *
     * @param user User to get permissions for
     * @param discussionId Id of the discussion containing posts
     * @param topicId Id of the topic containing discussion
     * @param categoryId Id of the category containing topic
     * @return list of Post permissions
     */
    List<PostPermission> findForUser(IDiscussionUser user, Long discussionId, Long topicId, Long categoryId);

    /**
     * Gets user's discussion permissions in a database.
     *
     * @param user User to get permissions for
     * @param topicId Id of the topic containing discussion
     * @param categoryId Id of the category containing topic
     * @return list of Discussion permissions
     */
    List<DiscussionPermission> findForUser(IDiscussionUser user, Long topicId, Long categoryId);

    /**
     * Gets user's topic permissions in a database.
     *
     * @param user User to get permissions for
     * @param categoryId Id of the category containing topic
     * @return list of Topic permissions
     */
    List<TopicPermission> findForUser(IDiscussionUser user, Long categoryId);

    /**
     * Gets user's category permissions in a database.
     *
     * @param user User to get permissions for
     * @return list of Category permissions
     */
    List<CategoryPermission> findForUser(IDiscussionUser user);

    /**
     * Gets user's permissions of given type in the database
     *
     * @param user User to get permissions for
     * @param type Type of component to get permissions for
     * @param level Level on which certain permission applies
     * @param itemId Id of the component
     * @return list of Discussion permissions
     */
    <Z extends AbstractPermission> List<Z> findByType(IDiscussionUser user, Class<Z> type, PermissionLevel level, Long itemId);
}
