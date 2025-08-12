package exceptions;

public class InvalidFundsAmountException extends RuntimeException {
    public InvalidFundsAmountException(String message) {
        super(message);
    }
}
