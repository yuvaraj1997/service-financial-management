package com.yuvaraj.financial.helpers;

import com.yuvaraj.financial.models.db.transaction.TransactionTypeEntity;

/**
 * @author Yuvaraj
 */
public class TransactionUtils {

    private TransactionUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static long formatAmountAccordingToType(String type, Long amount) {
        switch (TransactionTypeEntity.Type.valueOf(type)) {
            case Expenses:
            case Transfers:
            case Bills:
            case Investments:
            case Other:
                amount = -amount;
                break;
            default:
                amount = Math.abs(amount);
                break;
        }

        return amount;
    }
}
