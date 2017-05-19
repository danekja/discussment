package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.BaseEntity;

/**
 * Created by Martin Bláha on 13.05.17.
 */
public interface GenericDao<T extends BaseEntity> {
    T save(T obj);

    T getById(Long id);

    void remove(T obj);
}
