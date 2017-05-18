package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.domain.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Bláha on 23.01.17.
 */
public class PostWicketModel extends LoadableDetachableModel {

    private Post post;

    public PostWicketModel(Post post) {
        this.post = post;
    }

    @Override
    protected Object load() {
        List<Post> posts = new ArrayList<Post>();
        createList(post, posts);
        return posts;
    }

    private static void createList(Post postsModel, List<Post> posts) {

        posts.add(postsModel);

        for (Post post : postsModel.getReplies()) {
            createList(post, posts);
        }
    }


}
