package cz.zcu.fav.kiv.discussion.core.utils;

import cz.zcu.fav.kiv.discussion.core.entity.*;
import cz.zcu.fav.kiv.discussion.core.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class MapUtil {

    public static DiscussionModel mapDiscussionEntityModal(DiscussionEntity entity) {

        DiscussionModel model = new DiscussionModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setPass(entity.getPass());
        model.setPosts(mapPostEntityListToModelList(entity.getPosts()));

        ArrayList<UserModel> accessList = new ArrayList<UserModel>();
        for (UserEntity user : entity.getUserAccessList()) {
            accessList.add(mapUserEntityToModel(user));
        }
        model.setAccessList(accessList);

        return model;

    }

    public static UserModel mapUserEntityToModel(UserEntity entity) {

        UserModel model = new UserModel();
        model.setId(entity.getId());
        model.setPermission(mapPermissionEntityToModel(entity.getPermissions()));
        model.setName(entity.getName());
        model.setLastname(entity.getLastname());
        model.setUsername(entity.getUsername());

        return model;

    }

    public static CategoryModel mapCategoryEntityToModel(CategoryEntity entity) {

        CategoryModel model = new CategoryModel();
        model.setId(entity.getId());
        model.setName(entity.getName());

        return model;

    }


    public static TopicModel mapTopicEntityToModel(TopicEntity entity) {

        TopicModel model = new TopicModel();

        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());

        List<DiscussionModel> discussionModels = new ArrayList<DiscussionModel>();

        for (DiscussionEntity discussionEntity: entity.getDiscussions()) {
            discussionModels.add(mapDiscussionEntityModal(discussionEntity));
        }

        model.setDiscussions(discussionModels);

        return model;

    }

    public static PostModel mapPostEntityToModel(PostEntity entity) {

        if (entity == null) {
            return null;
        }

        PostModel model = new PostModel();
        model.setId(entity.getId());
        model.setUser(mapUserEntityToModel(entity.getUser()));
        model.setText(entity.getText());
        model.setCreated(entity.getCreated());
        model.setLevel(entity.getLevel());
        model.setDisabled(entity.isDisabled());
        model.setReplies(mapPostEntityListToModelList(entity.getReplies()));

        return model;
    }

    private static List<PostModel> mapPostEntityListToModelList(List<PostEntity> postsEntity) {

        List<PostModel> postsModel = new ArrayList<PostModel>();

        if (postsEntity == null)
            return postsModel;

        for (PostEntity post: postsEntity) {
            PostModel newPost = new PostModel(post.getId(), mapUserEntityToModel(post.getUser()), post.getText(), post.getCreated());
            newPost.setLevel(post.getLevel());
            newPost.setReplies(mapPostEntityListToModelList(post.getReplies()));
            newPost.setDisabled(post.isDisabled());
            postsModel.add(newPost);
        }

        return postsModel;
    }

    public static PermissionModel mapPermissionEntityToModel(PermissionEntity entity) {

        PermissionModel model = new PermissionModel();

        model.setCreateCategory(entity.isCreateCategory());
        model.setRemoveCategory(entity.isRemoveCategory());

        model.setCreateTopic(entity.isCreateTopic());
        model.setRemoveTopic(entity.isRemoveTopic());

        model.setCreateDiscussion(entity.isCreateDiscussion());
        model.setRemoveDiscussion(entity.isRemoveDiscussion());

        model.setCreatePost(entity.isCreatePost());
        model.setRemovePost(entity.isRemovePost());
        model.setDisablePost(entity.isDisablePost());

        model.setReadPrivateDiscussion(entity.isReadPrivateDiscussion());


        return model;

    }

    public static PermissionEntity mapPermissionModelToEntity(PermissionModel model) {

        PermissionEntity entity = new PermissionEntity();

        entity.setCreateCategory(model.isCreateCategory());
        entity.setRemoveCategory(model.isRemoveCategory());

        entity.setCreateTopic(model.isCreateTopic());
        entity.setRemoveTopic(model.isRemoveTopic());

        entity.setCreateDiscussion(model.isCreateDiscussion());
        entity.setRemoveDiscussion(model.isRemoveDiscussion());

        entity.setCreatePost(model.isCreatePost());
        entity.setRemovePost(model.isRemovePost());
        entity.setDisablePost(model.isDisablePost());

        entity.setReadPrivateDiscussion(model.isReadPrivateDiscussion());


        return entity;

    }
}
