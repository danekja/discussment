package cz.zcu.fav.kiv.discussion.gui.model;

import cz.zcu.fav.kiv.discussion.core.model.PostModel;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Bláha on 23.01.17.
 */
public class PostWicketModel implements IModel<List<PostModel>> {

    private List<PostModel> posts;

    public PostWicketModel() {
        posts = new ArrayList<PostModel>();
    }

    public PostWicketModel(PostModel post) {
        posts = new ArrayList<PostModel>();

        createList(post, posts);

    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(PostModel post) {
        posts = new ArrayList<PostModel>();
        createList(post, posts);
    }

    public void detach() {
    }

    public List<PostModel> getObject() {
        return posts;
    }

    private static void createList(PostModel postsModel, List<PostModel> posts) {

        posts.add(postsModel);

        for (PostModel post: postsModel.getReplies()) {
            createList(post, posts);
        }
    }

    public void setObject(List<PostModel> object) {
    }
}
