package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.accesscontrol.domain.IDiscussionUser;
import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PostDaoIT extends GenericDaoIT<Long, Post> {

    protected final PostDao postDao;

    @Autowired
    public PostDaoIT(PostDao dao) {
        super(dao);
        this.postDao = dao;
    }

    @Override
    protected Post newTestInstance() {
        return new Post(new IDiscussionUser() {
            @Override
            public String getDiscussionUserId() {
                return "testuser";
            }

            @Override
            public String getDisplayName() {
                return "testuser";
            }
        }, "PostDaoIT Test Post");
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
    protected String getSearchResultTestValue(Post item) {
        return item.getText();
    }

    @Test
    void getPostsByDiscussion() {
        Discussion d = new Discussion(TestData.TEST_DISCUSSION_1, "Test Post Discussion 1");
        List<Post> posts = postDao.getPostsByDiscussion(d);
        internalTestSearchResults(TestData.TEST_POSTS_DISCUSSION_1, posts);

        d = new Discussion(TestData.TEST_DISCUSSION_2, "Test Post Discussion 2");
        posts = postDao.getPostsByDiscussion(d);
        internalTestSearchResults(TestData.TEST_POSTS_DISCUSSION_2, posts);
    }

    @Test
    void getBasePostsByDiscussion() {
        Discussion d = new Discussion(TestData.TEST_DISCUSSION_1, "Test Post Discussion 1");
        List<Post> posts = postDao.getBasePostsByDiscussion(d);
        internalTestSearchResults(TestData.TEST_BASE_POSTS_DISCUSSION_1, posts);

        d = new Discussion(TestData.TEST_DISCUSSION_2, "Test Post Discussion 2");
        posts = postDao.getBasePostsByDiscussion(d);
        internalTestSearchResults(TestData.TEST_BASE_POSTS_DISCUSSION_2, posts);
    }

    @Test
    void getRepliesForPost() {
        List<Post> posts = postDao.getRepliesForPost(new Post(TestData.TEST_POST_1));
        internalTestSearchResults(TestData.TEST_POST_1_REPLIES, posts);

        posts = postDao.getRepliesForPost(new Post(TestData.TEST_POST_2));
        internalTestSearchResults(TestData.TEST_POST_2_REPLIES, posts);

        posts = postDao.getRepliesForPost(new Post(TestData.TEST_POST_3));
        internalTestSearchResults(TestData.TEST_POST_3_REPLIES, posts);
    }

    @Test
    void getLastPost() {
        Discussion d = new Discussion(TestData.TEST_DISCUSSION_1, "Test Post Discussion 1");
        Post post = postDao.getLastPost(d);
        internalTestSearchResults(TestData.TEST_LAST_POST_DISCUSSION_1, Collections.singletonList(post));

        d = new Discussion(TestData.TEST_DISCUSSION_2, "Test Post Discussion 2");
        post = postDao.getLastPost(d);
        internalTestSearchResults(TestData.TEST_LAST_POST_DISCUSSION_2, Collections.singletonList(post));
    }

    @Test
    void getNumberOfPosts() {
        Discussion d = new Discussion(TestData.TEST_DISCUSSION_1, "Test Post Discussion 1");
        long count = postDao.getNumberOfPosts(d);
        assertEquals(TestData.TEST_POSTS_DISCUSSION_1.length, count);


        d = new Discussion(TestData.TEST_DISCUSSION_2, "Test Post Discussion 2");
        count = postDao.getNumberOfPosts(d);
        assertEquals(TestData.TEST_POSTS_DISCUSSION_2.length, count);


    }

    @Test
    void getNumbersOfPosts() {
        Map<Long, Long> res = postDao.getNumbersOfPosts(Arrays.asList(TestData.TEST_DISCUSSION_1, TestData.TEST_DISCUSSION_2, TestData.TEST_DISCUSSION_3));

        assertEquals(Long.valueOf(TestData.TEST_POSTS_DISCUSSION_1.length), res.get(TestData.TEST_DISCUSSION_1));
        assertEquals(Long.valueOf(TestData.TEST_POSTS_DISCUSSION_2.length), res.get(TestData.TEST_DISCUSSION_2));
        assertNull(res.get(TestData.TEST_DISCUSSION_3));
    }
}