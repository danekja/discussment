package org.danekja.discussment.core.dao.jpa.transaction;

import org.danekja.discussment.core.dao.transaction.StaticTransactionHelper;

/**
 * Custom implementation of TransactionHelper bypassing JPA 2.0 incompatibilities.
 * <p>
 * Date: 24.05.2018
 *
 * @author Pavel Fidransky [jsem@pavelfidransky.cz]
 */
public class JPA20TransactionHelper extends StaticTransactionHelper {

    public JPA20TransactionHelper() {
        super(true);
    }

}
