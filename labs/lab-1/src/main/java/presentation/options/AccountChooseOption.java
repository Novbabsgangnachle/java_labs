package presentation.options;

import domain.Account;
import domain.AccountManager;
import exceptions.AccountNotFoundException;
import presentation.Option;

import java.util.Scanner;

/**
 * Handles user selection of an existing account.
 */
public class AccountChooseOption implements Option {

    /**
     * Prompts the user to choose an account, displays all accounts,
     * reads the user input, and returns the chosen account.
     *
     * @param accountManager the account manager
     */
    @Override
    public void execute(AccountManager accountManager)  {
        System.out.println("Choose an account:");

        ShowAccountsOption showAccountsOption = new ShowAccountsOption();
        showAccountsOption.execute(accountManager);

        Account account;
        Scanner in = new Scanner(System.in);
        try{
            String id = in.nextLine();
            accountManager.setCurrentAccount(accountManager.getAccount(Long.parseLong(id)));
        } catch (AccountNotFoundException e) {
            System.out.println("Account not found!");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number");
        }
        in.close();
    }
}
