package side.project.FHM.exception;

public class WordDoesNotExistException extends Exception {
    public WordDoesNotExistException() {
    }

    public WordDoesNotExistException(String message) {
        super(message);
    }

    public WordDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public WordDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public WordDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
