package cz.zcu.fav.kiv.discussion.core.dao;

import cz.zcu.fav.kiv.discussion.core.entity.PermissionEntity;

/**
 * Created by Martin Bláha on 04.05.17.
 */

public class PermissionDao extends GenericDao<PermissionEntity> {

    public PermissionDao() {
        super(PermissionEntity.class);
    }

}
