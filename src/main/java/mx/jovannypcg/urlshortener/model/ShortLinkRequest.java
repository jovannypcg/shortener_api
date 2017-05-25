package mx.jovannypcg.urlshortener.model;

/**
 * Provides the needed attributes to deal with JSON requests in /shortlinks.
 */
public class ShortLinkRequest {
    private String destination;
    private String slug;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return "{ destination: " + destination + ", slug: " + slug + " }";
    }
}
