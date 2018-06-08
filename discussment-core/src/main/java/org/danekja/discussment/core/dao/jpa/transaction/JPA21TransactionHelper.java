package org.danekja.discussment.core.dao.jpa.transaction;

import org.danekja.discussment.core.dao.transaction.TransactionHelper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Helper using JPA 2.1 to check whether there is an active transaction.
 * <p>
 * Date: 04.05.2018
 *
 * @author Pavel Fidransky [jsem@pavelfidransky.cz]
 */
public class JPA21TransactionHelper implements TransactionHelper {

    @PersistenceContext
    protected EntityManager em;

    public JPA21TransactionHelper(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean isJoinedToTransaction() {
        return em.isJoinedToTransaction();
    }
}
