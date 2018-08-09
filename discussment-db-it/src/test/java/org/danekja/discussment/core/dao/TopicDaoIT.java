package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.Category;
import org.danekja.discussment.core.domain.Topic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class TopicDaoIT extends GenericDaoIT<Long, Topic> {

    private final TopicDao topicDao;

    @Autowired
    public TopicDaoIT(TopicDao dao) {
        super(dao);
        this.topicDao = dao;
    }

    @Override
    protected Topic newTestInstance() {
        return new Topic("TopicDaoITInstance", "Some description");
    }

    @Override
    protected Long getTestIdForSearch() {
        return TestData.TEST_TOPIC_1;
    }

    @Override
    protected Long getTestIdForSearch_notFound() {
        return TestData.NOT_FOUND;
    }

    @Override
    protected Long getTestIdForRemove() {
        return TestData.TEST_TOPIC_3;
    }

    @Override
    protected String getSearchResultTestValue(Topic item) {
        return item.getName();
    }

    @Test
    void getTopicsByCategory() {
        List<Topic> found = topicDao.getTopicsByCategory(new Category(TestData.TEST_CATEGORY_1));
        internalTestSearchResults(TestData.TEST_TOPICS_CATEGORY_1, found);

        found = topicDao.getTopicsByCategory(new Category(TestData.TEST_CATEGORY_2));
        internalTestSearchResults(TestData.TEST_TOPICS_CATEGORY_2, found);

        found = topicDao.getTopicsByCategory(new Category(TestData.TEST_CATEGORY_3));
        internalTestSearchResults(TestData.TEST_TOPICS_CATEGORY_3, found);
    }
}