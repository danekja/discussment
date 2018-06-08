package org.danekja.discussment.core.dao.transaction;

/**
 * Helper class used to check whether there is an active transaction.
 * <p>
 * Date: 04.05.2018
 *
 * @author Pavel Fidransky [jsem@pavelfidransky.cz]
 */
public interface TransactionHelper {
    /**
     * @return true when there is an active transaction, otherwise false
     */
    boolean isJoinedToTransaction();

}
