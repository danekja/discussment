package cz.zcu.fav.kiv.discussion.core.model;

import java.io.Serializable;

/**
 * Created by Martin Bláha on 04.01.17.
 */
public class UserModel implements Serializable {

    private long id;
    private String username;
    private String name;
    private String lastname;


    private PermissionModel permission;

    public UserModel() {
    }

    public UserModel(String username, String name, String lastname) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public PermissionModel getPermission() {
        return permission;
    }

    public void setPermission(PermissionModel permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object object)
    {
        boolean equel = false;

        if (object != null && object instanceof UserModel)
        {
            equel = this.username == ((UserModel) object).username;
        }

        return equel;
    }

}
