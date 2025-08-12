package presentation;

import domain.AccountManager;
import presentation.options.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Provides a simple console-based interface for interacting with accounts.
 */
public class ConsoleSimulator {

    /**
     * A mapping of option keys (menu choices) to their corresponding actions.
     */
    private final Map<String, Option> options = new HashMap<>();

    /**
     * An AccountManager instance for handling account operations.
     */
    private final AccountManager accountManager = new AccountManager();

    /**
     * Displays the menu, waits for user input, and executes the corresponding option.
     * Then, it recursively calls itself to display the menu again.
     */
    public void menu() {

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Select an option:");
            System.out.println("\t1. Create account");
            System.out.println("\t2. Withdrawal");
            System.out.println("\t3. Deposit");
            System.out.println("\t4. Show history");
            System.out.println("\t5. Show accounts");
            System.out.println("\t6. Exit Program");

            String option = scanner.nextLine();

            if (!options.containsKey(option)){
                System.out.println("Invalid option!");
                continue;
            }
            options.get(option).execute(accountManager);
        }
    }

    /**
     * Registers the default options in the constructor.
     */
    public ConsoleSimulator() {
        options.put("1", new CreateAccountOption());
        options.put("2", new WithDrawalOption());
        options.put("3", new DepositOption());
        options.put("4", new ShowHistoryOption());
        options.put("5", new ShowAccountsOption());
        options.put("6", new ExitProgramOption());
    }
}
