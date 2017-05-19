package org.danekja.discussment.ui.wicket.model;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.service.IPostService;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class ThreadWicketModel extends LoadableDetachableModel<List<Post>> {

    private IPostService postService;
    private IModel<Discussion> discussionModel;

    public ThreadWicketModel(IPostService postService, IModel<Discussion> discussionModel) {
        this.postService = postService;
        this.discussionModel = discussionModel;
    }

    protected List<Post> load() {
        return postService.listPostHierarchy(discussionModel.getObject());
    }

}
