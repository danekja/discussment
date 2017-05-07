package cz.zcu.fav.kiv.discussion.gui.model;

import cz.zcu.fav.kiv.discussion.core.model.PostModel;
import cz.zcu.fav.kiv.discussion.core.service.DiscussionService;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class ThreadWicketModel implements IModel<List<PostModel>> {


    private long discussionId;

    public ThreadWicketModel(long discussionId) {

        this.discussionId = discussionId;

    }

    public long getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(long discussionId) {
        this.discussionId = discussionId;
    }

    public void detach() {
    }

    public List<PostModel> getObject() {
        return DiscussionService.getDiscussionById(discussionId).getPosts();
    }

    public void setObject(List<PostModel> object) {
    }
}
