package org.danekja.discussment.core.domain;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.danekja.discussment.core.domain.Discussion.GET_BY_NAME;
import static org.danekja.discussment.core.domain.Discussion.GET_DISCUSSIONS_BY_TOPIC_ID;

/**
 * Created by Martin Bláha on 19.01.17.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = GET_BY_NAME,
                query = "SELECT d FROM Discussion d WHERE d.name = :name"),
        @NamedQuery(name = GET_DISCUSSIONS_BY_TOPIC_ID,
                query = "SELECT d FROM Discussion d WHERE d.topic.id = :topicId")
})
public class Discussion extends BaseEntity implements Serializable {

    public static final String GET_BY_NAME = "Discussion.getByUsername";
    public static final String GET_DISCUSSIONS_BY_TOPIC_ID = "Discussion.getBytopicId";

    private String name;
    private String pass;


    @OneToMany(mappedBy = "discussion", orphanRemoval = true)
    private List<Post> posts = new ArrayList<Post>();

    @ManyToMany(mappedBy = "accessListToDiscussion")
    private List<User> userAccessList = new ArrayList<User>();

    @ManyToOne
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

    public List<Post> getPosts() {
        return posts;
    }

    public List<User> getUserAccessList() {
        return userAccessList;
    }

    public void setUserAccessList(List<User> accessList) {
        this.userAccessList = accessList;
    }

    public void addPost(Post post) {
        posts.add(post);
        if (post.getDiscussion() != this) {
            post.setDiscussion(this);
        }
    }

    public int getNumberOfPosts() {
        int numberOfPosts = 0;

        for (Post post: posts) {
            numberOfPosts += post.getNumberOfReplies();
        }

        return numberOfPosts;
    }

    public Post getLastPost() {
        Post lastPost = null;

        for (Post post: posts) {
            if (lastPost == null) {
                lastPost = post;
            }

            Post a = lastPost.getLastPost();
            Post b = post.getLastPost();

            if (b.getCreated().compareTo(a.getCreated()) > 0) {
                lastPost = b;
            } else {
                lastPost = a;
            }

        }

        return lastPost;
    }

}
