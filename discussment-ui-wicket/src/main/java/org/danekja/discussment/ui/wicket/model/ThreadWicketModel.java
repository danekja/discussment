package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class ThreadWicketModel implements IModel<List<Post>> {


    private Discussion discussion;

    public ThreadWicketModel(Discussion discussion) {

        this.discussion = discussion;

    }

    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion IDiscussion) {
        this.discussion = discussion;
    }

    public void detach() {
    }

    public List<Post> getObject() {
        return discussion.getPosts();
    }

    public void setObject(List<Post> object) {
    }
}
