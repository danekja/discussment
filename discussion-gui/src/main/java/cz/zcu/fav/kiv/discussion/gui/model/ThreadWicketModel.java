package cz.zcu.fav.kiv.discussion.gui.model;

import cz.zcu.fav.kiv.discussion.core.entity.DiscussionEntity;
import cz.zcu.fav.kiv.discussion.core.entity.PostEntity;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 */
public class ThreadWicketModel implements IModel<List<PostEntity>> {


    private DiscussionEntity discussionEntity;

    public ThreadWicketModel(DiscussionEntity discussionEntity) {

        this.discussionEntity = discussionEntity;

    }

    public DiscussionEntity getDiscussion() {
        return discussionEntity;
    }

    public void setDiscussion(DiscussionEntity discussionEntity) {
        this.discussionEntity = discussionEntity;
    }

    public void detach() {
    }

    public List<PostEntity> getObject() {
        return discussionEntity.getPosts();
    }

    public void setObject(List<PostEntity> object) {
    }
}
