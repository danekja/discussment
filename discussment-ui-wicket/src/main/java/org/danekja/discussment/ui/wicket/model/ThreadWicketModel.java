package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.PostService;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class ThreadWicketModel extends LoadableDetachableModel<List<Post>> {

    private PostService postService;
    private IModel<Discussion> discussionModel;

    public ThreadWicketModel(PostService postService, IModel<Discussion> discussionModel) {
        this.postService = postService;
        this.discussionModel = discussionModel;
    }

    protected List<Post> load() {
        return postService.listPostHierarchy(discussionModel.getObject());
    }

}
