package org.danekja.discussment.core.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The class represents the post's reputation on the website
 *
 * Date: 16.2.18
 *
 * @author Jiri Kryda
 */
@Embeddable
public class PostReputation implements Serializable {

    /**
     * The constant which tracks likes of the post.
     */
    private long likes;

    /**
     * The constant which tracks dislikes of the post.
     */
    private long dislikes;

    public PostReputation() {
        this.likes = 0;
        this.dislikes = 0;
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

    public synchronized void addLike(){
        this.likes++;
    }

    public synchronized void addDislike(){
        this.dislikes++;
    }

    public synchronized void removeLike(){
        this.likes--;
    }

    public synchronized void removeDislike(){
        this.dislikes--;
    }
}
