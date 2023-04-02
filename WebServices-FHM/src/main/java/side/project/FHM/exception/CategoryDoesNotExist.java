package side.project.FHM.exception;

public class CategoryDoesNotExist extends Exception {
    public CategoryDoesNotExist() {
    }

    public CategoryDoesNotExist(String message) {
        super(message);
    }

    public CategoryDoesNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryDoesNotExist(Throwable cause) {
        super(cause);
    }

    public CategoryDoesNotExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
