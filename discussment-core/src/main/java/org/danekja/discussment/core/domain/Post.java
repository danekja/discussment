package org.danekja.discussment.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.danekja.discussment.core.domain.Post.GET_BY_DISCUSSION;

/**
 * Created by Martin Bláha on 19.01.17.
 *
 * The class represents a post in the discussion.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = GET_BY_DISCUSSION,
                query = "SELECT p FROM Post p WHERE p.discussion.id = :discussionId")
})
public class Post extends BaseEntity implements Serializable {

    /**
     * The constant contains name of query for getting posts by discussion
     */
    public static final String GET_BY_DISCUSSION = "Post.getByDiscussion";

    /**
     * The user which the post created
     */
    private Long userId;

    /**
     * Text of the post
     */
    private String text;

    /**
     * If the variable is true, the post is disabled. If false, the post is enabled.
     */
    private boolean disabled;

    /**
     * Level of the post. Value 0 is a first thread.
     */
    private int level;

    /**
     * The time when the post was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    /**
     * The Discussion where the post is. If the post is removed, the discussion still exists
     */
    @ManyToOne
    private Discussion discussion;

    /**
     * The parent post, if the parent post does not exist, is null
     */
    @ManyToOne
    private Post post;

    /**
     * List constant all replies. If the post is removed, tje replies are removed too.
     */
    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Post> replies = new ArrayList<Post>();

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public Post() {}

    public Post(String text) {
        this.text = text;
    }

    public Post(IDiscussionUser user, String text) {
        this.userId = user.getId();
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

    public void setReplies(List<Post> replies) {
        this.replies = replies;
    }

    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }

    public Long getUserId() {
        return userId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setUser(Long userId) {
        this.userId = userId;
    }

    public void setUser(IDiscussionUser user) {
        this.userId = user.getId();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Post> getReplies() {
        return replies;
    }

    public void addReply(Post reply) {
        replies.add(reply);
        if (reply.getPost() != this) {
            reply.setPost(this);
        }
    }

    public int getNumberOfReplies() {

        return getNumberOfReplies(this, 0);
    }

    private int getNumberOfReplies(Post postModel, int count) {

        count++;

        for (Post post: postModel.getReplies()) {
            count = getNumberOfReplies(post, count);
        }
        return count;
    }

    public Post getLastPost() {
        return getLastPost(this);
    }

    private Post getLastPost(Post postModel) {
        Post lastPost = null;

        for (Post post: postModel.getReplies()) {
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
