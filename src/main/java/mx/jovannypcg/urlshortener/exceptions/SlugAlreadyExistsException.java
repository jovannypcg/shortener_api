package mx.jovannypcg.urlshortener.exceptions;

public class SlugAlreadyExistsException extends RuntimeException {
    public SlugAlreadyExistsException(String destination) {
        super("Slug [" + destination + "] already exists");
    }
}
