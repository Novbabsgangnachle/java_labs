package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a history of transactions for all accounts.
 */
public class TransactionsHistory {

    /**
     * The list of all transactions.
     */
    private final List<Transaction> transactions = new ArrayList<>();

    /**
     * Retrieves all transactions associated with a specific account.
     *
     * @param accountId the ID of the account
     * @return a list of transactions linked to the specified account
     */
    public List<Transaction> getTransactionsByAccountId(long accountId) {
        List<Transaction> resultTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAccountId() == accountId) {
                resultTransactions.add(transaction);
            }
        }
        return resultTransactions;
    }

    /**
     * Adds a new transaction to the history.
     *
     * @param transaction the transaction to be added
     */
    public void add(Transaction transaction) {
        transactions.add(transaction);
    }
}