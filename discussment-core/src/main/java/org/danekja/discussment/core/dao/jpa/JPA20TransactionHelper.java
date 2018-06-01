package org.danekja.discussment.core.dao.jpa;

import org.danekja.discussment.core.dao.ITransactionHelper;

/**
 * Custom implementation of ITransactionHelper bypassing JPA 2.0 incompatibilities.
 * <p>
 * Date: 24.05.2018
 *
 * @author Pavel Fidransky [jsem@pavelfidransky.cz]
 */
public class JPA20TransactionHelper implements ITransactionHelper {

    @Override
    public boolean isJoinedToTransaction() {
        return true;
    }
}
