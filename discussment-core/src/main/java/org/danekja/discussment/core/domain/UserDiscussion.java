package org.danekja.discussment.core.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Join table for IDiscussionUser and Discussion.
 * Defines which discussions user can access.
 *
 * Created by Zdenek Vales on 2.8.2017.
 */
@Entity(name = "user_discussion")
public class UserDiscussion extends BaseEntity implements Serializable {

    /**
     * After the object is load from database, actual user object can be added
     * to this class. Note that this field is not persisted.
     */
    @Transient
    private IDiscussionUser user;

    /**
     * Id of the user.
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * Discussion he can access.
     */
    @ManyToOne
    private Discussion discussion;

    public UserDiscussion() {
    }

    public UserDiscussion(Long userId, Discussion discussion) {
        this.userId = userId;
        this.discussion = discussion;
    }

    public IDiscussionUser getUser() {
        return user;
    }

    public void setUser(IDiscussionUser user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }

    @Override
    public String toString() {
        return "UserDiscussion{" +
                "user=" + user +
                ", userId=" + userId +
                ", discussion=" + discussion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserDiscussion that = (UserDiscussion) o;

        if (!userId.equals(that.userId)) return false;
        return discussion.equals(that.discussion);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + discussion.hashCode();
        return result;
    }
}
