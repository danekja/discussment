package cz.zcu.fav.kiv.discussion.core.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity.GET_CATEGORIES;

/**
 * Created by Martin Bláha on 19.01.17.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = GET_CATEGORIES, query = "SELECT c FROM CategoryEntity c WHERE id != 0"),
})
public class CategoryEntity extends BaseEntity {

    public static final String GET_CATEGORIES = "FileEntity.getCategories";

    private String name;

    @OneToMany(mappedBy = "category", orphanRemoval = true)
    private List<TopicEntity> topics = new ArrayList<TopicEntity>();

    public CategoryEntity() {}

    public CategoryEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TopicEntity> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicEntity> topics) {
        this.topics = topics;
    }
}
