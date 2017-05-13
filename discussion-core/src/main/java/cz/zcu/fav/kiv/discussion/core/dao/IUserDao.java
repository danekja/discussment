package cz.zcu.fav.kiv.discussion.core.dao;

import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 */
public interface IUserDao extends IGenericDao<UserEntity> {
    UserEntity getUserByUsername(String username);

    List<UserEntity> getUsers();
}
