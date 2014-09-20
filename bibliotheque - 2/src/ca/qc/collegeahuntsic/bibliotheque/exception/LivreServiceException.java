
package ca.qc.collegeahuntsic.bibliotheque.exception;

public class LivreServiceException extends Exception {

    /**
     * TODO Auto-generated field javadoc
     */
    private static final long serialVersionUID = 1L;

    public LivreServiceException() {
        super();
    }

    public LivreServiceException(String message) {
        super(message);
    }

    public LivreServiceException(Throwable cause) {
        super(cause);
    }

    public LivreServiceException(String message,
        Throwable cause) {
        super(message,
            cause);
    }

    public LivreServiceException(String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message,
            cause,
            enableSuppression,
            writableStackTrace);
    }

}