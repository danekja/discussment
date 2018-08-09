package org.danekja.discussment.core.dao;

import org.danekja.discussment.core.domain.BaseEntity;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Base test class for DAO integration testing. Contains logic for
 * testing basic CRUD operations with minimum configuration required.
 *
 * @param <PK> type of primary key
 * @param <T>  type of entity the tested DAO manages
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
abstract class GenericDaoIT<PK extends Serializable, T extends BaseEntity<PK>> {

    @Autowired
    protected Flyway flyway;

    protected final GenericDao<PK, T> dao;

    public GenericDaoIT(GenericDao<PK, T> dao) {
        this.dao = dao;
    }

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void save() {
        T object = newTestInstance();

        T newInstance = dao.save(object);

        assertEquals(object, newInstance);
    }

    @Test
    void getById() {
        T found = dao.getById(getTestIdForSearch());
        assertNotNull(found);
    }

    @Test
    void getById_fail() {
        T found = dao.getById(getTestIdForSearch_notFound());
        assertNull(found);
    }

    @Test
    void remove() {
        T toDelete = dao.getById(getTestIdForRemove());
        assertNotNull(toDelete);

        dao.remove(toDelete);

        toDelete = dao.getById(getTestIdForRemove());
        assertNull(toDelete);
    }

    /**
     * @return new, unsaved, instance for testing purposes
     */
    protected abstract T newTestInstance();

    /**
     * @return ID for read operations testing - must exist in the test database
     */
    protected abstract PK getTestIdForSearch();

    /**
     * @return ID for read operations testing - mustnt exist in the test data
     */
    protected abstract PK getTestIdForSearch_notFound();

    /**
     * @return ID for remove operation testing - must exist in the test database
     */
    protected abstract PK getTestIdForRemove();

    /**
     * @param item item returned by the search query
     * @return value to be tested inside the {@link #internalTestSearchResults(String[], List)} method
     */
    protected abstract String getSearchResultTestValue(T item);

    /**
     * This method can be used for easy testing of various query methods
     *
     * @param expected array of expected values to be compared with the {@link #getSearchResultTestValue(BaseEntity)} return
     * @param found    list of found entity instances during search query
     */
    protected void internalTestSearchResults(String[] expected, List<T> found) {
        assertEquals(expected.length, found.size());

        //check that the returned list contains only expected values (all of them)
        final Map<String, Boolean> checker = new HashMap<>(expected.length);
        for (String name : expected) {
            checker.put(name, Boolean.FALSE);
        }
        found.forEach(item -> checker.put(getSearchResultTestValue(item), Boolean.TRUE));

        assertEquals(expected.length, checker.size());
        checker.forEach((name, isPresent) -> assertTrue(isPresent));
    }
}