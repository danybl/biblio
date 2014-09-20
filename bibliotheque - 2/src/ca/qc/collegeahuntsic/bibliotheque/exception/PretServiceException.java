
package ca.qc.collegeahuntsic.bibliotheque.exception;

public class PretServiceException extends Exception {

    /**
     * TODO Auto-generated field javadoc
     */
    private static final long serialVersionUID = 1L;

    public PretServiceException() {
        super();
    }

    public PretServiceException(String message) {
        super(message);
    }

    public PretServiceException(Throwable cause) {
        super(cause);
    }

    public PretServiceException(String message,
        Throwable cause) {
        super(message,
            cause);
    }

    public PretServiceException(String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message,
            cause,
            enableSuppression,
            writableStackTrace);
    }

}