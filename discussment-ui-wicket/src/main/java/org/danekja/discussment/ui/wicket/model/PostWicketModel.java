package org.danekja.discussment.ui.wicket.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.domain.Post;

/**
 * Created by Martin Bláha on 23.01.17.
 */
public class PostWicketModel extends LoadableDetachableModel<List<Post>> {

    private IModel<Post> post;

    public PostWicketModel(IModel<Post> post) {
        this.post = post;
    }

    @Override
    protected List<Post> load() {
        return createList(post.getObject(), new ArrayList<Post>());
    }

    private List<Post> createList(Post postsModel, List<Post> posts) {

        posts.add(postsModel);

        for (Post post : postsModel.getReplies()) {
            createList(post, posts);
        }

        return posts;
    }


}
