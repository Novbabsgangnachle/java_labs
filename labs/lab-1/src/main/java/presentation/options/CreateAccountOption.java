package presentation.options;

import domain.Account;
import domain.AccountManager;
import presentation.Option;

public class CreateAccountOption implements Option {

    @Override
    public void execute(AccountManager accountManager) {
        Account result = accountManager.createAccount();

        System.out.printf("New account created with id: %s\n", result.getId());
    }
}
