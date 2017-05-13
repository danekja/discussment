package org.danekja.discussment.ui.wicket.model;

import org.danekja.discussment.core.domain.Post;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Bláha on 23.01.17.
 */
public class PostWicketModel implements IModel<List<Post>> {

    private List<Post> posts;

    public PostWicketModel() {
        posts = new ArrayList<Post>();
    }

    public PostWicketModel(Post post) {
        posts = new ArrayList<Post>();

        createList(post, posts);

    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(Post post) {
        posts = new ArrayList<Post>();
        createList(post, posts);
    }

    public void detach() {
    }

    public List<Post> getObject() {
        return posts;
    }

    private static void createList(Post postsModel, List<Post> posts) {

        posts.add(postsModel);

        for (Post post: postsModel.getReplies()) {
            createList(post, posts);
        }
    }

    public void setObject(List<Post> object) {
    }
}
