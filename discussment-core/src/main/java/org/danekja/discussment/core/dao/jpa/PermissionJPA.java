package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.dao.IPermissionDao;
import org.danekja.discussment.core.domain.Permission;

/**
 * Created by Martin Bláha on 04.05.17.
 */

public class PermissionJPA extends GenericJPA<Permission> implements IPermissionDao {

    public PermissionJPA() {
        super(Permission.class);
    }

}
