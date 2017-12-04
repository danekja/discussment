package org.danekja.discussment.core.service;

import org.danekja.discussment.core.domain.Post;
import org.danekja.discussment.core.mock.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * This test case is using services with accesscontrol component.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    private static User testUser;

    @BeforeClass
    public static void setUpGlobal() throws Exception {
        testUser = new User(-100L, "PMS Test User");
    }

    @Before
    public void setUp() {
        // todo: configure permissions
    }

    /**
     * Create post, add some replies, delete root post (with permissions set) and verify
     * that the whole chain has been deleted.
     */
    @Test
    @Ignore
    public void testRemovePost() {
        Post rootPost = new Post(testUser, "Root post");
        // todo: implement this and test new services
    }
}
