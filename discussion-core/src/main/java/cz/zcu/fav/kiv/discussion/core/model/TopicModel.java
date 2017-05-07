package cz.zcu.fav.kiv.discussion.core.model;

import cz.zcu.fav.kiv.discussion.core.entity.CategoryEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Bláha on 28.01.17.
 */
public class TopicModel implements Serializable {


    private long id;
    private String name;
    private String description;

    private CategoryEntity category;


    private List<DiscussionModel> discussions = new ArrayList<DiscussionModel>();

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

    public List<DiscussionModel> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<DiscussionModel> discussions) {
        this.discussions = discussions;
    }

    public int getNumberOfThreads() {
        return discussions.size();
    }

    public int getNumberOfPosts() {
        int numberOfPosts = 0;

        for (DiscussionModel discussion: discussions) {
            numberOfPosts += discussion.getNumberOfPosts();
        }

        return numberOfPosts;
    }

}
