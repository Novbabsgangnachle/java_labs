import domain.Account;
import domain.AccountManager;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidFundsAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AccountManagerTest {

    private AccountManager accountManager;

    @BeforeEach
    void setUp(){
        accountManager = new AccountManager();
    }

    @Test
    void testcreateAccount() {
        Account account = accountManager.createAccount();
        assertNotNull(account);
        assertEquals(new BigDecimal("0"), account.getBalance(), "New account balance should be zero.");
    }

    @Test
    void testDeposit() throws InvalidFundsAmountException, AccountNotFoundException {
        Account account = accountManager.createAccount();
        accountManager.deposit(account.getId(), new BigDecimal("100.0"));

        assertEquals(new BigDecimal("100.0"), account.getBalance(), "Account balance should be 100 after deposit.");
    }

    @Test
    void testWithdraw() throws InvalidFundsAmountException, InsufficientFundsException, AccountNotFoundException {
        Account account = accountManager.createAccount();
        accountManager.deposit(account.getId(), new BigDecimal("200.0"));
        accountManager.withdraw(account.getId(), new BigDecimal("100.0"));

        assertEquals(new BigDecimal("100.0"), account.getBalance(), "Account balance should be 100 after withdrawal.");
    }

    @Test
    void testWithdrawInsufficientFunds() throws InvalidFundsAmountException, AccountNotFoundException {
        Account account = accountManager.createAccount();
        accountManager.deposit(account.getId(), new BigDecimal("50.0"));

        assertThrows(InsufficientFundsException.class, () -> accountManager.withdraw(account.getId(), new BigDecimal("100.0")));
    }

    @Test
    void testDepositInvalidAmount() {
        Account account = accountManager.createAccount();
        assertThrows(InvalidFundsAmountException.class, () -> accountManager.deposit(account.getId(), new BigDecimal("-50.0")));
    }

    @Test
    void testWithdrawInvalidAmount() {
        Account account = accountManager.createAccount();
        assertThrows(InvalidFundsAmountException.class, () -> {
            accountManager.withdraw(account.getId(), new BigDecimal("-50.0"));
        });
    }
}
