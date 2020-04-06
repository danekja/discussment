package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.Model;
import org.danekja.discussment.core.domain.Post;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PostWicketModelTest {

    /**
     * Test that tree get transformed to list correctly.
     */
    @Test
    public void testLoad() {
        Post root = new Post(1L);
        root.getReplies().add(new Post(2L));
        root.getReplies().get(0).getReplies().add(new Post(3L));
        root.getReplies().get(0).getReplies().add(new Post(4L));
        root.getReplies().add(new Post(5L));
        root.getReplies().add(new Post(6L));
        root.getReplies().get(1).addReply(new Post(7L));

        long[] expectedOrder = new long[] {1, 2, 3, 4, 5, 7, 6};

        List<Post> posts = new PostWicketModel(Model.of(root)).load();
        assertEquals("Wrong number of posts returned!", expectedOrder.length, posts.size());
        int cnt = 0;
        for(Post p : posts) {
            assertEquals("Wrong post id!", expectedOrder[cnt++], p.getId().longValue());
        }
    }
}
