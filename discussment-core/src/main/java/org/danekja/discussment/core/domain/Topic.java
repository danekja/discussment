package org.danekja.discussment.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.danekja.discussment.core.domain.Topic.GET_TOPICS_BY_CATEGORY_ID;

/**
 * Created by Martin Bláha on 19.01.17.
 *
 * The class represents a topic in the forum.
 */

@Entity
@Table(name = "discussment_topic")
@NamedQueries({
        @NamedQuery(name = GET_TOPICS_BY_CATEGORY_ID, query = "SELECT t FROM Topic t WHERE t.category.id = :categoryId AND t.id != 1")
})
public class Topic extends LongEntity implements Serializable {

    /**
     * The constant contains name of query for getting topics by category id
     */
    public static final String GET_TOPICS_BY_CATEGORY_ID = "Topic.getTopicsByCategoryId";

    /**
     * The constant contains index reserved for default topic
     */
    public static final long DEFAULT_TOPIC_ID = 1;

    /**
     * Name of the topic
     */
    private String name;

    /**
     * Description of the topic
     */
    private String description;

    /**
     * Category in which the topic is
     */
    private Category category;


    /**
     * List contains discussion in the topic. If the topic is removed, the discussions are removed too.
     */
    private List<Discussion> discussions = new ArrayList<Discussion>();

    public Topic() {
    }

    public Topic(Long id) {
        super(id);
    }

    public Topic(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Topic(Long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE)
    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
    }
}
