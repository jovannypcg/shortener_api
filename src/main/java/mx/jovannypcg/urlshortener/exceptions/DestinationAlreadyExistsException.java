package mx.jovannypcg.urlshortener.exceptions;

public class DestinationAlreadyExistsException extends RuntimeException {
    public DestinationAlreadyExistsException(String destination) {
        super("Destination [" + destination + "] already exists");
    }
}
