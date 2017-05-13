package cz.zcu.fav.kiv.discussion.gui.model;

import cz.zcu.fav.kiv.discussion.core.entity.PostEntity;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Bláha on 23.01.17.
 */
public class PostWicketModel implements IModel<List<PostEntity>> {

    private List<PostEntity> posts;

    public PostWicketModel() {
        posts = new ArrayList<PostEntity>();
    }

    public PostWicketModel(PostEntity post) {
        posts = new ArrayList<PostEntity>();

        createList(post, posts);

    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(PostEntity post) {
        posts = new ArrayList<PostEntity>();
        createList(post, posts);
    }

    public void detach() {
    }

    public List<PostEntity> getObject() {
        return posts;
    }

    private static void createList(PostEntity postsModel, List<PostEntity> posts) {

        posts.add(postsModel);

        for (PostEntity post: postsModel.getReplies()) {
            createList(post, posts);
        }
    }

    public void setObject(List<PostEntity> object) {
    }
}
