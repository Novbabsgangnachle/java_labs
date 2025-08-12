import domain.Transaction;
import domain.TransactionType;
import domain.TransactionsHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionsHistoryTest {
    private TransactionsHistory transactionsHistory;

    @BeforeEach
    void setUp() {
        transactionsHistory = new TransactionsHistory();
    }

    @Test
    void testAddTransaction() {
        Transaction transaction = new Transaction(new BigDecimal("100.0"), TransactionType.DEPOSIT, 1);
        transactionsHistory.add(transaction);

        assertEquals(1, transactionsHistory.getTransactionsByAccountId(1).size(), "There should be 1 transaction.");
    }

    @Test
    void testGetTransactionsByAccountId() {
        Transaction deposit = new Transaction(new BigDecimal("100.0"), TransactionType.DEPOSIT, 1);
        Transaction withdrawal = new Transaction(new BigDecimal("50.0"), TransactionType.WITHDRAWAL, 1);
        transactionsHistory.add(deposit);
        transactionsHistory.add(withdrawal);

        assertEquals(2, transactionsHistory.getTransactionsByAccountId(1).size(), "There should be 2 transactions for account 1.");
    }
}
