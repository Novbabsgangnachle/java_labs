package presentation.options;

import domain.Account;
import domain.AccountManager;
import presentation.Option;

public class ShowAccountsOption implements Option {

    @Override
    public void execute(AccountManager accountManager) {
        for (Account account : accountManager.getAccounts().values()) {
            System.out.println("Id: " + account.getId() + " Balance: " + account.getBalance());
        }
    }
}
