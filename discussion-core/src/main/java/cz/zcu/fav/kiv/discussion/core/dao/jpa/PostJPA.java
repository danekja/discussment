package cz.zcu.fav.kiv.discussion.core.dao.jpa;

import cz.zcu.fav.kiv.discussion.core.dao.IPostDao;
import cz.zcu.fav.kiv.discussion.core.entity.PostEntity;

/**
 * Created by Martin Bláha on 19.01.17.
 */
public class PostJPA extends GenericJPA<PostEntity> implements IPostDao {

    public PostJPA() {
        super(PostEntity.class);
    }

}
