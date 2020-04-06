package org.danekja.discussment.ui.wicket.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.danekja.discussment.core.domain.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

/**
 * Model for getting hierarchy of one post as a list.
 *
 * Created by Martin Bláha on 23.01.17.
 */
public class PostWicketModel extends LoadableDetachableModel<List<Post>> {

    private IModel<Post> post;

    /**
     * Constructor for creating a instance of getting the posts.
     *
     * @param post Root post.
     */
    public PostWicketModel(IModel<Post> post) {
        this.post = post;
    }

    @Override
    protected List<Post> load() {

        return createListDfs(post.getObject());

    }

    /**
     * Uses DFS algorithm to transform reply tree to a list of posts.
     *
     * @param post Root posts.
     * @return Reply tree transformed to a list of posts.
     */
    private List<Post> createListDfs(Post post) {
        List<Post> posts = new ArrayList<>();
        Stack<Post> replyStack = new Stack<>();
        replyStack.push(post);

        while(!replyStack.isEmpty()) {
            Post p = replyStack.pop();
            posts.add(p);

            // push replies to the stack in reverse order
            // so that they're popped in correct order
            ListIterator<Post> replyIterator = p.getReplies().listIterator(p.getReplies().size());
            while(replyIterator.hasPrevious()) {
                replyStack.push(replyIterator.previous());
            }
        }

        return posts;
    }
}