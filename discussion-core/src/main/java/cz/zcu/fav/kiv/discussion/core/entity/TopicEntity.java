package cz.zcu.fav.kiv.discussion.core.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static cz.zcu.fav.kiv.discussion.core.entity.TopicEntity.GET_TOPICS_BY_CATEGORY_ID;
import static cz.zcu.fav.kiv.discussion.core.entity.TopicEntity.GET_TOPICS_WITHOUT_CATEGORY;

/**
 * Created by Martin Bláha on 19.01.17.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = GET_TOPICS_BY_CATEGORY_ID, query = "SELECT t FROM TopicEntity t WHERE t.category.id = :categoryId"),
        @NamedQuery(name = GET_TOPICS_WITHOUT_CATEGORY, query = "SELECT t FROM TopicEntity t WHERE t.category.id = 0")
})
public class TopicEntity extends BaseEntity {

    public static final String GET_TOPICS_BY_CATEGORY_ID = "TopicEntity.getTopicsByCategoryId";
    public static final String GET_TOPICS_WITHOUT_CATEGORY = "FileEntity.getTopicsWithoutCategory";

    private String name;
    private String description;

    @ManyToOne
    private CategoryEntity category;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE)
    private List<DiscussionEntity> discussions = new ArrayList<DiscussionEntity>();

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

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public List<DiscussionEntity> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<DiscussionEntity> discussions) {
        this.discussions = discussions;
    }
}
