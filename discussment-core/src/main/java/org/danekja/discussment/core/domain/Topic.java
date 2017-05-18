package org.danekja.discussment.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.danekja.discussment.core.domain.Topic.GET_TOPICS_BY_CATEGORY_ID;
import static org.danekja.discussment.core.domain.Topic.GET_TOPICS_WITHOUT_CATEGORY;

/**
 * Created by Martin Bláha on 19.01.17.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = GET_TOPICS_BY_CATEGORY_ID, query = "SELECT t FROM Topic t WHERE t.category.id = :categoryId"),
        @NamedQuery(name = GET_TOPICS_WITHOUT_CATEGORY, query = "SELECT t FROM Topic t WHERE t.category.id = 0")
})
public class Topic extends BaseEntity implements Serializable {

    public static final String GET_TOPICS_BY_CATEGORY_ID = "Topic.getTopicsByCategoryId";
    public static final String GET_TOPICS_WITHOUT_CATEGORY = "FileEntity.getTopicsWithoutCategory";

    private String name;
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Category category;


    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE)
    private List<Discussion> discussions = new ArrayList<Discussion>();

    public Topic() {
    }

    public Topic(String name, String description) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
    }

    public int getNumberOfDiscussions() {
        return discussions.size();
    }

    public int getNumberOfPosts() {
        int numberOfPosts = 0;

        for (Discussion discussion: discussions) {
            numberOfPosts += discussion.getNumberOfPosts();
        }

        return numberOfPosts;
    }
}
