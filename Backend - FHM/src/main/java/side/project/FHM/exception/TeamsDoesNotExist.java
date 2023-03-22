package side.project.FHM.exception;

public class TeamsDoesNotExist extends Exception{
    public TeamsDoesNotExist() {
    }

    public TeamsDoesNotExist(String message) {
        super(message);
    }

    public TeamsDoesNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public TeamsDoesNotExist(Throwable cause) {
        super(cause);
    }

    public TeamsDoesNotExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
