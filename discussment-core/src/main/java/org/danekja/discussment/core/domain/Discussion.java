package org.danekja.discussment.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.danekja.discussment.core.domain.Discussion.GET_DISCUSSIONS_BY_TOPIC_ID;

/**
 * Created by Martin Bláha on 19.01.17.
 *
 * The class represents one discussion in the discussion.
 */

@Entity
@Table(name = "discussment_discussion")
@NamedQueries({
        @NamedQuery(name = GET_DISCUSSIONS_BY_TOPIC_ID,
                query = "SELECT d FROM Discussion d WHERE d.topic.id = :topicId")
})
public class Discussion extends LongEntity implements Serializable {

    /**
     * The constant contains name of query for getting discussions by topic id
     */
    public static final String GET_DISCUSSIONS_BY_TOPIC_ID = "Discussion.getBytopicId";

    /**
     * The constant contains index reserved for default discussion
     */
    public static final long DEFAULT_DISCUSSION_ID = 1;

    /**
     * Name of the discussion
     */
    private String name;

    /**
     * Password of the discussion. If the discussion has not a password, is null.
     */
    private String pass;

    /**
     * List contains posts in the discussion. If the discussion is removed, the posts are removed too.
     */
    private List<Post> posts = new ArrayList<>();

    /**
     * Topic in which the discussion is.
     */
    private Topic topic;

    public Discussion() {}

    public Discussion(String name) {
        this.name = name;
        this.pass = null;
    }

    public Discussion(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public Discussion(Long id, String name) {
        super(id);
        this.name = name;
    }

    @ManyToOne
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
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

    @OneToMany(mappedBy = "discussion", orphanRemoval = true)
    public List<Post> getPosts() {
        return posts;
    }

    protected void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "Discussion{" +
                "name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", topic=" + topic +
                '}';
    }
}
