package org.danekja.discussment.core.accesscontrol.dao.jpa;

import org.danekja.discussment.core.accesscontrol.dao.PermissionDao;
import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.accesscontrol.domain.Permission;
import org.danekja.discussment.core.dao.jpa.GenericDaoJPA;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Old dao kept for backward compatibility. Should be deleted when not needed anymore.
 *
 * Created by Zdenek Vales on 26.11.2017.
 */
@Deprecated
public class OldPermissionDaoJPA extends GenericDaoJPA<Long, Permission> implements PermissionDao {

    public OldPermissionDaoJPA(EntityManager em) {
        super(Permission.class, em);
    }

    public Permission getUsersPermissions(IDiscussionUser user) {
        String query = "SELECT p FROM old_permission p " +
                " WHERE " +
                " p.userId = :userId";
        Query q = em.createQuery(query);
        q.setParameter("userId", user.getDiscussionUserId());
        List<Permission> permissions = q.getResultList();
        return permissions.isEmpty() ? null : permissions.get(0);
    }
}
