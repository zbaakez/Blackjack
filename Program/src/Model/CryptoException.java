package Model;

/**
 * CryptoException class is a class for a custom exception
 * @author Busted
 */
public class CryptoException extends Exception {

    public CryptoException() {
    }

    public CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}