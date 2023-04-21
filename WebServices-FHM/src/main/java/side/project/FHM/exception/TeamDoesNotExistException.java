package side.project.FHM.exception;

public class TeamDoesNotExistException extends Exception {
    public TeamDoesNotExistException() {
    }

    public TeamDoesNotExistException(String message) {
        super(message);
    }

    public TeamDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeamDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public TeamDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
