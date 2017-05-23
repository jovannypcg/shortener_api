package mx.jovannypcg.urlshortener.exceptions;

public class DestinationNotFoundException extends RuntimeException {
    public DestinationNotFoundException(String slug) {
        super("Destination for the given slug [" + slug + "] was not found");
    }
}
