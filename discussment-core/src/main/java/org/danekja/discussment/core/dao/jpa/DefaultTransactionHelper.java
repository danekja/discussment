package org.danekja.discussment.core.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.danekja.discussment.core.dao.ITransactionHelper;

/**
 * Helper using JPA 2.1 to check whether there is an active transaction.
 * <p>
 * Date: 04.05.2018
 *
 * @author Pavel Fidransky [jsem@pavelfidransky.cz]
 */
public class DefaultTransactionHelper implements ITransactionHelper {

    @PersistenceContext
    protected EntityManager em;

    public DefaultTransactionHelper(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean isJoinedToTransaction() {
        return em.isJoinedToTransaction();
    }
}
