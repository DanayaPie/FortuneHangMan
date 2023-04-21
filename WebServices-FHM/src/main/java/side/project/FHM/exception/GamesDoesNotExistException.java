package side.project.FHM.exception;

public class GamesDoesNotExistException extends Exception {
    public GamesDoesNotExistException() {
    }

    public GamesDoesNotExistException(String message) {
        super(message);
    }

    public GamesDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public GamesDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public GamesDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
