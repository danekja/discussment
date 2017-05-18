package org.danekja.discussment.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.danekja.discussment.core.domain.Category.GET_CATEGORIES;

/**
 * Created by Martin Bláha on 19.01.17.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = GET_CATEGORIES, query = "SELECT c FROM Category c WHERE id != 0"),
})
public class Category extends BaseEntity implements Serializable {

    public static final String GET_CATEGORIES = "FileEntity.getCategories";

    public static final int WITHOUT_CATEGORY = 0;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category", cascade = CascadeType.ALL)
    private List<Topic> topics = new ArrayList<Topic>();

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}
