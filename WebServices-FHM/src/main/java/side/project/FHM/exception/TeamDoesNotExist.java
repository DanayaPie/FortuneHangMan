package side.project.FHM.exception;

public class TeamDoesNotExist extends Exception {
    public TeamDoesNotExist() {
    }

    public TeamDoesNotExist(String message) {
        super(message);
    }

    public TeamDoesNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public TeamDoesNotExist(Throwable cause) {
        super(cause);
    }

    public TeamDoesNotExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
