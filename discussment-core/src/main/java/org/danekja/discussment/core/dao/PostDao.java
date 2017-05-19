package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.Discussion;
import org.danekja.discussment.core.domain.Post;

import java.util.List;

/**
 * Created by Martin Bláha on 13.05.17.
 */
public interface PostDao extends GenericDao<Post> {
    List<Post> getPostsByDiscussion(Discussion discussion);
}
