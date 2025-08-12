package presentation.options;

import domain.Account;
import domain.AccountManager;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidFundsAmountException;
import presentation.Option;

import java.math.BigDecimal;
import java.util.Scanner;

public class WithDrawalOption implements Option {

    @Override
    public void execute(AccountManager accountManager)  {
        AccountChooseOption option = new AccountChooseOption();
        option.execute(accountManager);
        Account account = accountManager.getCurrentAccount();
        if (account == null) {
            return;
        }

        System.out.println("Select an amount: ");

        Scanner in = new Scanner(System.in);

        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(in.nextLine()));

        in.close();
        try{

            accountManager.withdraw(account.getId(), amount);
        } catch (InsufficientFundsException e) {
            System.out.println("Insufficient funds");
        } catch (InvalidFundsAmountException e){
            System.out.println("Invalid funds amount");
        } catch (AccountNotFoundException e) {
            System.out.println("Account not found");
        }
    }
}
