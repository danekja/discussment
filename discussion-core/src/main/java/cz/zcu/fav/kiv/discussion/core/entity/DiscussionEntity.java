package cz.zcu.fav.kiv.discussion.core.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity.*;

/**
 * Created by Martin Bláha on 19.01.17.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = GET_BY_NAME,
                query = "SELECT d FROM DiscussionEntity d WHERE d.name = :name"),
        @NamedQuery(name = GET_DISCUSSIONS_BY_TOPIC_ID,
                query = "SELECT d FROM DiscussionEntity d WHERE d.topic.id = :topicId")
})
public class DiscussionEntity extends BaseEntity {

    public static final String GET_BY_NAME = "Discussion.getByUsername";
    public static final String GET_DISCUSSIONS_BY_TOPIC_ID = "Discussion.getBytopicId";

    private String name;
    private String pass;


    @OneToMany(mappedBy = "discussion", orphanRemoval = true)
    private List<PostEntity> posts = new ArrayList<PostEntity>();

    @ManyToMany(mappedBy = "accessListToDiscussion")
    private List<UserEntity> userAccessList = new ArrayList<UserEntity>();


    @ManyToOne
    private TopicEntity topic;

    public DiscussionEntity() {}

    public DiscussionEntity(String name) {
        this.name = name;
        this.pass = null;
    }

    public DiscussionEntity(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public TopicEntity getTopic() {
        return topic;
    }

    public void setTopic(TopicEntity topic) {
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public List<UserEntity> getUserAccessList() {
        return userAccessList;
    }

    public void setUserAccessList(List<UserEntity> accessList) {
        this.userAccessList = accessList;
    }

    public void addPost(PostEntity post) {
        posts.add(post);
        if (post.getDiscussion() != this) {
            post.setDiscussion(this);
        }
    }

}
