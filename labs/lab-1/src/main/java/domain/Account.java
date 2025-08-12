package domain;

import java.math.BigDecimal;

/**
 * Represents a bank account with an ID and a balance.
 */
public class Account {

    /**
     * The unique identifier of the account.
     */
    private final long id;

    /**
     * The current balance of the account.
     */
    private BigDecimal balance;

    /**
     * Constructs an Account with the specified ID and initializes the balance to 0.
     *
     * @param id the unique identifier of the account
     */
    public Account(long id) {
        this.balance = new BigDecimal(0);
        this.id = id;
    }

    /**
     * Returns the current balance of the account.
     *
     * @return the account balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets a new balance for the account.
     *
     * @param balance the new balance value
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Returns the identifier of the account.
     *
     * @return the account ID
     */
    public long getId() {
        return id;
    }
}