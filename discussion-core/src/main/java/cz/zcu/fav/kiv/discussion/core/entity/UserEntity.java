package cz.zcu.fav.kiv.discussion.core.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static cz.zcu.fav.kiv.discussion.core.entity.UserEntity.GET_BY_USERNAME;
import static cz.zcu.fav.kiv.discussion.core.entity.UserEntity.GET_USERS;

/**
 * Created by Martin Bláha on 04.01.17.
 */

//@SuppressWarnings("serial")
@Entity
@NamedQueries({
    @NamedQuery(name = GET_BY_USERNAME, query = "SELECT u FROM UserEntity u WHERE u.username = :username"),
    @NamedQuery(name = GET_USERS, query = "SELECT u FROM UserEntity u")
})
public class UserEntity extends BaseEntity implements Serializable {

    public static final String GET_BY_USERNAME = "User.getByUsername";
    public static final String GET_USERS = "User.getUsers";

    @Column(unique=true)
    private String username;
    private String name;
    private String lastname;

    @OneToOne(orphanRemoval = true)
    private PermissionEntity permissions;

    @ManyToMany(cascade = {CascadeType.PERSIST})
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
    private List<DiscussionEntity> accessListToDiscussion = new ArrayList<DiscussionEntity>();

    public UserEntity() {}

    public UserEntity(String username, String name, String lastname) {
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

    public boolean isAccessToDiscussion() {
        //todo
        return true;
    }

    public List<DiscussionEntity> getAccessListToDiscussion() {
        return accessListToDiscussion;
    }

    public void setAccessListToDiscussion(List<DiscussionEntity> accessListToDiscussion) {
        this.accessListToDiscussion = accessListToDiscussion;
    }

    public PermissionEntity getPermissions() {
        return permissions;
    }

    public void setPermissions(PermissionEntity permissions) {
        this.permissions = permissions;
    }
}
