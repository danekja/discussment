package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.dao.IPostDao;
import org.danekja.discussment.core.domain.Post;

/**
 * Created by Martin Bláha on 19.01.17.
 */
public class PostJPA extends GenericJPA<Post> implements IPostDao {

    public PostJPA() {
        super(Post.class);
    }

}
