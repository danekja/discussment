package org.danekja.discussment.core.accesscontrol.domain;


import org.danekja.discussment.core.domain.BaseEntity;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by Martin Bláha on 19.01.17.
 *
 * The class represents a permission for the userId.
 */
@Entity
public class Permission extends BaseEntity implements Serializable {

    /**
     * The constant contains index for setting of creating the category.
     */
    public static final int CREATE_CATEGORY = 1;

    /**
     * The constant contains index for setting of removing the category.
     */
    public static final int REMOVE_CATEGORY = 2;

    /**
     * The constant contains index for setting of creating the topic.
     */
    public static final int CREATE_TOPIC = 3;

    /**
     * The constant contains index for setting of removing the topic.
     */
    public static final int REMOVE_TOPIC = 4;

    /**
     * The constant contains index for setting of creating the discussion.
     */
    public static final int CREATE_DISCUSSION = 5;

    /**
     * The constant contains index for setting of removing the discussion.
     */
    public static final int REMOVE_DISCUSSION = 6;

    /**
     * The constant contains index for setting of creating the post.
     */
    public static final int CREATE_POST = 7;

    /**
     * The constant contains index for setting of removing the post.
     */
    public static final int REMOVE_POST = 8;

    /**
     * The constant contains index for setting of disabling the post.
     */
    public static final int DISABLE_POST = 9;

    /**
     * The constant contains index for setting of reading the private discussion.
     */
    public static final int READ_PRIVATE_DISCUSSION = 10;


    /**
     * User which has this permissions.
     */
    private Long userId;

    /**
     * If value is true, the userId can create the category.
     */
    private boolean createCategory;

    /**
     * If value is true, the userId can remove the category.
     */
    private boolean removeCategory;

    /**
     * If value is true, the userId can create the topic.
     */
    private boolean createTopic;

    /**
     * If value is true, the userId can remove the topic.
     */
    private boolean removeTopic;

    /**
     * If value is true, the userId can create the discussion.
     */
    private boolean createDiscussion;

    /**
     * If value is true, the userId can remove the discussion.
     */
    private boolean removeDiscussion;

    /**
     * If value is true, the userId can create the post.
     */
    private boolean createPost;

    /**
     * If value is true, the userId can remove the post.
     */
    private boolean removePost;

    /**
     * If value is true, the userId can disable the post.
     */
    private boolean disablePost;

    /**
     * If value is true, the userId can read the private discussion.
     */
    private boolean readPrivateDiscussion;

    public void setPermissions(Map<Integer, Boolean> permissions) {
        for (Map.Entry<Integer, Boolean> entry : permissions.entrySet()) {
            setPermission(entry.getKey(), entry.getValue());
        }
    }

    public void setPermission(int permission, boolean value) {
        switch (permission) {
            case CREATE_CATEGORY:
                setCreateCategory(value);
                break;
            case REMOVE_CATEGORY:
                setRemoveCategory(value);
                break;
            case CREATE_TOPIC:
                setCreateTopic(value);
                break;
            case REMOVE_TOPIC:
                setRemoveTopic(value);
                break;
            case CREATE_DISCUSSION:
                setCreateDiscussion(value);
                break;
            case REMOVE_DISCUSSION:
                setRemoveDiscussion(value);
                break;
            case CREATE_POST:
                setCreatePost(value);
                break;
            case REMOVE_POST:
                setRemovePost(value);
                break;
            case DISABLE_POST:
                setDisablePost(value);
                break;
            case READ_PRIVATE_DISCUSSION:
                setReadPrivateDiscussion(value);
                break;
        }
    }

    public boolean getPermission(int permission) {
        switch (permission) {
            case CREATE_CATEGORY:
                return isCreateCategory();
            case REMOVE_CATEGORY:
                return isRemoveCategory();
            case CREATE_TOPIC:
                return isCreateTopic();
            case REMOVE_TOPIC:
                return isRemoveTopic();
            case CREATE_DISCUSSION:
                return isCreateDiscussion();
            case REMOVE_DISCUSSION:
                return isRemoveDiscussion();
            case CREATE_POST:
                return isCreatePost();
            case REMOVE_POST:
                return isRemovePost();
            case DISABLE_POST:
                return isDisablePost();
            case READ_PRIVATE_DISCUSSION:
                return isReadPrivateDiscussion();
            default:
                return false;
        }
    }

    /**
     * Set permission from other object.
     * @param other Current permissions will be updated from this object.
     */
    public void update(Permission other) {
        for (int i = CREATE_CATEGORY; i <= READ_PRIVATE_DISCUSSION ; i++) {
            this.setPermission(i, other.getPermission(i));
        }
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isCreateCategory() {
        return createCategory;
    }

    public void setCreateCategory(boolean createCategory) {
        this.createCategory = createCategory;
    }

    public boolean isRemoveCategory() {
        return removeCategory;
    }

    public void setRemoveCategory(boolean removeCategory) {
        this.removeCategory = removeCategory;
    }

    public boolean isCreateTopic() {
        return createTopic;
    }

    public void setCreateTopic(boolean createTopic) {
        this.createTopic = createTopic;
    }

    public boolean isRemoveTopic() {
        return removeTopic;
    }

    public void setRemoveTopic(boolean removeTopic) {
        this.removeTopic = removeTopic;
    }

    public boolean isCreateDiscussion() {
        return createDiscussion;
    }

    public void setCreateDiscussion(boolean createDiscussion) {
        this.createDiscussion = createDiscussion;
    }

    public boolean isRemoveDiscussion() {
        return removeDiscussion;
    }

    public void setRemoveDiscussion(boolean removeDiscussion) {
        this.removeDiscussion = removeDiscussion;
    }

    public boolean isCreatePost() {
        return createPost;
    }

    public void setCreatePost(boolean createPost) {
        this.createPost = createPost;
    }

    public boolean isRemovePost() {
        return removePost;
    }

    public void setRemovePost(boolean removePost) {
        this.removePost = removePost;
    }

    public boolean isDisablePost() {
        return disablePost;
    }

    public void setDisablePost(boolean disablePost) {
        this.disablePost = disablePost;
    }

    public boolean isReadPrivateDiscussion() {
        return readPrivateDiscussion;
    }

    public void setReadPrivateDiscussion(boolean readPrivateDiscussion) {
        this.readPrivateDiscussion = readPrivateDiscussion;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "userId=" + userId +
                ", createCategory=" + createCategory +
                ", removeCategory=" + removeCategory +
                ", createTopic=" + createTopic +
                ", removeTopic=" + removeTopic +
                ", createDiscussion=" + createDiscussion +
                ", removeDiscussion=" + removeDiscussion +
                ", createPost=" + createPost +
                ", removePost=" + removePost +
                ", disablePost=" + disablePost +
                ", readPrivateDiscussion=" + readPrivateDiscussion +
                '}';
    }
}
