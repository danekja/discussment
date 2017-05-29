package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Bláha on 23.01.17.
 */
public class PostWicketModel extends LoadableDetachableModel<List<Post>> {

    private IModel<Post> post;

    private PostService postService;

    public PostWicketModel(IModel<Post> post, PostService postService) {
        this.post = post;
        this.postService = postService;
    }

    @Override
    protected List<Post> load() {

        return createList(postService.getPostById(post.getObject().getId()), new ArrayList<Post>());

    }

    private List<Post> createList(Post post, List<Post> posts) {

        if (post == null) {
            return posts;
        }

        posts.add(post);

        for (Post p : post.getReplies()) {
            createList(p, posts);
        }

        return posts;
    }


}
