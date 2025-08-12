package domain;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a single transaction with a specific amount, type, date, and associated account.
 */
public class Transaction {

    /**
     * The amount of the transaction.
     */
    private final BigDecimal amount;

    /**
     * The date when the transaction was made.
     */
    private final LocalDate date;

    /**
     * The type of the transaction (deposit or withdrawal).
     */
    private final TransactionType transactionType;

    /**
     * The ID of the account involved in the transaction.
     */
    private final long accountId;

    /**
     * Constructs a transaction with the given amount, type, and account ID.
     * The date is automatically set to the current date.
     *
     * @param amount          the transaction amount
     * @param transactionType the type of the transaction
     * @param accountId       the ID of the account for this transaction
     */
    public Transaction(BigDecimal amount, TransactionType transactionType, long accountId) {
        this.amount = amount;
        this.date = LocalDate.now();
        this.transactionType = transactionType;
        this.accountId = accountId;
    }

    /**
     * Returns the amount of this transaction.
     *
     * @return the transaction amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Returns the date of this transaction.
     *
     * @return the transaction date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the type of this transaction.
     *
     * @return the transaction type
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * Returns the ID of the account associated with this transaction.
     *
     * @return the account ID
     */
    public long getAccountId() {
        return accountId;
    }
}
