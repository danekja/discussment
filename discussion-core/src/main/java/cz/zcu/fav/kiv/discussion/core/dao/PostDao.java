package cz.zcu.fav.kiv.discussion.core.dao;

import cz.zcu.fav.kiv.discussion.core.entity.PostEntity;

/**
 * Created by Martin Bláha on 19.01.17.
 */
public class PostDao extends GenericDao<PostEntity> {

    public PostDao() {
        super(PostEntity.class);
    }

}
