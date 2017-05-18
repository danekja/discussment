package org.danekja.discussment.core.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.danekja.discussment.core.domain.User.GET_BY_USERNAME;
import static org.danekja.discussment.core.domain.User.GET_USERS;

/**
 * Created by Martin Bláha on 04.01.17.
 */

//@SuppressWarnings("serial")
@Entity
@NamedQueries({
    @NamedQuery(name = GET_BY_USERNAME, query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = GET_USERS, query = "SELECT u FROM User u")
})
public class User extends BaseEntity implements Serializable {

    public static final String GET_BY_USERNAME = "User.getByUsername";
    public static final String GET_USERS = "User.getUsers";

    @Column(unique=true)
    private String username;
    private String name;
    private String lastname;

    @OneToOne(orphanRemoval = true)
    private Permission permissions;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_discussion",
            joinColumns = {
                    @JoinColumn(
                            name = "user_id",
                            referencedColumnName = "id"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "discussion_id",
                            referencedColumnName = "id"
                    )
            }
    )
    private List<Discussion> accessListToDiscussion = new ArrayList<Discussion>();

    public User() {}

    public User(String username, String name, String lastname) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
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

        if (discussion.getPass() == null || getPermissions().isReadPrivateDiscussion() || discussion.getUserAccessList().contains(this)) {
            return true;
        }
        return false;
    }

    public List<Discussion> getAccessListToDiscussion() {
        return accessListToDiscussion;
    }

    public void setAccessListToDiscussion(List<Discussion> accessListToDiscussion) {
        this.accessListToDiscussion = accessListToDiscussion;
    }

    public Permission getPermissions() {
        return permissions;
    }

    public void setPermissions(Permission permissions) {
        this.permissions = permissions;
    }
}
