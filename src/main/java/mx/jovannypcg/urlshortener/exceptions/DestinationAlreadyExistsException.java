package mx.jovannypcg.urlshortener.exceptions;

/**
 * Thrown when the database already contains a destination which
 * is expected to be inserted.
 *
 * @author  Jovanny Pablo Cruz Gomez
 *          Software Engineer
 *          jovannypcg@yahoo.com
 */
public class DestinationAlreadyExistsException extends RuntimeException {
    public DestinationAlreadyExistsException(String destination) {
        super("Destination [" + destination + "] already exists");
    }
}
