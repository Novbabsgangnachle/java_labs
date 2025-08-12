package presentation.options;

import domain.Account;
import domain.AccountManager;
import exceptions.AccountNotFoundException;
import exceptions.InvalidFundsAmountException;
import presentation.Option;

import java.math.BigDecimal;
import java.util.Scanner;

public class DepositOption implements Option {

    @Override
    public void execute(AccountManager accountManager) {
        AccountChooseOption option = new AccountChooseOption();
        option.execute(accountManager);
        Account account = accountManager.getCurrentAccount();
        if (account == null) {
            return;
        }

        System.out.println("Select an amount: ");

        Scanner in = new Scanner(System.in);

        in.close();
        try{
            BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(in.nextLine()));
            accountManager.deposit(account.getId(), amount);

        } catch (InvalidFundsAmountException e){
            System.out.println("Invalid funds amount!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount!");
        } catch (AccountNotFoundException e) {
            System.out.println("Account not found!");
        }

    }
}
