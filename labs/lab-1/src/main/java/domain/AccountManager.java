package domain;

import exceptions.InsufficientFundsException;
import exceptions.InvalidFundsAmountException;
import exceptions.AccountNotFoundException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages accounts and their transactions.
 */
public class AccountManager {

    /**
     * A list containing all accounts.
     */
    private final Map<Long, Account> accounts = new HashMap<>();

    private Account currentAccount = null;

    /**
     * The transaction history for all accounts.
     */
    private final TransactionsHistory transactionsHistory = new TransactionsHistory();

    /**
     * A counter to generate unique IDs for new accounts.
     */
    private long idCounter = 0;

    public void setCurrentAccount(Account account) {
        currentAccount = account;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    /**
     * Creates a new account, adds it to the list, and returns it.
     *
     * @return the newly created account
     */
    public Account createAccount() {
        Account result = new Account(idCounter);
        accounts.put(idCounter, result);
        idCounter++;
        return result;
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param id the ID of the account
     * @return the found account
     * @throws AccountNotFoundException if no account with the specified ID is found
     */
    public Account getAccount(long id) throws AccountNotFoundException {
        Account result = accounts.get(id);
        if (result == null) {
            throw new AccountNotFoundException("Account not found");
        }
        return result;
    }

    /**
     * Returns the map of all accounts.
     *
     * @return the map of all accounts
     */
    public Map<Long, Account> getAccounts() {
        return accounts;
    }

    /**
     * Withdraws funds from the specified account.
     *
     * @param id     the account ID
     * @param amount the amount to withdraw
     * @throws InsufficientFundsException  if the account does not have enough funds
     * @throws InvalidFundsAmountException if the amount is negative
     */
    public void withdraw(long id, BigDecimal amount) throws InsufficientFundsException, InvalidFundsAmountException, AccountNotFoundException {
        if (amount.longValueExact() < 0) {
            throw new InvalidFundsAmountException("Invalid funds amount.");
        }

        Account account = getAccount(id);

        BigDecimal balance = account.getBalance();

        if (balance.compareTo(amount) == -1) {
            throw new InsufficientFundsException("Insufficient funds on account.");
        }

        account.setBalance(balance.subtract(amount));
        transactionsHistory.add(new Transaction(amount, TransactionType.WITHDRAWAL, id));
    }

    /**
     * Deposits funds into the specified account.
     *
     * @param id     the account ID
     * @param amount the amount to deposit
     * @throws InvalidFundsAmountException if the amount is negative
     */
    public void deposit(long id, BigDecimal amount) throws InvalidFundsAmountException, AccountNotFoundException {
        if (amount.longValueExact() < 0) {
            throw new InvalidFundsAmountException("Invalid funds amount.");
        }

        Account account = getAccount(id);

        BigDecimal balance = account.getBalance();

        account.setBalance(balance.add(amount));
        transactionsHistory.add(new Transaction(amount, TransactionType.DEPOSIT, id));
    }

    /**
     * Returns the transaction history for the specified account ID.
     *
     * @param accountId the account ID
     * @return a list of transactions for the given account
     */
    public List<Transaction> getTransactionsHistoryByAccountId(long accountId) {
        return transactionsHistory.getTransactionsByAccountId(accountId);
    }
}
