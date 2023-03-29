package side.project.FHM.exception;

public class RoundDoesNotExist extends Exception {
    public RoundDoesNotExist() {
    }

    public RoundDoesNotExist(String message) {
        super(message);
    }

    public RoundDoesNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public RoundDoesNotExist(Throwable cause) {
        super(cause);
    }

    public RoundDoesNotExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
