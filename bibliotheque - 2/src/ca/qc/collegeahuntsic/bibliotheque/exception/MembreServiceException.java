
package ca.qc.collegeahuntsic.bibliotheque.exception;

public class MembreServiceException extends Exception {

    /**
     * TODO Auto-generated field javadoc
     */
    private static final long serialVersionUID = 1L;

    public MembreServiceException() {
        super();
    }

    public MembreServiceException(String message) {
        super(message);
    }

    public MembreServiceException(Throwable cause) {
        super(cause);
    }

    public MembreServiceException(String message,
        Throwable cause) {
        super(message,
            cause);
    }

    public MembreServiceException(String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message,
            cause,
            enableSuppression,
            writableStackTrace);
    }

}
