package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.domain.Discussion;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class ThreadWicketModel extends LoadableDetachableModel {


    private IModel<Discussion> discussionModel;

    public ThreadWicketModel(IModel<Discussion> discussionModel) {

        this.discussionModel = discussionModel;

    }

    protected Object load() {
        return discussionModel.getObject().getPosts();
    }

}
