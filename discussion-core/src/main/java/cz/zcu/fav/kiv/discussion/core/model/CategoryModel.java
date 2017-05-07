package cz.zcu.fav.kiv.discussion.core.model;

import java.io.Serializable;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class CategoryModel implements Serializable {

    public static final int WITHOUT_CATEGORY = 0;

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
