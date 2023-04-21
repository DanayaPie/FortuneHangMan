package side.project.FHM.exception;

public class RoundDoesNotExistException extends Exception {
    public RoundDoesNotExistException() {
    }

    public RoundDoesNotExistException(String message) {
        super(message);
    }

    public RoundDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoundDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public RoundDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
