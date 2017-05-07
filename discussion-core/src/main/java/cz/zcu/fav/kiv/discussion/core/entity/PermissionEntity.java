package cz.zcu.fav.kiv.discussion.core.entity;


import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by Martin Bláha on 19.01.17.
 */
@Entity
public class PermissionEntity extends BaseEntity {

    @OneToOne
    private UserEntity user;

    private boolean createCategory;
    private boolean removeCategory;

    private boolean createTopic;
    private boolean removeTopic;

    private boolean createDiscussion;
    private boolean removeDiscussion;

    private boolean createPost;
    private boolean removePost;
    private boolean disablePost;

    private boolean readPrivateDiscussion;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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
}
