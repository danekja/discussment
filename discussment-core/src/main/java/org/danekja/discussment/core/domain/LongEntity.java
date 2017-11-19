package org.danekja.discussment.core.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * The class is a base entity for domain objects with Long PK.
 *
 * @author Jakub Danek
 */
@MappedSuperclass
public abstract class LongEntity extends BaseEntity<Long> {

    @Id
    @GeneratedValue
    @Override
    public Long getId() {
        return super.getId();
    }

}
