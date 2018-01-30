package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.dao.PermissionDao;
import org.danekja.discussment.core.domain.Permission;

import javax.persistence.EntityManager;

/**
 * Created by Martin Bláha on 04.05.17.
 */

public class PermissionDaoJPA extends GenericDaoJPA<Permission> implements PermissionDao {

    public PermissionDaoJPA(EntityManager em) {
        super(Permission.class, em);
    }

}
