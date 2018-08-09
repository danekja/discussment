package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.domain.UserPostReputation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserPostReputationDaoIT extends GenericDaoIT<Long, UserPostReputation> {

    private final UserPostReputationDao userPostReputationDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    public UserPostReputationDaoIT(UserPostReputationDao dao) {
        super(dao);
        this.userPostReputationDao = dao;
    }

    @Override
    protected UserPostReputation newTestInstance() {
        Post testPost = postDao.getById(TestData.TEST_POST_1);
        return new UserPostReputation(TestData.TEST_USER_1, testPost, true);
    }

    @Override
    protected Long getTestIdForSearch() {
        return TestData.TEST_USER_POST_REPUTATION;
    }

    @Override
    protected Long getTestIdForSearch_notFound() {
        return TestData.NOT_FOUND;
    }

    @Override
    protected Long getTestIdForRemove() {
        return TestData.TEST_USER_POST_REPUTATION;
    }

    @Override
    protected String getSearchResultTestValue(UserPostReputation item) {
        return item.getUserId() + ":" + item.getPost().getId();
    }

    @Test
    void getForUser() {
        UserPostReputation reputation = userPostReputationDao.getForUser(new UserMock(TestData.TEST_USER_1), new Post(TestData.TEST_POST_2));
        assertNotNull(reputation);
        assertTrue(reputation.getLiked());
        assertEquals(TestData.TEST_USER_1, reputation.getUserId());
        assertEquals(TestData.TEST_POST_2, reputation.getPost().getId());

        reputation = userPostReputationDao.getForUser(new UserMock(TestData.TEST_USER_2), new Post(TestData.TEST_POST_2));
        assertFalse(reputation.getLiked());
        assertEquals(TestData.TEST_USER_2, reputation.getUserId());
        assertEquals(TestData.TEST_POST_2, reputation.getPost().getId());
    }

    private static final class UserMock implements IDiscussionUser {

        private final String value;

        public UserMock(String value) {
            this.value = value;
        }

        @Override
        public String getDiscussionUserId() {
            return value;
        }

        @Override
        public String getDisplayName() {
            return value;
        }
    }
}