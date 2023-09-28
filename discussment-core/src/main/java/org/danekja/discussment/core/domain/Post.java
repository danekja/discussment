package org.danekja.discussment.core.domain;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.danekja.discussment.core.domain.Post.*;

/**
 * Created by Martin Bláha on 19.01.17.
 *
 * The class represents a post in the discussion.
 */
@Entity
@Table(name = "discussment_post")
@NamedQueries({
        @NamedQuery(name = GET_BY_DISCUSSION,
                query = "SELECT p FROM Post p WHERE p.discussion.id = :discussionId"),
        @NamedQuery(name = COUNT_BY_DISCUSSION,
                query = "SELECT COUNT(p) FROM Post p WHERE p.discussion.id = :discussionId"),
        @NamedQuery(name = COUNT_BY_DISCUSSIONS,
                query = "SELECT p.discussion.id, COUNT(p) FROM Post p WHERE p.discussion.id IN :discussionIds GROUP BY p.discussion.id"),
        @NamedQuery(name = GET_BASE_POSTS_BY_DISCUSSION,
                query = "SELECT p FROM Post p WHERE p.discussion.id = :discussionId AND p.level = 0"),
        @NamedQuery(name = GET_REPLIES_FOR_POST,
                query = "SELECT p FROM Post p WHERE p.post.id = :postId")
})
public class Post extends LongEntity implements Serializable {

    /**
     * String which will separate particular ids in chainId.
     */
    public static final String CHAIN_ID_SEPARATOR = ":";

    /**
     * The constant contains name of query for getting posts by discussion
     */
    public static final String GET_BY_DISCUSSION = "Post.getByDiscussion";

    /**
     * The constant contains name of query for getting number of posts by discussion
     */
    public static final String COUNT_BY_DISCUSSION = "Post.countByDiscussion";

    /**
     * The constant contains name of query for getting numbers of posts by discussions
     */
    public static final String COUNT_BY_DISCUSSIONS = "Post.countByDiscussions";

    /**
     * The constant contains name of query for getting base posts in discussion
     */
    public static final String GET_BASE_POSTS_BY_DISCUSSION = "Post.getBasePostsByDiscussion";

    /**
     * The constant contains name of query for getting replies of the post
     */
    public static final String GET_REPLIES_FOR_POST = "Post.getRepliesForPost";

    /**
     * The user who created the post
     */
    private String userId;

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
    private Date created;

    /**
     * The Discussion where the post is. If the post is removed, the discussion still exists
     */
    private Discussion discussion;

    /**
     * The parent post, if the parent post does not exist, is null
     */
    private Post post;

    /**
     * The reputation of the post. If the post is removed, its reputation is removed too.
     */
    private PostReputation postReputation = new PostReputation();

    /**
     * Id of the reply chain. ChainId of each consists of chainId of parent post and id of this reply.
     * This way every chain will have it's own prefix and every chainId will be unique.
     */
    private String chainId;

    /**
     * List contains all replies. If the post is removed, the replies are removed too.
     */
    private List<Post> replies = new ArrayList<>();

    /**
     * List contains users voting in this post reputation. It the post reputation is removed these are removed too.
     */
    private List<UserPostReputation> userPostReputations = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public Post() {
        chainId = "";
        level = 0;
    }

    public Post(Long id) {
        super(id);
    }

    public Post(String text) {
        super();
        this.text = text;
    }

    public Post(IDiscussionUser user, String text) {
        this.userId = user.getDiscussionUserId();
        this.text = text;
    }

    @Column(name = "level")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Transient
    public String getCreatedFormat() {
        SimpleDateFormat formatData = new SimpleDateFormat("d.M.yyyy H:mm:ss");

        return formatData.format(created);
    }

    @Column(name = "is_disabled")
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setReplies(List<Post> replies) {
        this.replies = replies;
    }

    @ManyToOne
    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }


    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @ManyToOne
    @JoinColumn(name = "parent_post_id")
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    public List<Post> getReplies() {
        return replies;
    }

    public void addReply(Post reply) {
        replies.add(reply);
        if (reply.getPost() != this) {
            reply.setPost(this);
        }
    }

    @Column(name = "chain_id")
    public String getChainId() {
        if (this.chainId == null) {
            //initialize chainId from parent object if it exists
            if (this.post != null) {
                if (StringUtils.isEmpty(this.post.chainId)) {
                    this.chainId = this.post.getId().toString();
                } else {
                    this.chainId = new StringBuilder(this.post.chainId)
                            .append(CHAIN_ID_SEPARATOR)
                            .append(this.post.getId()).toString();
                }
            }

        }
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    @Embedded
    public PostReputation getPostReputation(){ return  postReputation; }

    public void setPostReputation(PostReputation postReputation) { this.postReputation = postReputation; }

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    public List<UserPostReputation> getUserPostReputations() { return userPostReputations; }

    public void setUserPostReputations(List<UserPostReputation> userPostReputations) {
        this.userPostReputations = userPostReputations;
    }

    public void setAsReply(Post post) {
        this.setPost(post);
        this.setLevel(post.getLevel() + 1);
    }

}
