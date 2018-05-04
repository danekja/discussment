package org.danekja.discussment.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.danekja.discussment.core.domain.Category.GET_CATEGORIES;

/**
 * Created by Martin Bláha on 19.01.17.
 *
 * The class represents the category in the forum.
 */

@Entity
@Table(name = "discussment_category")
@NamedQueries({
        @NamedQuery(name = GET_CATEGORIES, query = "SELECT c FROM Category c WHERE id != 1"),
})
public class Category extends LongEntity implements Serializable {

    /**
     * The constant contains name of query for getting categories
     */
    public static final String GET_CATEGORIES = "FileEntity.getCategories";

    /**
     * The constant contains index reserved for default category
     */
    public static final long DEFAULT_CATEGORY_ID = 1;

    /**
     * Name of the category
     */
    private String name;

    /**
     * List contains topics in the category. If the category is removed, the topics are removed too.
     */
    private List<Topic> topics = new ArrayList<Topic>();

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public Category(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return List contains topics in the category. If the category is removed, the topics are removed too.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}
