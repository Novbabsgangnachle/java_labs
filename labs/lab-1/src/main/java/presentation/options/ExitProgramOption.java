package presentation.options;

import domain.AccountManager;
import presentation.Option;

public class ExitProgramOption implements Option {

    @Override
    public void execute(AccountManager accountManager) {
        System.exit(0);
    }
}
