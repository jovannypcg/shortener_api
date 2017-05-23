package mx.jovannypcg.urlshortener.model;

/**
 * Provides the needed attributes to deal with JSON requests in /shortlinks.
 */
public class ShortLinkRequest {
    private String destination;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
