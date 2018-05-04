package org.danekja.discussment.core.domain;

import javax.persistence.*;
import java.io.Serializable;

import static org.danekja.discussment.core.domain.UserPostReputation.GET_FOR_USER;

/**
 * Tracks which post reputations the user voted in and if he liked the post or not.
 *
 * Date: 19.2.18
 *
 * @author Jiri Kryda
 */
@Entity
@Table(name = "discussment_user_post_reputation")
@NamedQueries({
        @NamedQuery(name = GET_FOR_USER,
                query = "SELECT upr FROM UserPostReputation upr WHERE upr.userId = :userId AND upr.post.id = :postId")
})
public class UserPostReputation extends LongEntity implements Serializable {

    /**
     * The constant contains name of query for getting user post reputation
     */
    public static final String GET_FOR_USER = "UserPostReputation.getForUser";

    /**
     * Id of the user.
     */
    private String userId;

    /**
     * Post he voted on.
     */
    private Post post;

    /**
     * Constant to keep user's vote.
     */
    private boolean liked;

    public UserPostReputation(){}

    public UserPostReputation(String userId, Post post, boolean liked){
        this.userId = userId;
        this.post = post;
        this.liked = liked;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @ManyToOne
    @JoinColumn(name = "post_id")
    public Post getPost(){ return post; }

    public void setPost(Post post) {
        this.post = post;
    }

    public boolean getLiked(){ return liked; }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public void changeLiked(){
        if(this.liked){
            this.liked = false;
        } else {
            this.liked= true;
        }
    }

    @Override
    public String toString() {
        return "UserPostReputation{" +
                "userId=" + userId +
                ", post=" + post +
                ", liked=" + liked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserPostReputation that = (UserPostReputation) o;

        if (!userId.equals(that.userId)) return false;
        return post.equals(that.post);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + post.hashCode();
        return result;
    }
}

