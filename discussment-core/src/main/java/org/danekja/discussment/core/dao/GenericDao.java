package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.BaseEntity;

import java.io.Serializable;

/**
 * Created by Martin Bláha on 13.05.17.
 *
 * The interface contains basic methods for accessing a database
 *
 */
public interface GenericDao<PK extends Serializable, T extends BaseEntity<PK>> {

    /**
     * Save an object to the database.
     *
     * @param obj object
     * @return saved object
     */
    T save(T obj);

    /**
     * Get an object in a database based on his id.
     *
     * @param id id
     * @return object object
     */
    T getById(PK id);

    /**
     * Remove the specified object from database.
     *
     * @param obj object to be removed
     */
    void remove(T obj);
}
