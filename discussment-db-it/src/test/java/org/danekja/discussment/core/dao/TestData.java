package org.danekja.discussment.core.dao;

class TestData {


    static final Long NOT_FOUND = -666L;

    static final Long TEST_CATEGORY_1 = -1L;
    static final String TEST_CATEGORY_NAME_1 = "1 Category";

    static final Long TEST_CATEGORY_2 = -2L;
    static final String TEST_CATEGORY_NAME_2 = "2 Category";

    static final Long TEST_CATEGORY_3 = -3L;
    static final String TEST_CATEGORY_NAME_3 = "3 Category";

    static final Long TEST_TOPIC_1 = -1L;
    static final String TEST_TOPIC_NAME_1 = "1 Topic";

    static final Long TEST_TOPIC_2 = -2L;
    static final String TEST_TOPIC_NAME_2 = "2 Topic";

    static final Long TEST_TOPIC_3 = -3L;
    static final String TEST_TOPIC_NAME_3 = "3 Topic";

    static final Long TEST_DISCUSSION_1 = -1L;
    static final String TEST_DISCUSSION_NAME_1 = "1 Discussion";

    static final Long TEST_DISCUSSION_2 = -2L;
    static final String TEST_DISCUSSION_NAME_2 = "2 Discussion";

    static final Long TEST_DISCUSSION_3 = -3L;
    static final String TEST_DISCUSSION_NAME_3 = "3 Discussion";

    static final Long TEST_POST_1 = -1L;
    static final String TEST_POST_NAME_1 = "1 Post";

    static final Long TEST_POST_2 = -2L;
    static final String TEST_POST_NAME_2 = "2 Post";

    static final Long TEST_POST_3 = -3L;
    static final String TEST_POST_NAME_3 = "3 Post";

    static final Long TEST_POST_11 = -4L;
    static final String TEST_POST_NAME_11 = "11 Post";

    static final Long TEST_POST_12 = -5L;
    static final String TEST_POST_NAME_12 = "12 Post";

    static final Long TEST_POST_21 = -6L;
    static final String TEST_POST_NAME_21 = "21 Post";

    static final Long TEST_POST_111 = -7L;
    static final String TEST_POST_NAME_111 = "111 Post";

    static final String TEST_USER_1 = "testuser1";
    static final String TEST_USER_2 = "testuser2";

    static final Long TEST_USER_POST_REPUTATION = -1L;


    static final String[] TEST_CATEGORIES = {
            TEST_CATEGORY_NAME_1,
            TEST_CATEGORY_NAME_2,
            TEST_CATEGORY_NAME_3
    };

    static final String[] TEST_TOPICS_CATEGORY_1 = {
            TEST_TOPIC_NAME_1,
            TEST_TOPIC_NAME_2
    };

    static final String[] TEST_TOPICS_CATEGORY_2 = {
            TEST_TOPIC_NAME_3
    };

    static final String[] TEST_TOPICS_CATEGORY_3 = new String[0];

    static final String[] TEST_DISCUSSIONS_TOPIC_1 = {
            TEST_DISCUSSION_NAME_1,
            TEST_DISCUSSION_NAME_2
    };

    static final String[] TEST_DISCUSSIONS_TOPIC_2 = {
            TEST_DISCUSSION_NAME_3,
    };

    static final String[] TEST_DISCUSSIONS_TOPIC_3 = new String[0];

    static final String[] TEST_POSTS_DISCUSSION_1 = {
            TEST_POST_NAME_1,
            TEST_POST_NAME_2,
            TEST_POST_NAME_11,
            TEST_POST_NAME_21,
            TEST_POST_NAME_111,
            TEST_POST_NAME_12
    };

    static final String[] TEST_POSTS_DISCUSSION_2 = {
            TEST_POST_NAME_3
    };

    static final String[] TEST_BASE_POSTS_DISCUSSION_1 = {
            TEST_POST_NAME_1,
            TEST_POST_NAME_2
    };

    static final String[] TEST_BASE_POSTS_DISCUSSION_2 = {
            TEST_POST_NAME_3
    };

    static final String[] TEST_POST_1_REPLIES = {
            TEST_POST_NAME_11,
            TEST_POST_NAME_12
    };

    static final String[] TEST_POST_2_REPLIES = {
            TEST_POST_NAME_21
    };

    static final String[] TEST_POST_3_REPLIES = {
    };

    static final String[] TEST_LAST_POST_DISCUSSION_1 = {
            TEST_POST_NAME_21
    };

    static final String[] TEST_LAST_POST_DISCUSSION_2 = {
            TEST_POST_NAME_3
    };
}
