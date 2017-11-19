package org.danekja.discussment.core.domain;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by Martin Bláha on 04.01.17.
 *
 * The class is a base entity for domain objects.
 */
public abstract class BaseEntity<PK extends Serializable> implements Serializable {

    /**
     * Id of a object.
     */
    private PK id;

    @Transient
    public boolean isNew() {
        return id == null;
    }

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        BaseEntity other = (BaseEntity) obj;

        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }

        return true;
    }

}
