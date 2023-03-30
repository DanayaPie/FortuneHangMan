package side.project.FHM.exception;

public class GamesDoesNotExist extends Exception {
    public GamesDoesNotExist() {
    }

    public GamesDoesNotExist(String message) {
        super(message);
    }

    public GamesDoesNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public GamesDoesNotExist(Throwable cause) {
        super(cause);
    }

    public GamesDoesNotExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
