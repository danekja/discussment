package org.danekja.discussment.core.dao.transaction;

/**
 * Implementation of {@link TransactionHelper} which returns a pre-set static value (true or false).
 */
public class StaticTransactionHelper implements TransactionHelper {

    private final boolean value;

    public StaticTransactionHelper(boolean value) {
        this.value = value;
    }

    @Override
    public boolean isJoinedToTransaction() {
        return value;
    }
}
