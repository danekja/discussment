package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Topic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class DiscussionDaoIT extends GenericDaoIT<Long, Discussion> {


    protected final DiscussionDao discussionDao;

    @Autowired
    public DiscussionDaoIT(DiscussionDao dao) {
        super(dao);
        this.discussionDao = dao;
    }

    @Override
    protected Discussion newTestInstance() {
        return new Discussion("DiscussionDaoIT Test Discussion");
    }

    @Override
    protected Long getTestIdForSearch() {
        return -1L;
    }

    @Override
    protected Long getTestIdForSearch_notFound() {
        return -666L;
    }

    @Override
    protected Long getTestIdForRemove() {
        return -2L;
    }

    @Override
    protected String getSearchResultTestValue(Discussion item) {
        return item.getName();
    }

    @Test
    void getDiscussionsByTopic() {
        List<Discussion> found = discussionDao.getDiscussionsByTopic(new Topic(TestData.TEST_TOPIC_1));
        internalTestSearchResults(TestData.TEST_DISCUSSIONS_TOPIC_1, found);

        found = discussionDao.getDiscussionsByTopic(new Topic(TestData.TEST_TOPIC_2));
        internalTestSearchResults(TestData.TEST_DISCUSSIONS_TOPIC_2, found);

        found = discussionDao.getDiscussionsByTopic(new Topic(TestData.TEST_TOPIC_3));
        internalTestSearchResults(TestData.TEST_DISCUSSIONS_TOPIC_3, found);
    }
}