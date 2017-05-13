package cz.zcu.fav.kiv.discussion.core.dao;

import cz.zcu.fav.kiv.discussion.core.entity.BaseEntity;

/**
 * Created by Martin Bláha on 13.05.17.
 */
public interface IGenericDao<T extends BaseEntity> {
    T save(T obj);

    T getById(Long id);

    void remove(T obj);
}
