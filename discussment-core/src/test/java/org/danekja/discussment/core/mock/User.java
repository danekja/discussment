package org.danekja.discussment.core.mock;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.LongEntity;

import javax.persistence.*;

import static org.danekja.discussment.core.mock.User.GET_BY_USERNAME;
import static org.danekja.discussment.core.mock.User.GET_USERS;

/**
 * Created by Martin Bláha on 04.01.17.
 *
 * The class represents a user in the discussion.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = GET_BY_USERNAME, query = "SELECT u FROM User u WHERE u.username = :username"),
        @NamedQuery(name = GET_USERS, query = "SELECT u FROM User u")
})
public class User extends LongEntity implements IDiscussionUser {

    /**
     * The constant contains name of query for getting an user by username
     */
    public static final String GET_BY_USERNAME = "User.getByUsername";

    /**
     * The constant contains name of query for getting all users in a database
     */
    public static final String GET_USERS = "User.getUsers";

    /**
     * Username of the user. User name must be unique.
     */
    private String username;

    /**
     * Name of the user.
     */
    private String name;

    /**
     * Lastname of the user.
     */
    private String lastname;

    /**
     * List contains the discussions which the user has access.
     */


    public User() {}

    public User(String username, String name, String lastname) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
    }

    public User(Long id, String username) {
        super(id);
        this.username = username;
    }

    @Override
    @Transient
    public String getDiscussionUserId() {
        return getId() != null ? getId().toString() : null;
    }

    @Transient
    public String getDisplayName() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    @Column(unique=true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isAccessToDiscussion(Discussion discussion) {

        return discussion.getPass() == null;
    }

//    @OneToMany(mappedBy = "userAccessList", targetEntity = Discussion.class)
//    public List<UserDiscussion> getAccessListToDiscussion() {
//        return accessListToDiscussion;
//    }
//
//    public void setAccessListToDiscussion(List<UserDiscussion> accessListToDiscussion) {
//        this.accessListToDiscussion = accessListToDiscussion;
//    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}