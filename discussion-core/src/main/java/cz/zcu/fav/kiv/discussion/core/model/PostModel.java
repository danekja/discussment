package cz.zcu.fav.kiv.discussion.core.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Martin Bláha on 22.01.17.
 */
public class PostModel implements Serializable {

    private long id;

    private UserModel user;

    private String text;

    private List<PostModel> replies;

    private Date created;

    private boolean disabled;

    private int level;

    public PostModel() {
    }

    public PostModel(long id, UserModel user, String text, Date created) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.created = created;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getCreated() {
        return created;
    }

    public String getCreatedFormat() {
        SimpleDateFormat formatData = new SimpleDateFormat("d.M.yyyy H:mm:ss");

        return formatData.format(created);
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<PostModel> getReplies() {
        return replies;
    }

    public void setReplies(List<PostModel> replies) {
        this.replies = replies;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getNumberOfReplies() {

        return getNumberOfReplies(this, 0);
    }

    private int getNumberOfReplies(PostModel postModel, int count) {

        count++;

        for (PostModel post: postModel.getReplies()) {
            count = getNumberOfReplies(post, count);
        }
        return count;
    }

    public PostModel getLastPost() {
        return getLastPost(this);
    }

    private PostModel getLastPost(PostModel postModel) {
        PostModel lastPost = null;

        for (PostModel post: postModel.getReplies()) {
            lastPost = getLastPost(post);

            if (post.getCreated().compareTo(lastPost.getCreated()) > 0) {
                lastPost = post;
            }
        }

        if (lastPost == null) {
            return this;
        } else {
            return lastPost;
        }

    }
}
