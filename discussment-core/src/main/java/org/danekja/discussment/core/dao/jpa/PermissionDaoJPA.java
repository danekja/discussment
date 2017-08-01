package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.dao.PermissionDao;
import org.danekja.discussment.core.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.Permission;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Martin Bláha on 04.05.17.
 */

public class PermissionDaoJPA extends GenericDaoJPA<Permission> implements PermissionDao {

    public PermissionDaoJPA() {
        super(Permission.class);
    }

    public Permission getUsersPermissions(IDiscussionUser user) {
        String query = "SELECT p FROM "+Permission.class.getSimpleName()+" p " +
                " WHERE " +
                " p.user = :user";
        Query q = em.createQuery(query);
        q.setParameter("user", user);
        List<Permission> permissions = q.getResultList();
        return permissions.isEmpty() ? null : permissions.get(0);
    }
}
