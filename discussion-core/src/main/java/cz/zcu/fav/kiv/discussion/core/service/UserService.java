package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.dao.IPermissionDao;
import cz.zcu.fav.kiv.discussion.core.dao.IUserDao;
import cz.zcu.fav.kiv.discussion.core.dao.jpa.PermissionJPA;
import cz.zcu.fav.kiv.discussion.core.dao.jpa.UserJPA;
import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.PermissionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;

import java.util.List;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class UserService {

    private static IUserDao userDao = new UserJPA();
    private static IPermissionDao permissionDao = new PermissionJPA();


    public static UserEntity addUser(UserEntity entity, PermissionEntity permissionEntity) {

        permissionEntity = permissionDao.save(permissionEntity);
        permissionEntity.setUser(entity);

        entity.setPermissions(permissionEntity);

        return userDao.save(entity);
    }

    public static List<UserEntity> getUsers() {

        return userDao.getUsers();
    }

    public static void removeUser(UserEntity userEntity) {
        userDao.remove(userEntity);
    }

    public static UserEntity getUserById(long userId) {

        return userDao.getById(userId);
    }

    public static void addAccessToDiscussion(UserEntity entity, DiscussionEntity en) {

        entity.getAccessListToDiscussion().add(en);
        en.getUserAccessList().add(entity);

        userDao.save(entity);
    }

    public static UserEntity getUserByUsername(String username) {

        return userDao.getUserByUsername(username);
    }


}
