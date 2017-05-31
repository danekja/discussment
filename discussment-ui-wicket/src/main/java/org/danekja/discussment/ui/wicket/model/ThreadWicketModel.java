package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostService;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 *
 * The class for getting the posts in the thread by discussion via the discussion service.
 */
public class ThreadWicketModel extends LoadableDetachableModel<List<Post>> {

    private PostService postService;
    private IModel<Discussion> discussionModel;

    /**
     * Constructor for creating a instance of getting the posts in the thread.
     *
     * @param postService instance of the post service
     * @param discussionModel variable contains the discussion for getting the posts
     */
    public ThreadWicketModel(PostService postService, IModel<Discussion> discussionModel) {
        this.postService = postService;
        this.discussionModel = discussionModel;
    }

    protected List<Post> load() {

        return postService.listPostHierarchy(discussionModel.getObject());
    }

}
