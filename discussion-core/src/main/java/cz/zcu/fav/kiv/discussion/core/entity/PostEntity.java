package cz.zcu.fav.kiv.discussion.core.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Martin Bláha on 19.01.17.
 */
@Entity
public class PostEntity extends BaseEntity {

    @ManyToOne
    private UserEntity user;

    private String text;

    private boolean disabled;

    private int level;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @ManyToOne
    private  DiscussionEntity discussion;

    @ManyToOne
    private PostEntity post;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<PostEntity> replies = new ArrayList<PostEntity>();

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public PostEntity() {}

    public PostEntity(UserEntity user, String text) {
        this.user = user;
        this.text = text;
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

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setReplies(List<PostEntity> replies) {
        this.replies = replies;
    }

    public DiscussionEntity getDiscussion() {
        return discussion;
    }

    public void setDiscussion(DiscussionEntity discussion) {
        this.discussion = discussion;
    }

    public UserEntity getUser() {
        return user;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<PostEntity> getReplies() {
        return replies;
    }

    public void addReply(PostEntity reply) {
        replies.add(reply);
        if (reply.getPost() != this) {
            reply.setPost(this);
        }
    }

}
