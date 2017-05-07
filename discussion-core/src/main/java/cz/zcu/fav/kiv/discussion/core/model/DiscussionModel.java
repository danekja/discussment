package cz.zcu.fav.kiv.discussion.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Bláha on 21.01.17.
 */
public class DiscussionModel implements Serializable {

    private long id;

    private String name;
    private String pass;

    private List<UserModel> accessList = new ArrayList<UserModel>();


    private List<PostModel> posts = new ArrayList<PostModel>();


    private TopicModel topic;


    public DiscussionModel() {}

    public DiscussionModel(String name) {
        this.name = name;
        this.pass = null;
    }

    public DiscussionModel(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(List<PostModel> posts) {
        this.posts = posts;
    }

    public int getNumberOfPosts() {
        int numberOfPosts = 0;

        for (PostModel post: posts) {
            numberOfPosts += post.getNumberOfReplies();
        }

        return numberOfPosts;
    }

    public PostModel getLastPost() {
        PostModel lastPost = null;

        for (PostModel post: posts) {
            if (lastPost == null) {
                lastPost = post;
            }

            PostModel a = lastPost.getLastPost();
            PostModel b = post.getLastPost();

            if (b.getCreated().compareTo(a.getCreated()) > 0) {
                lastPost = b;
            } else {
                lastPost = a;
            }

        }

        return lastPost;
    }

    public List<UserModel> getAccessList() {
        return accessList;
    }

    public void setAccessList(List<UserModel> accessList) {
        this.accessList = accessList;
    }

    public TopicModel getTopic() {
        return topic;
    }

    public void setTopic(TopicModel topic) {
        this.topic = topic;
    }
}
