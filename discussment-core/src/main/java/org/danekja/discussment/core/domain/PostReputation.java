package org.danekja.discussment.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.danekja.discussment.core.domain.PostReputation.GET_BY_POST;

/**
 * The class represents the post's reputation on the website
 *
 * Date: 16.2.18
 *
 * @author Jiri Kryda
 */
@Entity
@Table(name = "post_reputation")
@NamedQueries({
        @NamedQuery(name = GET_BY_POST,
                query = "SELECT pr FROM PostReputation pr WHERE pr.post.id = :postId")
})
public class PostReputation extends LongEntity implements Serializable {

    /**
     * The constant contains name of query for getting post reputation
     */
    public static final String GET_BY_POST = "PostReputation.getByPost";


    /**
     * Post to which post reputation belongs to. If the post is removed the post reputation is removed too.
     */
    private Post post;

    /**
     * The constant which tracks likes of the post.
     */
    private long likes;

    /**
     * The constant which tracks dislikes of the post.
     */
    private long dislikes;

    /**
     * List contains users voting in this post reputation. It the post reputation is removed these are removed too.
     */
    private List<UserPostReputation> userPostReputations = new ArrayList<UserPostReputation>();

    public PostReputation() {}

    public PostReputation(Post post) {
        super();
        this.post = post;
    }

    @OneToOne
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long like) {
        this.likes = like;
    }

    public long getDislikes() {
        return dislikes;
    }

    public void setDislikes(long dislike) {
        this.dislikes = dislike;
    }

    @OneToMany(mappedBy = "postReputation", orphanRemoval = true)
    public List<UserPostReputation> getUserPostReputations() { return userPostReputations; }

    public void setUserPostReputations(List<UserPostReputation> userPostReputations) {
        this.userPostReputations = userPostReputations;
    }
}
