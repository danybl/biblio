
package ca.qc.collegeahuntsic.bibliothequeBackEnd.exception;

public class BibliothequeException extends Exception {

    private static final long serialVersionUID = 1L;

    public BibliothequeException() {
        super();
    }

    public BibliothequeException(String message) {
        super(message);
    }

    public BibliothequeException(Throwable cause) {
        super(cause);
    }

    public BibliothequeException(String message,
        Throwable cause) {
        super(message,
            cause);
    }

    public BibliothequeException(String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message,
            cause,
            enableSuppression,
            writableStackTrace);
    }

}
