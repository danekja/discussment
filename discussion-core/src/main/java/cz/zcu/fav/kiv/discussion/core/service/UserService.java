package cz.zcu.fav.kiv.discussion.core.service;

import cz.zcu.fav.kiv.discussion.core.dao.DiscussionDao;
import cz.zcu.fav.kiv.discussion.core.dao.PermissionDao;
import cz.zcu.fav.kiv.discussion.core.dao.UserDao;
import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.PermissionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.UserEntity;
import cz.zcu.fav.kiv.discussion.core.model.PermissionModel;
import cz.zcu.fav.kiv.discussion.core.model.UserModel;
import cz.zcu.fav.kiv.discussion.core.utils.MapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Martin Bláha on 20.01.17.
 */
public class UserService {

    private static UserDao userDao = new UserDao();
    private static DiscussionDao discussionDao = new DiscussionDao();
    private static PermissionDao permissionDao = new PermissionDao();

    public static UserModel addUser(String username, String name, String lastname) {
        return addUser(username, name, lastname, new HashMap<Integer, Boolean>());
    }

    public static UserModel addUser(String username, String name, String lastname, Map<Integer, Boolean> permissions) {

        UserEntity entity = new UserEntity(username, name, lastname);

        PermissionModel permissionModel = new PermissionModel();
        permissionModel.setPermissions(permissions);

        PermissionEntity permissionEntity = MapUtil.mapPermissionModelToEntity(permissionModel);
        permissionEntity = permissionDao.save(permissionEntity);
        permissionEntity.setUser(entity);

        entity.setPermissions(permissionEntity);

        entity = userDao.save(entity);

        return MapUtil.mapUserEntityToModel(entity);
    }

    public static List<UserModel> getUsers() {
        List<UserModel> userModelList = new ArrayList<UserModel>();

        for (UserEntity userEntity: userDao.getUsers()) {
            userModelList.add(MapUtil.mapUserEntityToModel(userEntity));
        }

        return userModelList;
    }

    public static void removeUserById(long userId) {
        userDao.remove(userDao.getById(userId));
    }

    public static UserModel getUserById(long userId) {
        UserEntity entity = userDao.getById(userId);

        if (entity != null) {
            return MapUtil.mapUserEntityToModel(entity);
        }
        return null;
    }

    public static UserModel getUserByUsername(String username) {

        UserEntity entity = userDao.getByUsername(username);

        if (entity != null) {
            return MapUtil.mapUserEntityToModel(entity);
        }
        return null;
    }

    public static void setUsername(long userId, String username) {
        UserEntity entity = userDao.getById(userId);
        entity.setUsername(username);
        userDao.save(entity);
    }

    public static void setName(long userId, String name) {
        UserEntity entity = userDao.getById(userId);
        entity.setName(name);
        userDao.save(entity);
    }

    public static void setLastname(long userId, String lastname) {
        UserEntity entity = userDao.getById(userId);
        entity.setLastname(lastname);
        userDao.save(entity);
    }

    public static void addAccessToDiscussion(long userId, long discussionId) {

        UserEntity entity = userDao.getById(userId);
        DiscussionEntity en = discussionDao.getById(discussionId);

        entity.getAccessListToDiscussion().add(en);
        en.getUserAccessList().add(entity);

        userDao.save(entity);
    }

}
