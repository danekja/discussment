package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.accesscontrol.domain.AccessDeniedException;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.danekja.discussment.core.service.DiscussionService;

import java.util.List;

/**
 * Created by Martin Bláha on 25.01.17.
 *
 * The class for getting the discussions by topic id via the discussion service.
 */
public class DiscussionWicketModel extends LoadableDetachableModel<List<Discussion>> {

    private DiscussionService discussionService;

    private IModel<Topic> topicModel;

    /**
     * Constructor for creating a instance of getting the categories.
     *
     * @param topicModel variable contains the topic for getting the categories
     * @param discussionService instance of the discussion service
     */
    public DiscussionWicketModel(IModel<Topic> topicModel, DiscussionService discussionService) {

        this.topicModel = topicModel;
        this.discussionService = discussionService;
    }

    protected List<Discussion> load() {
        try{
            return discussionService.getDiscussionsByTopic(topicModel.getObject());
        } catch (AccessDeniedException e) {
            return  null;
        } catch (NullPointerException e) {
            return null;
        }
    }
}
