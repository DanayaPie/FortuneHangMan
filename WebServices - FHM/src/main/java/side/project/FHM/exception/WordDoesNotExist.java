package side.project.FHM.exception;

public class WordDoesNotExist extends Exception {
    public WordDoesNotExist() {
    }

    public WordDoesNotExist(String message) {
        super(message);
    }

    public WordDoesNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public WordDoesNotExist(Throwable cause) {
        super(cause);
    }

    public WordDoesNotExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
