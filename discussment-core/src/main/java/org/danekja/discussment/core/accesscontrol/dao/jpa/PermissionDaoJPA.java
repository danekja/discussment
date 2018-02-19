package org.danekja.discussment.core.accesscontrol.dao.jpa;

import org.danekja.discussment.core.accesscontrol.dao.NewPermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.*;
import org.danekja.discussment.core.dao.jpa.GenericDaoJPA;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Martin Bláha on 04.05.17.
 */

public class PermissionDaoJPA extends GenericDaoJPA<PermissionId, AbstractPermission> implements NewPermissionDao {

    public PermissionDaoJPA(EntityManager em) {
        super(AbstractPermission.class, em);
    }

    public List<PostPermission> findForUser(IDiscussionUser user, Long discussionId, Long topicId, Long categoryId) {
        return null;
    }

    public List<DiscussionPermission> findForUser(IDiscussionUser user, Long topicId, Long categoryId) {
        return null;
    }

    public List<TopicPermission> findForUser(IDiscussionUser user, Long categoryId) {
        return null;
    }

    public List<CategoryPermission> findForUser(IDiscussionUser user) {
        return null;
    }

    public <Z extends AbstractPermission> List<Z> findByType(IDiscussionUser user, Class<Z> type, PermissionLevel level, Long itemId) {
        return null;
    }

}
