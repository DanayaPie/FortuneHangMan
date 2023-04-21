package side.project.FHM.exception;

public class WordAlreadyExistException extends Exception {
    public WordAlreadyExistException() {
    }

    public WordAlreadyExistException(String message) {
        super(message);
    }

    public WordAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public WordAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public WordAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
