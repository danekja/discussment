package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Bláha on 23.01.17.
 *
 * The class for getting the posts by post id via the post service.
 */
public class PostWicketModel extends LoadableDetachableModel<List<Post>> {

    private IModel<Post> post;

    private PostService postService;

    /**
     * Constructor for creating a instance of getting the posts.
     *
     * @param post variable contains the post for getting the posts
     * @param postService instance of the post service
     */
    public PostWicketModel(IModel<Post> post, PostService postService) {
        this.post = post;
        this.postService = postService;
    }

    @Override
    protected List<Post> load() {

        try {
            return createList(postService.getPostById(post.getObject().getId()), new ArrayList<Post>());
        } catch (AccessDeniedException e) {
            return null;
        } catch (NullPointerException e) {
            return null;
        }

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
