package org.danekja.discussment.core.domain;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;

import javax.persistence.*;
import java.io.Serializable;

import static org.danekja.discussment.core.domain.UserPostReputation.GET_FOR_USER;

/**
 * Join table for IDiscussionUser and PostReputation.
 * Tracks which post reputations the user voted in and if he liked the post or not.
 *
 * Date: 19.2.18
 *
 * @author Jiri Kryda
 */
@Entity
@Table(name = "user_post_reputation")
@NamedQueries({
        @NamedQuery(name = GET_FOR_USER,
                query = "SELECT upr FROM UserPostReputation upr WHERE upr.userId = :userId AND upr.postReputation.id = :postReputationId")
})
public class UserPostReputation extends LongEntity implements Serializable {

    /**
     * The constant contains name of query for getting user post reputation
     */
    public static final String GET_FOR_USER = "UserPostReputation.getForUser";

    /**
     * After the object is load from database, actual user object can be added
     * to this class. Note that this field is not persisted.
     */
    private IDiscussionUser user;

    /**
     * Id of the user.
     */
    private String userId;

    /**
     * Post reputation he voted in.
     */
    private PostReputation postReputation;

    /**
     * Constant to keep user's vote.
     */
    private boolean liked;

    public UserPostReputation(){}

    public UserPostReputation(String userId, PostReputation postReputation, boolean liked){
        this.userId = userId;
        this.postReputation = postReputation;
        this.liked = liked;
    }
    @Transient
    public IDiscussionUser getUser() {
        return user;
    }

    public void setUser(IDiscussionUser user) {
        this.user = user;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @ManyToOne
    @JoinColumn(name = "postreputation_id")
    public PostReputation getPostReputation(){ return postReputation; }

    public void setPostReputation(PostReputation postReputation) {
        this.postReputation = postReputation;
    }

    public boolean getLiked(){ return liked; }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "UserDiscussion{" +
                "user=" + user +
                ", userId=" + userId +
                ", discussion=" + postReputation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserPostReputation that = (UserPostReputation) o;

        if (!userId.equals(that.userId)) return false;
        return postReputation.equals(that.postReputation);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + postReputation.hashCode();
        return result;
    }
}

