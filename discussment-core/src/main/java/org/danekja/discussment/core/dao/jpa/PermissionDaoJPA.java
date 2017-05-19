package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.dao.PermissionDao;
import org.danekja.discussment.core.domain.Permission;

/**
 * Created by Martin Bláha on 04.05.17.
 */

public class PermissionDaoJPA extends GenericDaoJPA<Permission> implements PermissionDao {

    public PermissionDaoJPA() {
        super(Permission.class);
    }

}
