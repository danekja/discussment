package cz.zcu.fav.kiv.discussion.core.dao.jpa;

import cz.zcu.fav.kiv.discussion.core.dao.IPermissionDao;
import cz.zcu.fav.kiv.discussion.core.entity.PermissionEntity;

/**
 * Created by Martin Bláha on 04.05.17.
 */

public class PermissionJPA extends GenericJPA<PermissionEntity> implements IPermissionDao {

    public PermissionJPA() {
        super(PermissionEntity.class);
    }

}
