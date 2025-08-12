package presentation.options;

import domain.Account;
import domain.AccountManager;
import domain.Transaction;
import presentation.Option;

public class ShowHistoryOption implements Option {

    @Override
    public void execute(AccountManager accountManager) {

        AccountChooseOption option = new AccountChooseOption();
        option.execute(accountManager);
        Account account = accountManager.getCurrentAccount();
        if (account == null) {
            return;
        }

        for (Transaction transaction : accountManager.getTransactionsHistoryByAccountId(account.getId())) {
            System.out.println("Date: " + transaction.getDate() + " amount: " + transaction.getAmount()
                    + " type: " + transaction.getTransactionType().toString());
        }
    }
}